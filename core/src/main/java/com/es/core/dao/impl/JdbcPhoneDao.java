package com.es.core.dao.impl;

import com.es.core.dao.ColorDao;
import com.es.core.dao.PhoneDao;
import com.es.core.dao.mappers.PhoneRowMapper;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Resource
    private ColorDao jdbcColorDao;
    @Resource
    private PhoneRowMapper phoneRowMapper;

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


    public Phone get(final Long key) throws PhoneNotFoundException {
        Map<String, Long> namedParameters = Collections.singletonMap("phoneId", key);
        Set<Color> colors = jdbcColorDao.getColors(key);

        return jdbcTemplate.query("SELECT * FROM phones WHERE id = :phoneId", namedParameters,
                resultSet -> {
                    if(resultSet.next()){
                        Phone phone = phoneRowMapper.mapRow(resultSet, 1);
                        phone.setColors(new HashSet<>(colors));
                        return phone;
                    } else {
                        throw new PhoneNotFoundException(key);
                    }
                });
    }

    public void save(final Phone phone) {
        if(phone.getBrand() == null || phone.getModel() == null){
            throw new IllegalArgumentException("Trying to save phone with NULL required fields.");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource phoneParam = new BeanPropertySqlParameterSource(phone);
        if(phone.getId() == null){
            jdbcTemplate.update(SQL_INSERT_PHONE, phoneParam, keyHolder);
            phone.setId(keyHolder.getKey().longValue());
        } else {
            jdbcTemplate.update(SQL_UPDATE_PHONE, phoneParam);
        }

        jdbcColorDao.save(phone.getId(), phone.getColors());
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query("SELECT * FROM phones OFFSET " + offset + " LIMIT " + limit, new BeanPropertyRowMapper<>(Phone.class));
    }
}
