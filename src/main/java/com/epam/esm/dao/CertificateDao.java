package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public interface CertificateDao {
    List<GiftCertificate> loadAll();
    Optional<GiftCertificate> loadById(int id);
    void create(String name, String description,
                double price, int duration,
                Date createDate, Date lastUpdateDate, List<String> tags);
    List<GiftCertificate> loadByTagName(String tag);
    List<GiftCertificate> loadWhereNameLike(String name);
    List<GiftCertificate> loadWhereDescriptionLike(String description);
    //TODO Add update method
}