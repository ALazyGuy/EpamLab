package com.epam.esm.controller;

import com.epam.esm.model.dto.CertificateCreateDTO;
import com.epam.esm.model.dto.CertificateUpdateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.CertificateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Certificate Controller")
@RestController
@RequestMapping("/v1/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @ApiOperation("Search certificates by optional parameters")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If certificates found successfully", response = GiftCertificate.class),
            @ApiResponse(code = 204, message = "If no certificates found"),
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String tagName,
                                 @RequestParam(required = false, defaultValue = "") String namePart,
                                 @RequestParam(required = false, defaultValue = "") String descriptionPart){
        List<GiftCertificate> certificates = certificateService.searchBy(tagName, namePart, descriptionPart);

        if(certificates.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(certificates);
    }

    @ApiOperation("Create a new certificate")
    @ApiResponses({
            @ApiResponse(code = 201, message = "If created successfully"),
            @ApiResponse(code = 409, message = "If certificate with same name already exists"),
            @ApiResponse(code = 400, message = "If JSON object in request body is invalid")
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity create(@Valid @RequestBody CertificateCreateDTO certificateCreateDTO){
        if(!certificateService.create(certificateCreateDTO)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("Delete certificate by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "On successful deletion"),
            @ApiResponse(code = 404, message = "Certificate with given id doesn't exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id){
        if(certificateService.delete(id)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


    @ApiOperation("Update certificate by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "If updated successfully or certificate doesn't exist"),
            @ApiResponse(code = 400, message = "If JSON object in request body is invalid"),
            @ApiResponse(code = 404, message = "Certificate with given id doesn't exist")
    })
    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update(@PathVariable int id, @Valid @RequestBody CertificateUpdateDTO certificateUpdateDTO){
        if(certificateService.updateCertificate(id, certificateUpdateDTO)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
