package com.epam.esm.dao.impl;

import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
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
        return jdbcTemplate.query("SELECT certificates.*, GROUP_CONCAT(ct.tag_id SEPARATOR ' ') AS tId " +
                        "FROM certificates JOIN" +
                        " certificates_tags ct ON certificates.id = ct.certificate_id" +
                        " GROUP BY certificates.id",
                new CertificateMapper(tagDao));
    }

    @Override
    public void create(String name, String description, double price, int duration, List<String> tags) {
        int count = countCertificates(name);

        if(count != 0){
            return;
        }

        jdbcTemplate.update("INSERT INTO certificates (name, description, price, duration, create_date, last_update_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                new Object[]{name,
                        description,
                        price,
                        duration,
                        LocalDateTime.now(),
                        LocalDateTime.now()},
                new int[]{ Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP});


        int certificateId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM certificates", Integer.class);

        tags.stream().map(t -> {
            tagDao.create(t);
            return tagDao.loadByName(t).get();
        }).forEach(tag -> {
            jdbcTemplate.update("INSERT INTO certificates_tags (certificate_id, tag_id) VALUES (?, ?)",
                    new Object[]{certificateId, tag.getId()},
                    new int[]{Types.INTEGER, Types.INTEGER});
        });
    }

    @Override
    public Optional<GiftCertificate> loadById(int id) {
        return Optional.of(jdbcTemplate.query("SELECT certificates.*, GROUP_CONCAT(ct.tag_id SEPARATOR ' ') AS tId " +
                "FROM certificates JOIN" +
                " certificates_tags ct ON certificates.id = ct.certificate_id" +
                " AND certificates.id = ? GROUP BY certificates.id",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new CertificateMapper(tagDao))
                .stream().findAny().orElse(null));
    }

    @Override
    public List<GiftCertificate> search(SQLQueryParamBuilder sqlQueryParamBuilder) {
        String query = String.format("SELECT certificates.*, GROUP_CONCAT(ct.tag_id SEPARATOR ' ') AS tId " +
                        "FROM certificates JOIN" +
                        " certificates_tags ct ON certificates.id = ct.certificate_id" +
                        " LEFT JOIN tags t on t.id = ct.tag_id%s" +
                        " GROUP BY certificates.id", sqlQueryParamBuilder.build());
        return jdbcTemplate.query(query, new CertificateMapper(tagDao));
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM certificates WHERE id = ?", id);
        jdbcTemplate.update("DELETE t FROM tags t" +
                " LEFT JOIN certificates_tags ct on t.id = ct.tag_id " +
                "WHERE ct.tag_id IS NULL");
    }

    private int countCertificates(String name){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM certificates WHERE name=?",
                new Object[]{name},
                new int[]{Types.VARCHAR},
                Integer.class);
    }
}
