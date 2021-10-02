package com.epam.esm.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class CertificateCreateDTO {
    @NotBlank(message = "Name of certificate cannot be empty")
    @Pattern(regexp = "^([a-zA-Z0-9]|\\s|[.,!?\\n])+$", message = "Invalid name of certificate")
    private String name;
    @NotBlank(message = "Description of certificate cannot be empty")
    @Pattern(regexp = "^([a-zA-Z0-9]|\\s|[.,!?\\n])+$", message = "Invalid description of certificate")
    private String description;
    @Min(value = 0, message = "Price cannot be negative")
    private double price;
    @Min(value = 1, message = "Duration cannot be less than one day")
    private int duration;
    private List<String> tags = new LinkedList<>();
}
