package com.comerciosa.gestao_contatos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.comerciosa.gestao_contatos.dto.request.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ClienteResponseDTO;
import com.comerciosa.gestao_contatos.exception.BadRequestException;
import com.comerciosa.gestao_contatos.exception.ResourceNotFoundException;
import com.comerciosa.gestao_contatos.mapper.ClienteMapper;
import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.model.Endereco;
import com.comerciosa.gestao_contatos.repository.ClienteRepository;
import com.comerciosa.gestao_contatos.repository.EnderecoRepository;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(clienteRepository, enderecoRepository, clienteMapper);
    }

    @Test
    @DisplayName("Salva cliente com sucesso se dados forem válidos")
    void saveCliente_DadosValidos_DeveRetornarClienteSalvo() {
        ClienteRequestDTO clienteRequestMock = new ClienteRequestDTO(
                "John Doe",
                "12345678920",
                LocalDate.of(2005, 10, 13),
                Endereco.builder().rua("Rua Teste").build());

        Cliente clienteMock = Cliente.builder()
                .id(1)
                .nome(clienteRequestMock.nome())
                .cpf(clienteRequestMock.cpf())
                .dataNascimento(clienteRequestMock.dataNascimento())
                .endereco(clienteRequestMock.endereco())
                .build();

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        Cliente resultado = clienteService.saveCliente(clienteRequestMock);

        assertNotNull(resultado);
        assertEquals(clienteMock, resultado);
    }

    @Test
    @DisplayName("Lança BadRequestException se CPF já estiver cadastrado")
    void saveCliente_CpfCadastrado_DeveLancarBadRequestException() {
        String cpf = "12345678920";

        ClienteRequestDTO clienteRequestMock = new ClienteRequestDTO("John Doe", cpf, LocalDate.of(2005, 10, 13), null);

        when(clienteRepository.save(any(Cliente.class)))
                .thenThrow(new DataIntegrityViolationException("CPF já cadastrado"));

        assertThrows(BadRequestException.class, () -> {
            clienteService.saveCliente(clienteRequestMock);
        });
    }

    @Test
    @DisplayName("Retorna cliente encontrado com sucesso")
    void getClienteById_ClienteEncontrado_DeveRetornarCliente() {
        Endereco enderecoMock = Endereco.builder()
                .id(1)
                .rua("Rua Teste")
                .build();

        Cliente clienteMock = Cliente.builder()
                .id(1)
                .nome("John Doe")
                .cpf("12345678920")
                .dataNascimento(LocalDate.of(2005, 10, 13))
                .endereco(enderecoMock)
                .build();

        ClienteResponseDTO expectedResultado = new ClienteResponseDTO(
                1,
                clienteMock.getNome(),
                clienteMock.getCpf(),
                clienteMock.getDataNascimento(),
                enderecoMock);

        when(clienteRepository.findById(clienteMock.getId())).thenReturn(Optional.of(clienteMock));
        when(enderecoRepository.findByClienteId(clienteMock.getId())).thenReturn(Optional.of(enderecoMock));
        when(clienteMapper.toClienteResponseDTO(clienteMock)).thenReturn(
                new ClienteResponseDTO(
                        clienteMock.getId(),
                        clienteMock.getNome(),
                        clienteMock.getCpf(),
                        clienteMock.getDataNascimento(),
                        enderecoMock));

        ClienteResponseDTO resultado = clienteService.getClienteById(clienteMock.getId());

        assertNotNull(resultado);
        assertEquals(expectedResultado, resultado);
    }

    @Test
    @DisplayName("Lança ResourceNotFoundException caso cliente não seja encontrado")
    void getClienteById_ClienteNaoEncontrado_DeveLancarResourceNotFoundException() {
        Integer clienteId = 1;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.getClienteById(clienteId);
        });
    }

    @Test
    @DisplayName("Atualiza um cliente com sucesso")
    void updateCliente_ClienteExistente_DeveAtualizarCliente() {
        Integer clienteId = 1;

        Cliente clienteMock = Cliente.builder()
                .id(clienteId)
                .build();

        Endereco enderecoMock = Endereco.builder()
                .rua("Rua Teste")
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));
        when(enderecoRepository.findByClienteId(clienteId)).thenReturn(Optional.of(enderecoMock));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        Endereco enderecoUpdateMock = Endereco.builder()
                .rua("Rua Teste 2")
                .numero("123")
                .build();

        Cliente clienteUpdateMock = Cliente.builder()
                .id(clienteId)
                .nome("John Doe")
                .cpf("12345678920")
                .endereco(enderecoUpdateMock)
                .build();

        Cliente resultado = clienteService.updateCliente(clienteId, clienteUpdateMock);

        assertNotNull(resultado);
        assertEquals(clienteUpdateMock, resultado);
    }

    @Test
    @DisplayName("Lança ResourceNotFoundException ao tentar atualizar cliente inexistente")
    void updateCliente_ClienteNaoEncontrado_DeveLancarResourceNotFoundException() {
        Integer clienteId = 1;

        Cliente clienteMock = Cliente.builder()
            .id(clienteId)
            .cpf("12345678920")
            .build();

        when(clienteRepository.findById(clienteId)).thenThrow(new ResourceNotFoundException("CPF já cadastrado"));

        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.updateCliente(clienteId, clienteMock);
        });

        verify(enderecoRepository, times(0)).findByClienteId(clienteId);
    }

    @Test
    @DisplayName("Lança BadRequestException ao tentar atualizar cliente com CPF já cadastrado")
    void updateCliente_CpfCadastrado_DeveLancarBadRequestException() {
        Integer clienteId = 1;

        Cliente clienteMock = Cliente.builder()
                .id(clienteId)
                .build();

        Endereco enderecoMock = Endereco.builder()
                .rua("Rua Teste")
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));
        when(enderecoRepository.findByClienteId(clienteId)).thenReturn(Optional.of(enderecoMock));
        when(clienteRepository.save(any(Cliente.class)))
                .thenThrow(new DataIntegrityViolationException("CPF já cadastrado"));

        Endereco enderecoUpdateMock = Endereco.builder()
                .rua("Rua Teste 2")
                .numero("123")
                .build();

        Cliente clienteUpdateMock = Cliente.builder()
                .id(clienteId)
                .nome("John Doe")
                .cpf("12345678920")
                .endereco(enderecoUpdateMock)
                .build();

        assertThrows(BadRequestException.class, () -> {
            clienteService.updateCliente(clienteId, clienteUpdateMock);
        });
    }

    @Test
    @DisplayName("Deleta um cliente com sucesso")
    void deleteCliente_ClienteExistente_DeveDeletarCliente() {
        Integer clienteId = 1;
        Cliente clienteMock = new Cliente();
        clienteMock.setId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

        clienteService.deleteCliente(clienteId);

        verify(clienteRepository, times(1)).delete(clienteMock);
    }

    @Test
    @DisplayName("Lança ResourceNotFoundException ao tentar deletar cliente inexistente")
    void deleteCliente_ClienteInexistente_DeveLancarResourceNotFoundException() {
        Integer clienteId = 99;

        when(clienteRepository.findById(clienteId)).thenThrow(new ResourceNotFoundException("Cliente não encotrado"));

        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.deleteCliente(clienteId);
        });

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(0)).delete(any(Cliente.class));
    }
}
