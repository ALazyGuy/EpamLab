package com.epam.esm.dao;

import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public interface CertificateDao {
    List<GiftCertificate> loadAll();
    Optional<GiftCertificate> loadById(int id);
    void create(CertificateCreateDTO certificateCreateDTO);
    List<GiftCertificate> search(String tagName, String namePart, String descriptionPart);
}