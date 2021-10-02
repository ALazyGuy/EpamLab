package com.epam.esm.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class SQLColumnListBuilder {

    @Data
    @AllArgsConstructor
    public static class SQLColumnListState{
        private String args;
        private List<String> values;
    }

    private Map<String, String> columns = new HashMap<>();

    public static SQLColumnListBuilder init(){
        return new SQLColumnListBuilder();
    }

    public SQLColumnListBuilder append(String column, double value){
        if(value == 0){
            return this;
        }

        return append(column, Double.toString(value));
    }

    public SQLColumnListBuilder append(String column, String value){
        if(!value.isEmpty()){
            columns.put(column, value);
        }
        return this;
    }

    public boolean isEmpty(){
        return columns.isEmpty();
    }

    public SQLColumnListState build(){
        String args = columns.keySet()
                .stream()
                .map(field -> String.format("%s = ?", field))
                .collect(Collectors.joining(", "));
        return new SQLColumnListState(args, columns.values().stream().collect(Collectors.toList()));
    }

}
