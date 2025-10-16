package com.example.bankopsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name can´t be empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Card type can´t be empty")
    @Column(nullable = false)
    private String cardType;

    @NotBlank(message = "Pin attempts can´t be empty")
    @Column(nullable = false)
    private Integer pinAttempts;

    @NotBlank(message = "Validity can´t be empty")
    @Column(nullable = false)
    private String validity;

    @NotBlank(message = "Security code can´t be empty")
    @Column(nullable = false)
    private String securityCode;

    @NotBlank(message = "Accept approach can´t be empty")
    @Column(nullable = false)
    private Boolean acceptApproach;

    @NotBlank(message = "Online purchase can´t be empty")
    @Column(nullable = false)
    private Boolean onlinePurchase;

    @NotBlank(message = "Card international can´t be empty")
    @Column(nullable = false)
    private Boolean cardInternational;

    @NotBlank(message = "Issuer id can´t be empty")
    @OneToOne
    @JoinColumn(name = "issuer_id", nullable = false, unique = true)
    private Issuer issuer;
}
