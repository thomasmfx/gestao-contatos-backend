package com.comerciosa.gestao_contatos.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.repository.ClienteRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve retornar o cliente por ID e status 200 quando o cliente existir")
    void deveRetornarClientePorIdComSucesso() throws Exception {
        var clienteMock = new Cliente();
        clienteMock.setId(1);
        clienteMock.setNome("Thomas");
        clienteMock.setCpf("12345678901");
        clienteMock.setDataNascimento(LocalDate.of(2005, 10, 13));
        clienteMock.setEndereco("Rua Jaburu, 123");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteMock));

        var resultado = mockMvc.perform(get("/clientes/{id}", 1).accept(MediaType.APPLICATION_JSON));

        resultado
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(clienteMock.getId()))
                .andExpect(jsonPath("$.nome").value(clienteMock.getNome()))
                .andExpect(jsonPath("$.cpf").value(clienteMock.getCpf()))
                .andExpect(jsonPath("$.dataNascimento").value(clienteMock.getDataNascimento().toString()))
                .andExpect(jsonPath("$.endereco").value(clienteMock.getEndereco()));
    }
}
