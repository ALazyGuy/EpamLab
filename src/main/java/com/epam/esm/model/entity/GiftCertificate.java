package com.epam.esm.model.entity;

import com.epam.esm.util.LocalDateDeserializer;
import com.epam.esm.util.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime createDate;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime lastUpdateDate;
    private List<Tag> tags = new LinkedList<>();

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public void removeTag(Tag tag){
        this.tags.remove(tag);
    }
}
