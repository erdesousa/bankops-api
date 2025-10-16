package com.example.bankopsapi.controller;

import com.example.bankopsapi.model.Issuer;
import com.example.bankopsapi.service.IssuerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/issuers")
@RequiredArgsConstructor
public class IssuerController {

    private final IssuerService issuerService;

    @PostMapping
    public ResponseEntity<Issuer> createIssuer(@RequestBody Issuer issuer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issuerService.createIssuer(issuer));
    }

    @GetMapping
    public ResponseEntity<List<Issuer>> findAllIssuers() {
        return ResponseEntity.ok(issuerService.listAllIssuers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issuer> findByIdIssuer(@PathVariable Long id) {
        return ResponseEntity.ok(issuerService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssuer(@PathVariable Long id) {
        issuerService.deleteIssuer(id);
        return ResponseEntity.noContent().build();
    }
}
