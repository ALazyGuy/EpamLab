package com.epam.esm.service;

import com.epam.esm.model.dto.TagDeleteDTO;
import com.epam.esm.model.dto.TagInputDTO;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    void create(TagInputDTO tagInputDTO);
    List<Tag> getAll();
    void delete(TagDeleteDTO tagDeleteDTO);
}
