package com.example.bankopsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Bin can´t be empty")
    @Column(nullable = false)
    private String bin;

    @NotBlank(message = "Name can´t be empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Flag can´t be empty")
    @Column(nullable = false)
    private String flag;

    @NotBlank(message = "Country can´t be empty")
    @Column(nullable = false)
    private String country;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "issuer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Card card;
}
