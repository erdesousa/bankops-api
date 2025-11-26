package com.example.bankopsapi.service;

import com.example.bankopsapi.dto.request.IssuerRequestDTO;
import com.example.bankopsapi.exception.exists.ExistsIssuerNameException;
import com.example.bankopsapi.exception.invalid.InvalidIssuerIdException;
import com.example.bankopsapi.exception.notfound.IssuerNotFoundException;
import com.example.bankopsapi.exception.notfound.NoIssuerFoundException;
import com.example.bankopsapi.model.Issuer;
import com.example.bankopsapi.repository.IssuerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para IssuerService")
class IssuerServiceTest {
    @Mock
    private IssuerRepository issuerRepository;

    @InjectMocks
    private IssuerService issuerService;

    private IssuerRequestDTO issuerRequestDTO;
    private Issuer mockIssuer;

    @BeforeEach
    void setUp() {
        issuerRequestDTO = new IssuerRequestDTO(
                "123456",
                "Bankops Issuer",
                "VISA",
                "BR"
        );

        mockIssuer = Issuer.builder()
                .id(1L)
                .bin("123456")
                .name("BankOps Issuer")
                .flag("VISA")
                .country("BR")
                .build();
    }

    // --- Testes para createIssuer ---

    @Test
    @DisplayName("Deve criar um Issuer com sucesso")
    void createIssuer_ShouldCreateIssuerSuccessfully() {
        when(issuerRepository.existsByName(issuerRequestDTO.name())).thenReturn(false);
        when(issuerRepository.save(any(Issuer.class))).thenReturn(mockIssuer);

        Issuer createdIssuer = issuerService.createIssuer(issuerRequestDTO);

        assertNotNull(createdIssuer);
        assertEquals(mockIssuer.getName(), createdIssuer.getName());

        verify(issuerRepository, times(1)).existsByName(issuerRequestDTO.name());
        verify(issuerRepository, times(1)).save(any(Issuer.class));
    }

    @Test
    @DisplayName("Deve lançar ExistsIssuerNameException se o nome do Issuer já existir")
    void createIssuer_ShouldThrowExistsIssuerNameException_WhenNameExists() {
        when(issuerRepository.existsByName(issuerRequestDTO.name())).thenReturn(true);

        assertThrows(ExistsIssuerNameException.class, () -> issuerService.createIssuer(issuerRequestDTO));

        verify(issuerRepository, times(1)).existsByName(issuerRequestDTO.name());
        verify(issuerRepository, never()).save(any(Issuer.class));
    }

    // --- Testes para listAllIssuers ---

    @Test
    @DisplayName("Deve retornar uma lista de Issuers quando houver dados")
    void listAllIssuers_ShouldReturnListOfIssuers() {
        List<Issuer> issuers = List.of(mockIssuer, Issuer.builder().id(2L).name("Another Issuer").build());
        when(issuerRepository.findAll()).thenReturn(issuers);

        List<Issuer> result = issuerService.listAllIssuers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        verify(issuerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve lançar NoIssuerFoundException quando a lista de Issuers estiver vazia")
    void listAllIssuers_ShouldThrowNoIssuerFoundException_WhenListIsEmpty() {
        when(issuerRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoIssuerFoundException.class, () -> issuerService.listAllIssuers());

        verify(issuerRepository, times(1)).findAll();
    }

    // --- Testes para findById ---

    @Test
    @DisplayName("Deve retornar um Issuer quando o ID for válido e encontrado")
    void findById_ShouldReturnIssuer_WhenIdIsValidAndFound() {
        Long issuerId = 1L;
        when(issuerRepository.findById(issuerId)).thenReturn(Optional.of(mockIssuer));

        Issuer result = issuerService.findById(issuerId);

        assertNotNull(result);
        assertEquals(issuerId, result.getId());

        verify(issuerRepository, times(1)).findById(issuerId);
    }

    @Test
    @DisplayName("Deve lançar InvalidIssuerIdException quando o ID for nulo ou inválido")
    void findById_ShouldThrowInvalidIssuerIdException_WhenIdIsInvalid() {
        assertThrows(InvalidIssuerIdException.class, () -> issuerService.findById(null));
        assertThrows(InvalidIssuerIdException.class, () -> issuerService.findById(0L));
        assertThrows(InvalidIssuerIdException.class, () -> issuerService.findById(-1L));

        verify(issuerRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar IssuerNotFoundException quando o Issuer não for encontrado")
    void findById_ShouldThrowIssuerNotFoundException_WhenIssuerNotFound() {
        Long issuerId = 99L;
        when(issuerRepository.findById(issuerId)).thenReturn(Optional.empty());

        assertThrows(IssuerNotFoundException.class, () -> issuerService.findById(issuerId));

        verify(issuerRepository, times(1)).findById(issuerId);
    }

    // --- Testes para deleteIssuer ---

    @Test
    @DisplayName("Deve deletar um Issuer com sucesso")
    void deleteIssuer_ShouldDeleteIssuerSuccessfully() {
        Long issuerId = 1L;
        when(issuerRepository.existsById(issuerId)).thenReturn(true);
        doNothing().when(issuerRepository).deleteById(issuerId);

        issuerService.deleteIssuer(issuerId);

        verify(issuerRepository, times(1)).existsById(issuerId);
        verify(issuerRepository, times(1)).deleteById(issuerId);
    }

    @Test
    @DisplayName("Deve lançar InvalidIssuerIdException ao tentar deletar com ID nulo ou inválido")
    void deleteIssuer_ShouldThrowInvalidIssuerIdException_WhenIdIsInvalid() {
        assertThrows(InvalidIssuerIdException.class, () -> issuerService.deleteIssuer(null));
        assertThrows(InvalidIssuerIdException.class, () -> issuerService.deleteIssuer(0L));

        verify(issuerRepository, never()).existsById(anyLong());
        verify(issuerRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar InvalidIssuerIdException se o Issuer não existir ao tentar deletar")
    void deleteIssuer_ShouldThrowInvalidIssuerIdException_WhenIssuerDoesNotExist() {
        Long issuerId = 99L;
        when(issuerRepository.existsById(issuerId)).thenReturn(false);

        assertThrows(InvalidIssuerIdException.class, () -> issuerService.deleteIssuer(issuerId));

        verify(issuerRepository, times(1)).existsById(issuerId);
        verify(issuerRepository, never()).deleteById(anyLong());
    }
}
