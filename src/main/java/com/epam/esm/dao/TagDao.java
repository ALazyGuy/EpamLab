package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TagDao {
    List<Tag> loadAll();
    Optional<Tag> loadByName(String name);
    Tag create(String name);
    void delete(int id);
}
