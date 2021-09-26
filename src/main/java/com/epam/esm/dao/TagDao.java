package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> loadAll();
    Optional<Tag> loadByName(String name);
    boolean create(String name);
    void delete(int id);
}
