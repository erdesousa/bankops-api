package com.example.bankopsapi.service;

import com.example.bankopsapi.dto.CardRequestDTO;
import com.example.bankopsapi.model.Card;
import com.example.bankopsapi.model.Issuer;
import com.example.bankopsapi.repository.CardRepository;
import com.example.bankopsapi.repository.IssuerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final IssuerRepository issuerRepository;

    public Card createCard(CardRequestDTO request) {
        Issuer issuer = issuerRepository.findById(request.issuerId())
                .orElseThrow(() -> new RuntimeException("Emissor não encontrado."));

        Card card = Card.builder()
                .name(request.name())
                .issuer(issuer)
                .build();

        return cardRepository.save(card);
    }

    public List<Card> listAllCards() {
        return cardRepository.findAll();
    }

    public Card findById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado."));
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
