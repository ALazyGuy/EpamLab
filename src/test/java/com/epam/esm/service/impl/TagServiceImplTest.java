package com.epam.esm.service.impl;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TagServiceImplTest {

    private static final List<Tag> tags = List.of(new Tag(1, "tag1"), new Tag(1, "tag2"));

    @Autowired
    private TagService tagService;
    @Autowired
    private TagDao tagDao;

    @Test
    public void getAllTest(){
        when(tagDao.loadAll()).thenReturn(tags);
        List<Tag> actual = tagService.getAll();
        assertEquals(tags, actual);
    }

}
