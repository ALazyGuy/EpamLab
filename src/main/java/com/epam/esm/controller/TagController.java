package com.epam.esm.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAll(){
        List<Tag> tags = tagService.getAll();

        if(tags.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(tags);
    }

}
