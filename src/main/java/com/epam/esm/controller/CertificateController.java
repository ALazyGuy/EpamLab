package com.epam.esm.controller;

import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAll(){
        List<GiftCertificate> certificates = certificateService.getAllCertificates();

        if(certificates.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(certificates);
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String tagName,
                                 @RequestParam(required = false, defaultValue = "") String namePart,
                                 @RequestParam(required = false, defaultValue = "") String descriptionPart){
        List<GiftCertificate> certificates = certificateService.searchBy(tagName, namePart, descriptionPart);

        if(certificates.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(certificates);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity create(@RequestBody CertificateCreateDTO certificateCreateDTO){
        if(certificateService.create(certificateCreateDTO)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        certificateService.delete(id);
    }
}
