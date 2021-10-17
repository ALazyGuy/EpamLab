package com.epam.esm.dao.impl;

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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        certificateDao.create("C1", "Desk1", 10, 10, List.of());
        List<GiftCertificate> certificates = certificateDao.loadAll();
        assertEquals(1, certificates.size());
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