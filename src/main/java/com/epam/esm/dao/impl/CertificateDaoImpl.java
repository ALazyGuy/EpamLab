package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CertificateDaoImpl implements CertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;

    @Autowired
    public CertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
    }

    @Override
    public List<GiftCertificate> loadAll() {
        return null;
    }

    @Override
    public void create(String name, String description, double price, int duration, Date createDate, Date lastUpdateDate, List<String> tags) {
        int count = countCertificates(name);

        if(count != 0){
            return;
        }

        jdbcTemplate.update("INSERT INTO certificates (name, description, price, duration, create_date, last_update_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                new Object[]{name, description, price, duration, createDate, lastUpdateDate},
                new int[]{ Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER, Types.DATE, Types.DATE});


        int certificateId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM certificates", Integer.class);

        tags.stream().map(tagDao::create).forEach(tag -> {
            jdbcTemplate.update("INSERT INTO certificates_tags (certificate_id, tag_id) VALUES (?, ?)",
                    new Object[]{certificateId, tag.getId()},
                    new int[]{Types.INTEGER, Types.INTEGER});
        });
    }

    @Override
    public List<GiftCertificate> loadByTagName(String tag) {
        return null;
    }

    @Override
    public List<GiftCertificate> loadWhereNameLike(String name) {
        return null;
    }

    @Override
    public List<GiftCertificate> loadWhereDescriptionLike(String description) {
        return null;
    }

    @Override
    public Optional<GiftCertificate> loadById(int id) {
        return Optional.of(jdbcTemplate.query("SELECT certificates.*, GROUP_CONCAT(ct.tag_id SEPARATOR ' ') AS tId " +
                "FROM certificates JOIN" +
                " certificates_tags ct on certificates.id = ct.certificate_id" +
                " and certificates.id = ?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new CertificateMapper(tagDao))
                .stream().findAny().orElse(null));
    }

    private int countCertificates(String name){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM certificates WHERE name=?",
                new Object[]{name},
                new int[]{Types.VARCHAR},
                Integer.class);
    }
}
