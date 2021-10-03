package com.epam.esm.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

public class SQLQueryParamBuilder {

    @Data
    @AllArgsConstructor
    public static class SQLQueryParamState{
        private String query;
        private List<Object> args;
    }

    private StringBuilder result;
    private static List<Object> args;
    private boolean initialized;

    private SQLQueryParamBuilder(String start, String param){
        initialized = !param.isEmpty();
        result = new StringBuilder(start);
        args = new LinkedList<>();
        if(initialized){
            args.add(param);
        }
    }

    public static SQLQueryParamBuilder initEquals(String paramName, String param){
        if(param.isEmpty()){
            return new SQLQueryParamBuilder("", param);
        }

        return new SQLQueryParamBuilder(String.format(" WHERE %s = ?", paramName), param);
    }

    public SQLQueryParamBuilder like(String paramName, String param){
        if(param.isEmpty()){
            return this;
        }

        if(!initialized){
            initialized = true;
            result = new StringBuilder(" WHERE ");
        }else{
            result.append(" AND ");
        }

        this.result.append(String.format("%s LIKE ?", paramName));
        args.add(String.format("%%%s%%", param));
        return this;
    }

    public SQLQueryParamState build(){
        return new SQLQueryParamState(result.toString(), args);
    }

}
