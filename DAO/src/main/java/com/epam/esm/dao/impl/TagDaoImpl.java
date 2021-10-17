package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
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
    public Tag create(String name){
        int count = countByName(name);

        if(count != 0) {
            return loadByName(name).get();
        }

        jdbcTemplate.update("INSERT INTO tags (name) VALUES (?)",
                new Object[]{name},
                new int[]{Types.VARCHAR});
        return loadByName(name).get();
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM tags WHERE id=?",
                new Object[]{id},
                new int[]{Types.INTEGER});
    }

    @Override
    public Optional<Tag> loadByName(String name) {
        return Optional.ofNullable(loadAll()
                .stream()
                .filter(tag -> tag.getName().equals(name))
                .findAny().orElse(null));
    }

    @Override
    public Optional<Tag> loadById(int id) {
        return Optional.ofNullable(loadAll()
                .stream()
                .filter(tag -> tag.getId() == id)
                .findAny().orElse(null));
    }

    private int countByName(String name){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tags WHERE name=?",
                new Object[]{name},
                new int[]{Types.VARCHAR},
                Integer.class);
    }
}
