package com.epam.esm.builder;

public class SQLQueryParamBuilder {

    private StringBuilder result;

    private SQLQueryParamBuilder(String start){
        result = new StringBuilder(start);
    }

    public static SQLQueryParamBuilder initEquals(String paramName){
        return new SQLQueryParamBuilder(String.format("WHERE %s = ?"));
    }

    public static SQLQueryParamBuilder initLike(String paramName){
        return new SQLQueryParamBuilder(String.format("WHERE %s LIKE ?"));
    }

    public SQLQueryParamBuilder or(){
        this.result.append(" OR ");
        return this;
    }

    public SQLQueryParamBuilder and(){
        this.result.append(" AND ");
        return this;
    }

    public SQLQueryParamBuilder like(String paramName){
        this.result.append(String.format("%s LIKE ?", paramName));
        return this;
    }

    public SQLQueryParamBuilder equals(String paramName){
        this.result.append(String.format("%s = ?", paramName));
        return this;
    }

    public String build(){
        return result.toString();
    }

}
