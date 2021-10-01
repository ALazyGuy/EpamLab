package com.epam.esm.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class CertificateCreateDTO {
    private String name;
    private String description;
    private double price;
    private int duration;
    private List<String> tags = new LinkedList<>();
}
