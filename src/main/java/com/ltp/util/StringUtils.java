package com.ltp.util;

import java.util.Objects;

public class StringUtils {

    public static boolean isPositiveNumber(String number){
        if(Objects.isNull(number)){
            return false;
        }

        return number.startsWith("-") && org.apache.commons.lang3.StringUtils.isNumeric(number.substring(1));
    }

}
