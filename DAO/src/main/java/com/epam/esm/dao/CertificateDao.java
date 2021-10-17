package com.epam.esm.dao;

import com.epam.esm.builder.SQLColumnListBuilder;
import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateDao {
    List<GiftCertificate> loadAll();
    Optional<GiftCertificate> create(String name, String description, double price, int duration, List<String> tags);
    Optional<GiftCertificate> loadById(int id);
    List<GiftCertificate> search(SQLQueryParamBuilder.SQLQueryParamState sqlQueryParamState);
    GiftCertificate update(int id, SQLColumnListBuilder.SQLColumnListState state, List<Tag> tags);
    boolean delete(int id);
}