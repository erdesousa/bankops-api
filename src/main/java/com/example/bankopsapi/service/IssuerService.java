package com.example.bankopsapi.service;

import com.example.bankopsapi.dto.request.IssuerRequestDTO;
import com.example.bankopsapi.exception.IssuerNotFoundException;
import com.example.bankopsapi.model.Issuer;
import com.example.bankopsapi.repository.IssuerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssuerService {

    private final IssuerRepository issuerRepository;

    public Issuer createIssuer(IssuerRequestDTO request) {
        Issuer issuer = Issuer.builder()
                .bin(request.bin())
                .name(request.name())
                .flag(request.flag())
                .country(request.country())
                .build();

        return issuerRepository.save(issuer);
    }

    public List<Issuer> listAllIssuers() {
        return issuerRepository.findAll();
    }

    public Issuer findById(Long id) {
        return issuerRepository.findById(id)
                .orElseThrow(IssuerNotFoundException::new);
    }

    public void deleteIssuer(Long id) {
        issuerRepository.deleteById(id);
    }
}
