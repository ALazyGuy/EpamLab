package com.epam.esm.dao;

import com.epam.esm.builder.SQLColumnListBuilder;
import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CertificateDao {
    List<GiftCertificate> loadAll();
    boolean create(String name, String description, double price, int duration, List<String> tags);
    Optional<GiftCertificate> loadById(int id);
    List<GiftCertificate> search(SQLQueryParamBuilder sqlQueryParamBuilder);
    void update(int id, SQLColumnListBuilder.SQLColumnListState state);
    void delete(int id);
}