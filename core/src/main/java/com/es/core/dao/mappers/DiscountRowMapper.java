package com.es.core.dao.mappers;

import com.es.core.model.discount.Discount;
import com.es.core.model.discount.DiscountApplicableFor;
import com.es.core.model.discount.DiscountValueType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.es.core.dao.DbContract.DiscountsTable.*;

@Component
public class DiscountRowMapper implements RowMapper<Discount> {
    @Override
    public Discount mapRow(ResultSet resultSet, int i) throws SQLException {
        Discount discount = new Discount();
        discount.setValue(resultSet.getBigDecimal(VALUE));
        discount.setValueType(DiscountValueType.valueOf(resultSet.getString(VALUE_TYPE)));
        discount.setApplicableFor(DiscountApplicableFor.valueOf(resultSet.getString(APPLICABLE_FOR)));
        discount.setEnabled(resultSet.getBoolean(ENABLED));
        return discount;
    }
}
