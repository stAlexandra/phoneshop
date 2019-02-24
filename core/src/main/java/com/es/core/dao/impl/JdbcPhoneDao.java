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

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Resource
    private ColorDao jdbcColorDao;
    @Resource
    private PhoneRowMapper phoneRowMapper;

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
    private static final String SQL_FIND_ALL_PRESENT_IN_STOCK = "SELECT * FROM phones ph LEFT OUTER JOIN stocks st ON ph.id = st.phoneId WHERE st.stock - st.reserved > 0 OFFSET :offset LIMIT :limit";


    public Phone get(final Long key) throws PhonesNotFoundException {
        Map<String, Long> namedParameters = Collections.singletonMap("phoneId", key);
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
            Set<Color> newColors = phoneColors.stream().filter(color -> color.getId() == null).collect(Collectors.toSet());
            jdbcColorDao.save(phoneColors);
            savePhone2Color(phone.getId(), newColors);
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        return findPhones(SQL_FIND_ALL, offset, limit);
    }

    public List<Phone> findAllValid(int offset, int limit) {
        return findPhones(SQL_FIND_ALL_PRESENT_IN_STOCK, offset, limit);
    }

    public int findValidPhonesTotalCount() {
        return jdbcTemplate.query("SELECT COUNT(phoneId) AS count FROM stocks WHERE stock - reserved > 0", resultSet ->
                resultSet.next() ? resultSet.getInt("count") : 0);
    }

    private List<Phone> findPhones(String sql, int offset, int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("offset", offset);
        params.addValue("limit", limit);

        List<Phone> phones = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Phone.class));
        if (phones.isEmpty()) {
            throw new PhonesNotFoundException();
        }
        setPhoneColors(phones);
        return phones;
    }

    private void savePhone2Color(Long phoneId, Set<Color> colors) {
        List<SqlParameterSource> batchValues = new ArrayList<>();
        colors.forEach(color -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("phoneId", phoneId);
            params.addValue("colorId", color.getId());
            batchValues.add(params);
        });

        jdbcTemplate.batchUpdate(SQL_INSERT_PHONE2COLOR, batchValues.toArray(new SqlParameterSource[0]));
    }

    private void setPhoneColors(List<Phone> phones) {
        Map<Long, Set<Color>> mapPhoneIdToColor = jdbcColorDao.getColors(phones.stream().map(Phone::getId).collect(Collectors.toList()));
        phones.forEach(phone -> {
            Set<Color> colors = mapPhoneIdToColor.get(phone.getId());
            phone.setColors(colors);
        });
    }
}
