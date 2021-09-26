package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.dto.TagInputDTO;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.List;

@Component
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> loadAll(){
        return jdbcTemplate.query("SELECT * FROM tags", new BeanPropertyRowMapper(Tag.class));
    }

    @Override
    public boolean create(TagInputDTO tagInputDTO){
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tags WHERE name=?",
                new Object[]{tagInputDTO.getName()},
                new int[]{Types.VARCHAR},
                Integer.class);

        if(count != 0) {
            return false;
        }

        jdbcTemplate.update("INSERT INTO tags (name) VALUES (?)",
                new Object[]{tagInputDTO.getName()},
                new int[]{Types.VARCHAR});
        return true;
    }

}
