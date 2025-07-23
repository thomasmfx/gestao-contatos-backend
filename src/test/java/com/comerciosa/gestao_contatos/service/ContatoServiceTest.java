package com.comerciosa.gestao_contatos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.comerciosa.gestao_contatos.dto.request.ContatoRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ContatoResponseDTO;
import com.comerciosa.gestao_contatos.exception.ResourceNotFoundException;
import com.comerciosa.gestao_contatos.mapper.ContatoMapper;
import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.model.Contato;
import com.comerciosa.gestao_contatos.repository.ClienteRepository;
import com.comerciosa.gestao_contatos.repository.ContatoRepository;

@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ContatoMapper contatoMapper;

    @InjectMocks
    private ContatoService contatoService;

    private Cliente clienteMock;
    private Contato contatoMock;
    private ContatoRequestDTO contatoRequestDTOMock;
    private ContatoResponseDTO contatoResponseDTOMock;

    @BeforeEach
    void setUp() {
        clienteMock = Cliente.builder().id(1).nome("Cliente Teste").build();

        contatoRequestDTOMock = new ContatoRequestDTO(1, "EMAIL", "teste@email.com", "Contato principal");

        contatoMock = Contato.builder()
                .id(10)
                .cliente(clienteMock)
                .tipo(contatoRequestDTOMock.tipo())
                .valor(contatoRequestDTOMock.valor())
                .observacao(contatoRequestDTOMock.observacao())
                .build();

        contatoResponseDTOMock = new ContatoResponseDTO(
                contatoMock.getId(),
                1, contatoMock.getTipo(),
                contatoMock.getValor(),
                contatoMock.getObservacao());
    }

    @Test
    @DisplayName("Salva contato com sucesso se dados e cliente forem válidos")
    void saveContato_DadosValidos_DeveRetornarContatoSalvo() {
        when(clienteRepository.findById(clienteMock.getId())).thenReturn(Optional.of(clienteMock));
        when(contatoRepository.save(any(Contato.class))).thenReturn(contatoMock);

        Contato resultado = contatoService.saveContato(contatoRequestDTOMock);

        assertNotNull(resultado);
        assertEquals(contatoMock.getTipo(), resultado.getTipo());
        assertEquals(clienteMock, resultado.getCliente());
        verify(clienteRepository, times(1)).findById(clienteMock.getId());
        verify(contatoRepository, times(1)).save(any(Contato.class));
    }

    @Test
    @DisplayName("Lança ResourceNotFoundException ao tentar salvar contato para cliente inexistente")
    void saveContato_ClienteNaoEncontrado_DeveLancarResourceNotFoundException() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contatoService.saveContato(contatoRequestDTOMock);
        });

        verify(contatoRepository, never()).save(any(Contato.class));
    }

    @Test
    @DisplayName("Retorna todos os contatos quando nenhum filtro é aplicado")
    void getAll_SemFiltros_DeveRetornarTodosContatos() {
        when(contatoRepository.findAll()).thenReturn(List.of(contatoMock));
        when(contatoMapper.toContatoResponseDTO(contatoMock)).thenReturn(contatoResponseDTOMock);

        List<ContatoResponseDTO> resultado = contatoService.getAll(null, null);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(contatoResponseDTOMock, resultado.get(0));
        verify(contatoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Retorna contatos filtrados por clienteId")
    void getAll_ComClienteId_DeveRetornarContatosDoCliente() {
        Integer clienteId = 1;
        when(contatoRepository.findByClienteId(clienteId)).thenReturn(List.of(contatoMock));
        when(contatoMapper.toContatoResponseDTO(contatoMock)).thenReturn(contatoResponseDTOMock);

        List<ContatoResponseDTO> resultado = contatoService.getAll(clienteId, null);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(contatoRepository, times(1)).findByClienteId(clienteId);
    }

    @Test
    @DisplayName("Retorna contatos filtrados por clienteId e termo de busca")
    void getAll_ComClienteIdESearch_DeveRetornarContatosFiltrados() {
        Integer clienteId = 1;
        String search = "email";
        when(contatoRepository.findByClienteIdAndTipoOrValorOrObservacao(clienteId, search))
                .thenReturn(List.of(contatoMock));
        when(contatoMapper.toContatoResponseDTO(contatoMock)).thenReturn(contatoResponseDTOMock);

        List<ContatoResponseDTO> resultado = contatoService.getAll(clienteId, search);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(contatoRepository, times(1)).findByClienteIdAndTipoOrValorOrObservacao(clienteId, search);
    }

    @Test
    @DisplayName("Retorna contato por ID com sucesso")
    void getContatoById_ContatoEncontrado_DeveRetornarContatoDTO() {
        when(contatoRepository.findById(contatoMock.getId())).thenReturn(Optional.of(contatoMock));
        when(contatoMapper.toContatoResponseDTO(contatoMock)).thenReturn(contatoResponseDTOMock);

        ContatoResponseDTO resultado = contatoService.getContatoById(contatoMock.getId());

        assertNotNull(resultado);
        assertEquals(contatoResponseDTOMock, resultado);
    }

    @Test
    @DisplayName("Lança ResourceNotFoundException ao buscar contato com ID inexistente")
    void getContatoById_ContatoNaoEncontrado_DeveLancarResourceNotFoundException() {
        Integer idInexistente = 99;
        when(contatoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contatoService.getContatoById(idInexistente);
        });
    }

    @Test
    @DisplayName("Atualiza um contato com sucesso")
    void updateContato_ContatoExistente_DeveRetornarContatoAtualizadoDTO() {
        ContatoRequestDTO dadosUpdate = new ContatoRequestDTO(1, "TELEFONE", "99999-8888", "Novo Telefone");

        when(contatoRepository.findById(contatoMock.getId())).thenReturn(Optional.of(contatoMock));
        when(clienteRepository.findById(dadosUpdate.clienteId())).thenReturn(Optional.of(clienteMock));

        when(contatoMapper.toContatoResponseDTO(any(Contato.class))).thenAnswer(invocation -> {
            Contato c = invocation.getArgument(0);
            return new ContatoResponseDTO(c.getId(), null, c.getTipo(), c.getValor(), c.getObservacao());
        });

        ContatoResponseDTO resultado = contatoService.updateContato(contatoMock.getId(), dadosUpdate);

        assertNotNull(resultado);
        assertEquals(dadosUpdate.tipo(), resultado.tipo());
        assertEquals(dadosUpdate.valor(), resultado.valor());
        assertEquals(dadosUpdate.observacao(), resultado.observacao());
        verify(contatoRepository, times(1)).save(contatoMock);
    }

    @Test
    @DisplayName("Lança ResourceNotFoundException ao tentar atualizar contato inexistente")
    void updateContato_ContatoNaoEncontrado_DeveLancarResourceNotFoundException() {
        Integer idInexistente = 99;
        when(contatoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contatoService.updateContato(idInexistente, contatoRequestDTOMock);
        });

        verify(clienteRepository, never()).findById(anyInt());
        verify(contatoRepository, never()).save(any(Contato.class));
    }

    @Test
    @DisplayName("Deleta um contato com sucesso")
    void deleteContato_ContatoExistente_DeveChamarDeleteDoRepositorio() {
        when(contatoRepository.findById(contatoMock.getId())).thenReturn(Optional.of(contatoMock));

        assertDoesNotThrow(() -> contatoService.deleteContato(contatoMock.getId()));

        verify(contatoRepository, times(1)).delete(contatoMock);
    }

    @Test
    @DisplayName("Lança ResourceNotFoundException ao tentar deletar contato inexistente")
    void deleteContato_ContatoInexistente_DeveLancarResourceNotFoundException() {
        Integer idInexistente = 99;
        when(contatoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contatoService.deleteContato(idInexistente);
        });

        verify(contatoRepository, never()).delete(any(Contato.class));
    }
}