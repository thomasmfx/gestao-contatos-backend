package com.comerciosa.gestao_contatos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.comerciosa.gestao_contatos.dto.response.EnderecoResponseDTO;
import com.comerciosa.gestao_contatos.exception.ResourceNotFoundException;
import com.comerciosa.gestao_contatos.model.EnderecoViaCep;
import com.comerciosa.gestao_contatos.mapper.EnderecoViaCepMapper;

@ExtendWith(MockitoExtension.class)
class EnderecoViaCepServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EnderecoViaCepMapper enderecoViaCepMapper;

    private EnderecoViaCepService enderecoViaCepService;

    @BeforeEach
    void setUp() {
        enderecoViaCepService = new EnderecoViaCepService(restTemplate, enderecoViaCepMapper);
    }

    @Test
    @DisplayName("Deve retornar todas as informações do endereço caso CEP seja válido")
    void getEndereco_CepValido_DeveRetornarEnderecoCompleto() {
        String cep = "08410280";
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        EnderecoViaCep enderecoViaCepMock = EnderecoViaCep.builder()
                .cep(cep)
                .uf("SP")
                .localidade("São Paulo")
                .logradouro("Rua Tupinambaranas")
                .build();

        EnderecoResponseDTO expectedDtoMock = new EnderecoResponseDTO(
                "Rua Tupinambaranas",
                null,
                "São Paulo",
                "SP",
                cep);

        when(restTemplate.getForObject(url, EnderecoViaCep.class)).thenReturn(enderecoViaCepMock);
        when(enderecoViaCepMapper.toEnderecoResponseDTO(enderecoViaCepMock)).thenReturn(expectedDtoMock);

        EnderecoResponseDTO resultado = enderecoViaCepService.getEnderecoByCep(cep);

        assertNotNull(resultado);
        assertEquals(expectedDtoMock.cep(), resultado.cep());
        assertEquals(expectedDtoMock.estado(), resultado.estado());
        assertEquals(expectedDtoMock.cidade(), resultado.cidade());
        assertEquals(expectedDtoMock.rua(), resultado.rua());
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException caso CEP seja inválido (não existe)")
    void getEnderecoByCep_CepInvalido_DeveLancarResourceNotFoundException() {
        String cep = "99999999";
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        EnderecoViaCep enderecoViaCepMock = EnderecoViaCep.builder()
                .erro(true)
                .build();

        when(restTemplate.getForObject(url, EnderecoViaCep.class)).thenReturn(enderecoViaCepMock);

        assertThrows(ResourceNotFoundException.class, () -> {
            enderecoViaCepService.getEnderecoByCep(cep);
        });
    }

    @Test
    @DisplayName("Deve lançar HttpClientErrorException caso CEP tenha um formato inválido")
    void getEnderecoByCep_CepFormatoInvalido_DeveLancarHttpClientErrorException() {
        // CEPs com menos ou mais que 8 dígitos são considerados inválidos
        String cep = "123456789";
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        when(restTemplate.getForObject(url, EnderecoViaCep.class))
            .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThrows(HttpClientErrorException.class, () -> {
            enderecoViaCepService.getEnderecoByCep(cep);
        });
    }
}