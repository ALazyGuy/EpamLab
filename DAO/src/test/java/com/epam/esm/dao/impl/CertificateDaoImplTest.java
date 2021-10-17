package com.epam.esm.dao.impl;

import com.epam.esm.builder.SQLColumnListBuilder;
import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.configuration.DaoTestConfiguration;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CertificateDaoImplTest {

    @Autowired
    private CertificateDao certificateDao;
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
        runScript("certificate/certificates.sql");
        List<GiftCertificate> certificates = certificateDao.loadAll();
        assertEquals(5, certificates.size());
    }

    @Test
    public void createFailTest(){
        runScript("certificate/certificates.sql");
        boolean result = certificateDao.create("C1", "D1", 1, 1, null).isPresent();
        assertFalse(result);
    }

    @Test
    public void createSuccessTest(){
        runScript("certificate/certificates.sql");
        boolean result = certificateDao.create("C10", "D1", 1, 1, List.of()).isPresent();
        assertTrue(result);
    }

    @Test
    public void loadByIdFailTest(){
        runScript("certificate/certificates.sql");
        boolean result  = certificateDao.loadById(100).isPresent();
        assertFalse(result);
    }

    @Test
    public void loadByIdSuccessTest(){
        runScript("certificate/certificates.sql");
        GiftCertificate result = certificateDao.loadById(1).get();
        assertEquals(1, result.getId());
    }

    @Test
    public void deleteFailTest(){
        boolean deleteResult = certificateDao.delete(1);
        assertFalse(deleteResult);
    }

    @Test
    public void deleteSuccessTest(){
        runScript("certificate/certificates.sql");
        boolean deleteResult = certificateDao.delete(1);
        assertTrue(deleteResult);
    }

    @Test
    public void searchTest(){
        runScript("certificate/certificates.sql");
        runScript("certificate/certificates_tags.sql");
        runScript("tag/tags.sql");
        SQLQueryParamBuilder.SQLQueryParamState state = SQLQueryParamBuilder
                .initEquals("t.name", "tag2")
                .like("certificates.description", "es").build();
        System.out.println("=".repeat(100));
        System.out.println(state.toString());
        List<GiftCertificate> found = certificateDao.search(state);
        assertEquals(2, found.size());
    }

    @Test
    public void updateFailTest(){
        SQLColumnListBuilder.SQLColumnListState state = SQLColumnListBuilder
                .init()
                .append("description", "1")
                .build();
        boolean result = certificateDao.update(1, state, List.of()).isEmpty();
        assertTrue(result);
    }

    @Test
    public void updateSuccessTest(){
        runScript("certificate/certificates.sql");
        SQLColumnListBuilder.SQLColumnListState state = SQLColumnListBuilder
                .init()
                .append("description", "1")
                .build();
        boolean result = certificateDao.update(1, state, List.of()).isEmpty();
        assertFalse(result);
    }

    @SneakyThrows
    private void runScript(String name){
        String path = "/home/razor/Lab/task2/SecondModule2/DAO/src/test/resources/" + name;
        String code = Files.readAllLines(Paths.get(path))
                .stream()
                .collect(Collectors.joining());
        jdbcTemplate.update(code);
    }

}