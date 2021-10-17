package com.epam.esm.service.impl;

import com.epam.esm.builder.SQLColumnListBuilder;
import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.dto.CertificateUpdateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;
    private final TagDao tagDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
    }

    @Override
    public Optional<GiftCertificate> create(CertificateCreateDTO certificateCreateDTO) {
        return certificateDao.create(certificateCreateDTO.getName(),
                certificateCreateDTO.getDescription(),
                certificateCreateDTO.getPrice(),
                certificateCreateDTO.getDuration(),
                certificateCreateDTO.getTags());
    }

    @Override
    public boolean delete(int id) {
        return certificateDao.delete(id);
    }

    @Override
    public List<GiftCertificate> getAllCertificates() {
        return certificateDao.loadAll();
    }

    @Override
    public List<GiftCertificate> searchBy(String tagName, String namePart, String descriptionPart) {
        SQLQueryParamBuilder sqlQueryParamBuilder = SQLQueryParamBuilder
                .initEquals("t.name", tagName)
                .like("certificates.name", namePart)
                .like("certificates.description", descriptionPart);
        return certificateDao.search(sqlQueryParamBuilder.build());
    }

    @Override
    @Transactional
    public Optional<GiftCertificate> updateCertificate(int id, CertificateUpdateDTO certificateUpdateDTO) {
        if(certificateDao.loadById(id).isEmpty()){
            return Optional.empty();
        }

        List<Tag> tags = null;

        if(!certificateUpdateDTO.getTags().isEmpty()) {
            tags = certificateUpdateDTO
                    .getTags()
                    .stream()
                    .map(t -> tagDao.create(t))
                    .collect(Collectors.toList());
        }

        SQLColumnListBuilder sqlColumnListBuilder = SQLColumnListBuilder.init()
                .append("name", certificateUpdateDTO.getName())
                .append("description", certificateUpdateDTO.getDescription())
                .append("price", certificateUpdateDTO.getPrice())
                .append("duration", certificateUpdateDTO.getDuration());

        GiftCertificate result = null;

        if(!sqlColumnListBuilder.isEmpty()){
            SQLColumnListBuilder.SQLColumnListState state = sqlColumnListBuilder
                    .append("last_update_date", LocalDateTime.now().toString())
                    .build();
            result = certificateDao.update(id, state, tags);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<GiftCertificate> getById(int id) {
        return certificateDao.loadById(id);
    }
}
