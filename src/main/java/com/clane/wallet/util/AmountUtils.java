package com.clane.wallet.util;

import java.math.BigDecimal;

/**
 * @author timolor
 * @created 30/07/2021
 */
public class AmountUtils {

    public static BigDecimal toMajor(String minorAmount){
        BigDecimal bigDecimalAmount = new BigDecimal(minorAmount);
        BigDecimal divisor = new BigDecimal(100);
        return bigDecimalAmount.divide(divisor);
    }
}
