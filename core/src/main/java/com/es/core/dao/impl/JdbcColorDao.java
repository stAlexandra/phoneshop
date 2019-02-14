package com.es.core.dao.impl;

import com.es.core.dao.ColorDao;
import com.es.core.dao.mappers.ColorRowMapper;
import com.es.core.model.phone.Color;
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
public class JdbcColorDao implements ColorDao {
    private static final String SQL_GET_1_COLORSET = "SELECT * FROM colors INNER JOIN phone2color ON colors.id = phone2color.colorId WHERE phone2color.phoneId = :phoneId";
    private static final String SQL_GET_MULTIPLE_COLORSETS = "SELECT * FROM colors AS c INNER JOIN phone2color AS p2c ON c.id = p2c.colorId WHERE p2c.phoneId IN (:phoneIds)";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private ColorRowMapper colorRowMapper;

    @Override
    public Set<Color> getColors(Long phoneId) {
        List<Color> colorList = jdbcTemplate.query(SQL_GET_1_COLORSET, Collections.singletonMap("phoneId", phoneId), colorRowMapper);
        return new HashSet<>(colorList);
    }

    @Override
    public Map<Long, Set<Color>> getColors(List<Long> phoneIds) {
        Map<Long, Set<Color>> phoneIdToColors = new HashMap<>();

        SqlParameterSource params = new MapSqlParameterSource("phoneIds", phoneIds);
        jdbcTemplate.query(SQL_GET_MULTIPLE_COLORSETS,
                params, resultSet -> {
                    do {
                        Long phoneId = resultSet.getLong("phone2color.phoneId");
                        if(phoneIdToColors.containsKey(phoneId)){
                            phoneIdToColors.get(phoneId).add(colorRowMapper.mapRow(resultSet, 1));
                        } else {
                            Set<Color> colorSet = new HashSet<>();
                            colorSet.add(colorRowMapper.mapRow(resultSet, 1));
                            phoneIdToColors.put(phoneId, colorSet);
                        }
                    } while (resultSet.next());
                });
        return phoneIdToColors;
    }

    @Override
    public void save(Long phoneId, Set<Color> colors) {
        Set<Color> newColors = colors.stream().filter(color -> color.getId() == null).collect(Collectors.toSet());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParams = new MapSqlParameterSource("phoneId", phoneId);
        for (Color color : colors) {
            namedParams.addValue("code", color.getCode());

            jdbcTemplate.update("MERGE INTO colors (code) KEY (code) VALUES(:code)", namedParams, keyHolder);
            color.setId(keyHolder.getKey().longValue());
        }

        List<SqlParameterSource> batchValues = new ArrayList<>();
        newColors.forEach(color -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("phoneId", phoneId);
            params.addValue("colorId", color.getId());
            batchValues.add(params);
        });

        jdbcTemplate.batchUpdate("INSERT INTO phone2color VALUES (:phoneId, :colorId)", batchValues.toArray(new SqlParameterSource[0]));
    }

}