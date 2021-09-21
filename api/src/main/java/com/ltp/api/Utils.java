package com.ltp.api;

import com.ltp.util.StringUtils;

import java.util.Arrays;

public class Utils {

    public static boolean isAllPositiveNumbers(String ...args){
        return Arrays.stream(args).allMatch(StringUtils::isPositiveNumber);
    }

}
