package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> loadAll(){
        return jdbcTemplate.query("SELECT * FROM tags", new BeanPropertyRowMapper(Tag.class));
    }

}
