package com.epam.esm.controller;

import com.epam.esm.configuration.ControllerTestConfiguration;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ControllerTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagControllerTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagController tagController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeAll
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void postEach(){
        reset(tagService);
    }

    @Test
    @SneakyThrows
    public void getAllEmptyTest() {
        when(tagService.getAll()).thenReturn(new LinkedList<>());
        mockMvc.perform(get("/v1/tag/all")).andExpect(status().is(204));

        verify(tagService, times(1)).getAll();
    }

    @Test
    @SneakyThrows
    public void getAllListTest() {
        List<Tag> tags = List.of(
                new Tag(-1, "Tag1"),
                new Tag(-2, "Tag2")
        );

        when(tagService.getAll()).thenReturn(tags);
        mockMvc.perform(get("/v1/tag/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(tags)
                ));

        verify(tagService, times(1)).getAll();
    }

}
