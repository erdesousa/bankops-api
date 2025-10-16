package com.example.bankopsapi.service;

import com.example.bankopsapi.dto.request.CardRequestDTO;
import com.example.bankopsapi.exception.*;
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
        if (cardRepository.existsById(request.issuerId())){
            throw new ExistsCardIssuerException();
        }

        Issuer issuer = issuerRepository.findById(request.issuerId())
                .orElseThrow(IssuerNotFoundException::new);

        Card card = Card.builder()
                .name(request.name())
                .cardType(request.cardType())
                .pinAttempts(request.pinAttempts())
                .validity(request.validity())
                .securityCode(request.securityCode())
                .acceptApproach(request.acceptApproach())
                .onlinePurchase(request.onlinePurchase())
                .cardInternational(request.cardInternational())
                .issuer(issuer)
                .build();

        return cardRepository.save(card);
    }

    public List<Card> listAllCards() {
        List<Card> cards = cardRepository.findAll();

        if (cards.isEmpty()) {
            throw new NoCardFoundException();
        }

        return cards;
    }

    public Card findById(Long id) {
        if (id == null || id <= 0){
            throw new InvalidCardIdException();
        }

        return cardRepository.findById(id)
                .orElseThrow(CardNotFoundException::new);
    }

    public void deleteCard(Long id) {
        if (id == null || id <= 0){
            throw new InvalidCardIdException();
        }

        if (!cardRepository.existsById(id)) {
            throw new InvalidCardIdException();
        }

        cardRepository.deleteById(id);
    }
}
