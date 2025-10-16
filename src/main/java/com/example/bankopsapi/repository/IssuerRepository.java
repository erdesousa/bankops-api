package com.example.bankopsapi.repository;

import com.example.bankopsapi.model.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Long> {
    boolean findByName(String name);
}
