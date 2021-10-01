package com.epam.esm.builder;

public class SQLQueryParamBuilder {

    private StringBuilder result;
    private boolean initialized;

    private SQLQueryParamBuilder(String start, boolean isEmpty){
        initialized = !isEmpty;
        result = new StringBuilder(start);
    }

    public static SQLQueryParamBuilder initEquals(String paramName, String param){
        if(param.isEmpty()){
            return new SQLQueryParamBuilder("", param.isEmpty());
        }
        return new SQLQueryParamBuilder(String.format(" WHERE %s = '%s'", paramName, param), param.isEmpty());
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

        this.result.append(String.format("%s LIKE '%%%s%%'", paramName, param));
        return this;
    }

    public String build(){
        return result.toString();
    }

}
