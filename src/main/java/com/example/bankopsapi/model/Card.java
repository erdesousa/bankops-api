package com.example.bankopsapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cardType;

    @Column(nullable = false)
    private Integer pinAttempts;

    @Column(nullable = false)
    private String validity;

    @Column(nullable = false)
    private String securityCode;

    @Column(nullable = false)
    private Boolean acceptApproach;

    @Column(nullable = false)
    private Boolean onlinePurchase;

    @Column(nullable = false)
    private Boolean cardInternational;

    @OneToOne
    @JoinColumn(name = "issuer_id", nullable = false, unique = true)
    private Issuer issuer;
}
