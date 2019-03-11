package com.es.core.dao.impl;

import com.es.core.dao.ColorDao;
import com.es.core.dao.PhoneDao;
import com.es.core.dao.mappers.PhoneRowMapper;
import com.es.core.exception.PhonesNotFoundException;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String SQL_SELECT_PHONE = "SELECT * FROM phones WHERE id = :phoneId";
    private static final String SQL_INSERT_PHONE = "INSERT INTO phones VALUES (:id, :brand, :model, :price, :displaySizeInches, :weightGr," +
            ":lengthMm, :widthMm, :heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity, " +
            ":displayTechnology, :backCameraMegapixels, :frontCameraMegapixels, :ramGb, :internalStorageGb, :batteryCapacityMah, " +
            ":talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";
    private static final String SQL_UPDATE_PHONE = "UPDATE phones SET brand=:brand, model=:model, price=:price," +
            " displaySizeInches=:displaySizeInches, weightGr=:weightGr, lengthMm=:lengthMm, widthMm=:widthMm," +
            " heightMm=:heightMm, announced=:announced, deviceType=:deviceType, os=:os, displayResolution=:displayResolution," +
            " pixelDensity=:pixelDensity, displayTechnology=:displayTechnology, backCameraMegapixels=:backCameraMegapixels," +
            " frontCameraMegapixels=:frontCameraMegapixels, ramGb=:ramGb, internalStorageGb=:internalStorageGb," +
            " batteryCapacityMah=:batteryCapacityMah, talkTimeHours=:talkTimeHours, standByTimeHours=:standByTimeHours," +
            " bluetooth=:bluetooth, positioning=:positioning, imageUrl=:imageUrl, description=:description" +
            " WHERE id=:id";
    private static final String SQL_INSERT_PHONE2COLOR = "INSERT INTO phone2color VALUES (:phoneId, :colorId)";
    private static final String SQL_FIND_ALL = "SELECT * FROM phones OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_ALL_PRESENT_IN_STOCK = "SELECT ph.* FROM phones ph " +
            "RIGHT OUTER JOIN stocks st ON ph.id = st.phoneId WHERE st.stock - st.reserved > 0 " +
            "AND ph.price > 0 " +
            "OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_VALID_QUERY = "SELECT ph.* FROM phones ph " +
            "RIGHT OUTER JOIN stocks st ON ph.id = st.phoneId WHERE st.stock - st.reserved > 0 " +
            "AND ph.price > 0 " +
            "AND (ph.brand ILIKE CONCAT('%', :query, '%') OR ph.model ILIKE CONCAT('%', :query, '%')) " +
            "OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_VALID_SORT = "SELECT ph.* FROM phones ph " +
            "RIGHT OUTER JOIN stocks st ON ph.id = st.phoneId WHERE st.stock - st.reserved > 0 " +
            "AND ph.price > 0 " +
            "ORDER BY sortName sortOrder OFFSET :offset LIMIT :limit";
    private static final String SQL_FIND_VALID_QUERY_SORT = "SELECT ph.* FROM phones ph " +
            "RIGHT OUTER JOIN stocks st ON ph.id = st.phoneId WHERE st.stock - st.reserved > 0 " +
            "AND ph.price > 0 " +
            "AND (ph.brand ILIKE CONCAT('%', :query, '%') OR ph.model ILIKE CONCAT('%', :query, '%')) " +
            "ORDER BY sortName sortOrder OFFSET :offset LIMIT :limit";
    private static final String SQL_COUNT_VALID_PHONES = "SELECT COUNT(phoneId) AS count FROM stocks st " +
            "LEFT OUTER JOIN phones ph ON ph.id = st.phoneId " +
            "WHERE st.stock - st.reserved > 0 AND ph.price > 0";
    private static final String SQL_COUNT_VALID_PHONES_MATCHING_QUERY = "SELECT COUNT(id) AS count FROM phones ph " +
            "RIGHT OUTER JOIN stocks st ON ph.id = st.phoneId WHERE st.stock - st.reserved > 0 " +
            "AND ph.price > 0 " +
            "AND (ph.brand ILIKE CONCAT('%', :query, '%') OR ph.model ILIKE CONCAT('%', :query, '%'))";
    private static final String PHONE_ID_PARAM = "phoneId";
    private static final String COLOR_ID_PARAM = "colorId";
    private static final String COUNT_COLUMN_LABEL = "count";
    private static final String QUERY_PARAM = "query";
    private static final String SORT_NAME_PARAM = "sortName";
    private static final String SORT_ORDER_PARAM = "sortOrder";
    private static final String OFFSET_PARAM = "offset";
    private static final String LIMIT_PARAM = "limit";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Resource
    private ColorDao jdbcColorDao;
    @Resource
    private PhoneRowMapper phoneRowMapper;

    public Phone get(final Long key) throws PhonesNotFoundException {
        Map<String, Long> namedParameters = Collections.singletonMap(PHONE_ID_PARAM, key);
        Set<Color> colors = jdbcColorDao.getColors(key);

        return jdbcTemplate.query(SQL_SELECT_PHONE, namedParameters,
                resultSet -> {
                    if (resultSet.next()) {
                        Phone phone = phoneRowMapper.mapRow(resultSet, 1);
                        phone.setColors(new HashSet<>(colors));
                        return phone;
                    } else {
                        throw new PhonesNotFoundException(key);
                    }
                });
    }

    public void save(final Phone phone) {
        if (phone.getBrand() == null || phone.getModel() == null) {
            throw new IllegalArgumentException("Trying to save phone with empty required fields.");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource phoneParam = new BeanPropertySqlParameterSource(phone);
        if (phone.getId() == null) {
            jdbcTemplate.update(SQL_INSERT_PHONE, phoneParam, keyHolder);
            phone.setId(keyHolder.getKey().longValue());
        } else {
            jdbcTemplate.update(SQL_UPDATE_PHONE, phoneParam);
        }

        Set<Color> phoneColors = phone.getColors();
        if (!phoneColors.isEmpty()) {
            Set<Color> newColors = phoneColors.stream().filter(color -> color.getId() == null)
                    .collect(Collectors.toSet());
            jdbcColorDao.save(phoneColors);
            savePhone2Color(phone.getId(), newColors);
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return findPhones(SQL_FIND_ALL, offset, limit, new MapSqlParameterSource());
    }

    public List<Phone> findAllValid(int offset, int limit) {
        return findPhones(SQL_FIND_ALL_PRESENT_IN_STOCK, offset, limit, new MapSqlParameterSource());
    }

    public List<Phone> findAllValid(int offset, int limit, String query) {
        return findPhones(SQL_FIND_VALID_QUERY, offset, limit, new MapSqlParameterSource(QUERY_PARAM, query));
    }

    public List<Phone> findAllValid(int offset, int limit, String sortName, String sortOrder) {
        return findPhones(SQL_FIND_VALID_SORT.replaceFirst(SORT_NAME_PARAM, sortName)
                        .replaceFirst(SORT_ORDER_PARAM, sortOrder),
                offset, limit, new MapSqlParameterSource());
    }

    public List<Phone> findAllValid(int offset, int limit, String query, String sortName, String sortOrder) {
        return findPhones(SQL_FIND_VALID_QUERY_SORT.replaceFirst(SORT_NAME_PARAM, sortName)
                        .replaceFirst(SORT_ORDER_PARAM, sortOrder),
                offset, limit, new MapSqlParameterSource(QUERY_PARAM, query));
    }

    public int findValidPhonesTotalCount() {
        return jdbcTemplate.query(SQL_COUNT_VALID_PHONES, resultSet ->
                resultSet.next() ? resultSet.getInt(COUNT_COLUMN_LABEL) : 0);
    }

    public int findPhonesMatchingQueryTotalCount(String query) {
        return jdbcTemplate.query(SQL_COUNT_VALID_PHONES_MATCHING_QUERY,
                Collections.singletonMap(QUERY_PARAM, query),
                resultSet -> resultSet.next() ? resultSet.getInt(COUNT_COLUMN_LABEL) : 0);
    }

    private List<Phone> findPhones(String sql, int offset, int limit, MapSqlParameterSource params) {
        params.addValue(OFFSET_PARAM, offset);
        params.addValue(LIMIT_PARAM, limit);

        List<Phone> phones = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Phone.class));

        if (!phones.isEmpty()) {
            setPhoneColors(phones);
        }
        return phones;
    }

    private void savePhone2Color(Long phoneId, Set<Color> colors) {
        List<SqlParameterSource> batchValues = new ArrayList<>();
        colors.forEach(color -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(PHONE_ID_PARAM, phoneId);
            params.addValue(COLOR_ID_PARAM, color.getId());
            batchValues.add(params);
        });

        jdbcTemplate.batchUpdate(SQL_INSERT_PHONE2COLOR, batchValues.toArray(new SqlParameterSource[0]));
    }

    private void setPhoneColors(List<Phone> phones) {
        Map<Long, Set<Color>> mapPhoneIdToColor = jdbcColorDao.getColors(phones.stream().map(Phone::getId)
                .collect(Collectors.toList()));
        phones.forEach(phone -> {
            Set<Color> colors = mapPhoneIdToColor.get(phone.getId());
            phone.setColors(colors);
        });
    }
}
