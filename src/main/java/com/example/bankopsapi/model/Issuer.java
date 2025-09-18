package com.example.bankopsapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "issuers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issuer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "issuer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Card card;
}
