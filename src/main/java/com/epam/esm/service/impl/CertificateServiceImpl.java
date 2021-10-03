package com.epam.esm.service.impl;

import com.epam.esm.builder.SQLColumnListBuilder;
import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.dto.CertificateUpdateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public boolean create(CertificateCreateDTO certificateCreateDTO) {
        return certificateDao.create(certificateCreateDTO.getName(),
                certificateCreateDTO.getDescription(),
                certificateCreateDTO.getPrice(),
                certificateCreateDTO.getDuration(),
                certificateCreateDTO.getTags());
    }

    @Override
    public void delete(int id) {
        certificateDao.delete(id);
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
    public void updateCertificate(int id, CertificateUpdateDTO certificateUpdateDTO) {
        SQLColumnListBuilder sqlColumnListBuilder = SQLColumnListBuilder.init()
                .append("name", certificateUpdateDTO.getName())
                .append("description", certificateUpdateDTO.getDescription())
                .append("price", certificateUpdateDTO.getPrice())
                .append("duration", certificateUpdateDTO.getDuration());

        if(sqlColumnListBuilder.isEmpty()){
            return;
        }

        SQLColumnListBuilder.SQLColumnListState state = sqlColumnListBuilder
                .append("last_update_date", LocalDateTime.now().toString())
                .build();
        certificateDao.update(id, state);
    }

}
