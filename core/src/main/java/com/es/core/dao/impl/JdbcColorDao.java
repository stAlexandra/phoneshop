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

@Component
public class JdbcColorDao implements ColorDao {
    private static final String SQL_GET_1_COLORSET = "SELECT * FROM colors INNER JOIN phone2color ON colors.id = phone2color.colorId WHERE phone2color.phoneId = :phoneId";
    private static final String SQL_GET_MULTIPLE_COLORSETS = "SELECT * FROM colors c INNER JOIN phone2color p2c ON c.id = p2c.colorId WHERE p2c.phoneId IN (:phoneIds)";
    private static final String SQL_MERGE_INTO_COLORS = "MERGE INTO colors (code) KEY (code) VALUES(:code)";
    
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
                        if(!phoneIdToColors.containsKey(phoneId)){
                            phoneIdToColors.put(phoneId, new HashSet<>());
                        }
                        phoneIdToColors.get(phoneId).add(colorRowMapper.mapRow(resultSet, 1));
                    } while (resultSet.next());
                });
        return phoneIdToColors;
    }

    @Override
    public void save(Set<Color> colors) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParams = new MapSqlParameterSource();
        for (Color color : colors) {
            namedParams.addValue("code", color.getCode());

            jdbcTemplate.update(SQL_MERGE_INTO_COLORS, namedParams, keyHolder);
            if(!keyHolder.getKeyList().isEmpty()) {
                color.setId(keyHolder.getKey().longValue());
            }
        }
    }

}
