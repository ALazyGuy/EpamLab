package com.epam.esm.dao.impl;

import com.epam.esm.builder.SQLColumnListBuilder;
import com.epam.esm.builder.SQLQueryParamBuilder;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.extractor.CertificateExtractor;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
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
        return jdbcTemplate.query("SELECT certificates.*, t.id AS tId, t.name AS tName\n" +
                        "                        FROM certificates LEFT JOIN\n" +
                        "                         certificates_tags ct ON certificates.id = ct.certificate_id\n" +
                        "                         LEFT JOIN tags t ON ct.tag_id = t.id",
                new CertificateExtractor(new CertificateMapper()));
    }

    @Override
    //TODO Change certificate id loading
    public Optional<GiftCertificate> create(String name, String description, double price, int duration, List<String> tags) {
        int count = countCertificates(name);

        if(count != 0){
            return Optional.empty();
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
            jdbcTemplate.update("INSERT INTO certificates_tags (certificate_id, tag_id) VALUES (?, ?)", certificateId, tag.getId());
        });
        return loadById(certificateId);
    }

    @Override
    public Optional<GiftCertificate> loadById(int id) {
        return Optional.ofNullable(jdbcTemplate.query("SELECT certificates.*, t.id AS tId, t.name AS tName\n" +
                                "                        FROM certificates LEFT JOIN\n" +
                                "                         certificates_tags ct ON certificates.id = ct.certificate_id\n" +
                                "                         LEFT JOIN tags t ON ct.tag_id = t.id WHERE certificates.id = ?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new CertificateExtractor(new CertificateMapper()))
                .stream().findAny().orElse(null));
    }

    @Override
    public List<GiftCertificate> search(SQLQueryParamBuilder.SQLQueryParamState sqlQueryParamState) {
        if(sqlQueryParamState.getQuery().isEmpty()){
            return loadAll();
        }

        String query = String.format("SELECT certificates.*, t.id AS tId, t.name AS tName\n" +
                "                        FROM certificates JOIN\n" +
                "                         certificates_tags ct ON certificates.id IN (SELECT DISTINCT certificate_id FROM certificates_tags\n" +
                "                                LEFT JOIN tags t on t.id = certificates_tags.tag_id %s)\n" +
                "                         LEFT JOIN tags t ON ct.tag_id = t.id", sqlQueryParamState.getQuery());
        return jdbcTemplate.query(query, new CertificateExtractor(new CertificateMapper()), sqlQueryParamState.getArgs().toArray());
    }

    @Override
    public boolean delete(int id) {
        int affected = jdbcTemplate.update("DELETE FROM certificates WHERE id = ?", id);

        if(affected == 0){
            return false;
        }

        jdbcTemplate.update("DELETE FROM certificates_tags WHERE certificate_id = ?", id);
        jdbcTemplate.update("DELETE FROM tags" +
                " LEFT JOIN certificates_tags ct on t.id = ct.tag_id " +
                "WHERE ct.tag_id IS NULL");


        return true;
    }

    @Override
    public GiftCertificate update(int id, SQLColumnListBuilder.SQLColumnListState state, List<Tag> tags) {
        List<String> values = state.getValues();
        values.add(Integer.toString(id));

        jdbcTemplate.update(
                String.format(
                    "UPDATE certificates SET %s WHERE id = ?",
                        state.getArgs()),
                values.toArray()
                );

        if(Objects.nonNull(tags)){
            jdbcTemplate.update("DELETE FROM certificates_tags WHERE certificate_id = ?", id);
            tags.forEach(t -> jdbcTemplate.update("INSERT INTO certificates_tags VALUES (?, ?)", id, t.getId()));
        }

        return loadById(id).get();
    }

    private int countCertificates(String name){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM certificates WHERE name=?",
                new Object[]{name},
                new int[]{Types.VARCHAR},
                Integer.class);
    }
}
