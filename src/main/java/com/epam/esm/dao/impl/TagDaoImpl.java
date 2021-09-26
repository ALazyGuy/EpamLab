package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.dto.TagDeleteDTO;
import com.epam.esm.model.dto.TagInputDTO;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

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
    public boolean create(String name){
        int count = countByName(name);

        if(count != 0) {
            return false;
        }

        jdbcTemplate.update("INSERT INTO tags (name) VALUES (?)",
                new Object[]{name},
                new int[]{Types.VARCHAR});
        return true;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM tags WHERE id=?",
                new Object[]{id},
                new int[]{Types.INTEGER});
    }

    @Override
    public Optional<Tag> loadByName(String name) {
        int count = countByName(name);

        if(count == 0){
            return Optional.empty();
        }

        return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM tags WHERE name=?",
                new Object[]{name},
                new int[]{Types.VARCHAR}, Tag.class));
    }

    private int countByName(String name){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tags WHERE name=?",
                new Object[]{name},
                new int[]{Types.VARCHAR},
                Integer.class);
    }
}
