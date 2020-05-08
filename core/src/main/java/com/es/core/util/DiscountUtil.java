package com.es.core.util;

import com.es.core.model.discount.Discount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class DiscountUtil {
    public static BigDecimal getAbsoluteDiscountValue(BigDecimal total, Discount discount) {
        switch (discount.getValueType()) {
            case AMOUNT: {
                return discount.getValue();
            }
            case PERCENTAGE: {
                return total.multiply(discount.getValue());
            }
            default: {
                return BigDecimal.ZERO;
            }
        }
    }

    public static BigDecimal getPercentageDiscountValue(BigDecimal total, Discount discount) {
        switch (discount.getValueType()) {
            case AMOUNT: {
                return discount.getValue().divide(total, new MathContext(2, RoundingMode.FLOOR));
            }
            case PERCENTAGE: {
                return discount.getValue();
            }
            default: {
                return BigDecimal.ZERO;
            }
        }
    }

}
