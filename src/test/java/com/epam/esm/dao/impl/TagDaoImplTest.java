package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DaoTestConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.swing.text.html.Option;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows
    @BeforeEach
    public void init(){
        //I WILL FCKING BREAK MY KEYBOARD NEXT FCKING TIME
        runScript("clear.sql");
        runScript("initialTest.sql");
    }

    @Test
    public void loadAllTest(){
        runScript("tag/tags.sql");
        List<Tag> tags = tagDao.loadAll();
        assertEquals(10, tags.size());
    }

    @Test
    public void createTest(){
        tagDao.create("Tag1");
        Tag tag = tagDao.loadAll().get(0);
        assertEquals("Tag1", tag.getName());
    }

    @Test
    public void deleteTest(){
        runScript("tag/tags.sql");
        tagDao.delete(5);
        assertTrue(tagDao.loadAll().stream().allMatch(tag -> tag.getId() != 5));
    }

    @Test
    public void loadByNameTest(){
        runScript("tag/tags.sql");
        Optional<Tag> tag = tagDao.loadByName("tag1");
        if(tag.isEmpty()){
            fail();
        }
        assertEquals("tag1", tag.get().getName());
    }

    @Test
    public void loadByNameFailTest(){
        runScript("tag/tags.sql");
        Optional<Tag> tag = tagDao.loadByName("tag1qwe");
        if(tag.isEmpty()){
            return;
        }
        fail();
    }

    @Test
    public void loadByIdTest(){
        runScript("tag/tags.sql");
        Optional<Tag> tag = tagDao.loadById(3);
        if(tag.isEmpty()){
            fail();
        }
        assertEquals(3, tag.get().getId());
    }

    @Test
    public void loadByIdFailTest(){
        runScript("tag/tags.sql");
        Optional<Tag> tag = tagDao.loadById(100);
        if(tag.isEmpty()){
            return;
        }
        fail();
    }

    @SneakyThrows
    private void runScript(String name){
        String path = "/home/razor/Lab/task2/SecondModule/src/test/resources/" + name;
        String code = Files.readAllLines(Paths.get(path))
                .stream()
                .collect(Collectors.joining());
        jdbcTemplate.update(code);
    }

}
