package com.example.bankopsapi.service;

import com.example.bankopsapi.dto.request.CardRequestDTO;
import com.example.bankopsapi.exception.exists.ExistsCardIssuerException;
import com.example.bankopsapi.exception.invalid.InvalidCardIdException;
import com.example.bankopsapi.exception.notfound.CardNotFoundException;
import com.example.bankopsapi.exception.notfound.IssuerNotFoundException;
import com.example.bankopsapi.exception.notfound.NoCardFoundException;
import com.example.bankopsapi.model.Card;
import com.example.bankopsapi.model.Issuer;
import com.example.bankopsapi.repository.CardRepository;
import com.example.bankopsapi.repository.IssuerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para CardService")
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private IssuerRepository issuerRepository;

    @InjectMocks
    private CardService cardService;

    private CardRequestDTO cardRequestDTO;
    private Issuer mockIssuer;
    private Card mockCard;

    @BeforeEach
    void setUp() {
        cardRequestDTO = new CardRequestDTO(
                "Visa Platinum",
                "CREDIT",
                3,
                "12/25",
                "123",
                true,
                true,
                false,
                1L // issuerId
        );

        mockIssuer = Issuer.builder()
                .id(1L)
                .name("Bankops Issuer")
                .build();

        mockCard = Card.builder()
                .id(10L)
                .name("Visa Platinum")
                .issuer(mockIssuer)
                .build();
    }

    // --- Testes para createCard ---

    @Test
    @DisplayName("Deve criar um cartão com sucesso")
    void createCard_ShouldCreateCardSuccessfully() {
        when(cardRepository.existsById(cardRequestDTO.issuerId())).thenReturn(false);
        when(issuerRepository.findById(cardRequestDTO.issuerId())).thenReturn(Optional.of(mockIssuer));
        when(cardRepository.save(any(Card.class))).thenReturn(mockCard);

        Card createdCard = cardService.createCard(cardRequestDTO);

        assertNotNull(createdCard);
        assertEquals(mockCard.getName(), createdCard.getName());
        assertEquals(mockIssuer.getId(), createdCard.getIssuer().getId());

        verify(cardRepository, times(1)).existsById(cardRequestDTO.issuerId());
        verify(issuerRepository, times(1)).findById(cardRequestDTO.issuerId());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Deve lançar ExistsCardIssuerException se o IssuerId já estiver em uso")
    void createCard_ShouldThrowExistsCardIssuerException_WhenIssuerIdExists() {
        when(cardRepository.existsById(cardRequestDTO.issuerId())).thenReturn(true);

        assertThrows(ExistsCardIssuerException.class, () -> cardService.createCard(cardRequestDTO));

        verify(cardRepository, times(1)).existsById(cardRequestDTO.issuerId());
        verify(issuerRepository, never()).findById(anyLong());
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Deve lançar IssuerNotFoundException se o Issuer não for encontrado")
    void createCard_ShouldThrowIssuerNotFoundException_WhenIssuerNotFound() {
        when(cardRepository.existsById(cardRequestDTO.issuerId())).thenReturn(false);
        when(issuerRepository.findById(cardRequestDTO.issuerId())).thenReturn(Optional.empty());

        assertThrows(IssuerNotFoundException.class, () -> cardService.createCard(cardRequestDTO));

        verify(cardRepository, times(1)).existsById(cardRequestDTO.issuerId());
        verify(issuerRepository, times(1)).findById(cardRequestDTO.issuerId());
        verify(cardRepository, never()).save(any(Card.class));
    }

    // --- Testes para listAllCards ---

    @Test
    @DisplayName("Deve retornar uma lista de cartões quando houver dados")
    void listAllCards_ShouldReturnListOfCards() {
        List<Card> cards = List.of(mockCard, Card.builder().id(11L).name("Mastercard").build());
        when(cardRepository.findAll()).thenReturn(cards);

        List<Card> result = cardService.listAllCards();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        verify(cardRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve lançar NoCardFoundException quando a lista de cartões estiver vazia")
    void listAllCards_ShouldThrowNoCardFoundException_WhenListIsEmpty() {
        when(cardRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoCardFoundException.class, () -> cardService.listAllCards());

        verify(cardRepository, times(1)).findAll();
    }

    // --- Testes para findById ---

    @Test
    @DisplayName("Deve retornar um cartão quando o ID for válido e encontrado")
    void findById_ShouldReturnCard_WhenIdIsValidAndFound() {
        Long cardId = 10L;
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(mockCard));

        Card result = cardService.findById(cardId);

        assertNotNull(result);
        assertEquals(cardId, result.getId());

        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    @DisplayName("Deve lançar InvalidCardIdException quando o ID for nulo ou inválido")
    void findById_ShouldThrowInvalidCardIdException_WhenIdIsInvalid() {
        assertThrows(InvalidCardIdException.class, () -> cardService.findById(null));
        assertThrows(InvalidCardIdException.class, () -> cardService.findById(0L));
        assertThrows(InvalidCardIdException.class, () -> cardService.findById(-1L));

        verify(cardRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar CardNotFoundException quando o cartão não for encontrado")
    void findById_ShouldThrowCardNotFoundException_WhenCardNotFound() {
        Long cardId = 99L;
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardService.findById(cardId));

        verify(cardRepository, times(1)).findById(cardId);
    }

    // --- Testes para deleteCard ---

    @Test
    @DisplayName("Deve deletar um cartão com sucesso")
    void deleteCard_ShouldDeleteCardSuccessfully() {
        Long cardId = 10L;
        when(cardRepository.existsById(cardId)).thenReturn(true);
        doNothing().when(cardRepository).deleteById(cardId);

        cardService.deleteCard(cardId);

        verify(cardRepository, times(1)).existsById(cardId);
        verify(cardRepository, times(1)).deleteById(cardId);
    }

    @Test
    @DisplayName("Deve lançar InvalidCardIdException ao tentar deletar com ID nulo ou inválido")
    void deleteCard_ShouldThrowInvalidCardIdException_WhenIdIsInvalid() {
        assertThrows(InvalidCardIdException.class, () -> cardService.deleteCard(null));

        assertThrows(InvalidCardIdException.class, () -> cardService.deleteCard(0L));

        verify(cardRepository, never()).existsById(anyLong());
        verify(cardRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar InvalidCardIdException se o cartão não existir ao tentar deletar")
    void deleteCard_ShouldThrowInvalidCardIdException_WhenCardDoesNotExist() {
        Long cardId = 99L;
        when(cardRepository.existsById(cardId)).thenReturn(false);

        assertThrows(InvalidCardIdException.class, () -> cardService.deleteCard(cardId));

        verify(cardRepository, times(1)).existsById(cardId);
        verify(cardRepository, never()).deleteById(anyLong());
    }

}
