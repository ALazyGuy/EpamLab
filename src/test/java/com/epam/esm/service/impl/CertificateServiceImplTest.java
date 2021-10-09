package com.epam.esm.service.impl;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.CertificateService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CertificateServiceImplTest {

    private static List<GiftCertificate> all;
    private static List<GiftCertificate> searchResult;

    @Autowired
    private CertificateService certificateService;
    @Autowired
    private CertificateDao certificateDao;

    @BeforeAll
    public void init(){
        all = List.of(
                new GiftCertificate(-1, "Certificate1", "Description1",
                        1, 1, null, null, null),
                new GiftCertificate(-2, "Certificate2", "Description2",
                        2, 2, null, null, null),
                new GiftCertificate(-3, "Certificate3", "Description3",
                        3, 3, null, null, null),
                new GiftCertificate(-4, "Certificate4", "Description4",
                        4, 4, null, null, null),
                new GiftCertificate(-5, "Certificate5", "Description5",
                        5, 5, null, null, null)
        );

        searchResult = List.of(
                new GiftCertificate(-1, "Certificate1", "Description1",
                        1, 1, null, null, List.of(
                        new Tag(-1, "Tag1"),
                        new Tag(-1, "Tag2")
                )),
                new GiftCertificate(-2, "Certificate2", "Description2",
                        2, 2, null, null, List.of(
                        new Tag(-1, "Tag1"),
                        new Tag(-1, "Tag2")
                )),
                new GiftCertificate(-3, "Certificate3", "Description3",
                        3, 3, null, null, List.of(
                        new Tag(-1, "Tag1"),
                        new Tag(-1, "Tag2")
                ))
        );

        when(certificateDao.loadAll()).thenReturn(all);
        when(certificateDao.search(any())).thenReturn(searchResult);
    }

    @Test
    public void getAllCertificatesTest(){
        List<GiftCertificate> actual = certificateService.getAllCertificates();
        assertEquals(all, actual);
    }

    @Test
    public void searchByTest(){
        List<GiftCertificate> actual = certificateService.searchBy("", "", "");
        assertEquals(searchResult, actual);
    }

}
