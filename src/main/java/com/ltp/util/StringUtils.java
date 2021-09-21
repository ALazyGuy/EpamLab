package com.ltp.util;

import java.util.Arrays;
import java.util.Objects;

public class StringUtils {

    public static boolean isPositiveNumber(String number) throws NumberFormatException{
        if(Objects.isNull(number) || number.charAt(0) == '-'){
            return false;
        }

        if(number.charAt(0) == '+'){
           number = number.substring(1);
        }

        String[] parts = number.split("\\.");

        if(parts.length > 2 || Arrays.stream(parts).anyMatch(part -> !org.apache.commons.lang3.StringUtils.isNumeric(part))){
            throw new NumberFormatException(number);
        }

        return true;
    }

}
