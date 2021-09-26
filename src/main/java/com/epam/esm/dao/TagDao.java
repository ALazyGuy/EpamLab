package com.epam.esm.dao;

import com.epam.esm.model.dto.TagInputDTO;
import com.epam.esm.model.entity.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> loadAll();
    boolean create(TagInputDTO tagInputDTO);
}
