package com.example.bankopsapi.service;

import com.example.bankopsapi.dto.request.IssuerRequestDTO;
import com.example.bankopsapi.exception.exists.ExistsIssuerNameException;
import com.example.bankopsapi.exception.invalid.InvalidCardIdException;
import com.example.bankopsapi.exception.invalid.InvalidIssuerIdException;
import com.example.bankopsapi.exception.notfound.IssuerNotFoundException;
import com.example.bankopsapi.exception.notfound.NoCardFoundException;
import com.example.bankopsapi.exception.notfound.NoIssuerFoundException;
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

        if (issuerRepository.existsByName(request.name())) {
            throw new ExistsIssuerNameException();
        }

        Issuer issuer = Issuer.builder()
                .bin(request.bin())
                .name(request.name())
                .flag(request.flag())
                .country(request.country())
                .build();

        return issuerRepository.save(issuer);
    }

    public List<Issuer> listAllIssuers() {
        List<Issuer> issuers = issuerRepository.findAll();

        if (issuers.isEmpty()) {
            throw new NoIssuerFoundException();
        }

        return issuers;
    }

    public Issuer findById(Long id) {
        if (id == null || id <= 0){
            throw new InvalidIssuerIdException();
        }

        return issuerRepository.findById(id)
                .orElseThrow(IssuerNotFoundException::new);
    }

    public void deleteIssuer(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIssuerIdException();
        }

        if (!issuerRepository.existsById(id)) {
            throw new InvalidIssuerIdException();
        }

        issuerRepository.deleteById(id);
    }
}
