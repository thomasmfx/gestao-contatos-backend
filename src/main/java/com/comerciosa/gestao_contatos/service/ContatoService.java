package com.comerciosa.gestao_contatos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comerciosa.gestao_contatos.dto.request.ContatoRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ContatoResponseDTO;
import com.comerciosa.gestao_contatos.exception.ResourceNotFoundException;
import com.comerciosa.gestao_contatos.mapper.ContatoMapper;
import com.comerciosa.gestao_contatos.model.Contato;
import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.repository.ClienteRepository;
import com.comerciosa.gestao_contatos.repository.ContatoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final ClienteRepository clienteRepository;
    private final ContatoMapper contatoMapper;
    private static final String CLIENTE_NAO_ENCONTRADO_COM_ID = "Cliente não encontrado com o ID: ";
    private static final String CONTATO_NAO_ENCONTRADO_COM_ID = "Cliente não encontrado com o ID: ";

    public Contato saveContato(ContatoRequestDTO dados) {
        Cliente cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + dados.clienteId()));

        Contato dadosContato = Contato.builder()
                .cliente(cliente)
                .tipo(dados.tipo())
                .valor(dados.valor())
                .observacao(dados.observacao())
                .build();

        return contatoRepository.save(dadosContato);
    }

    public List<ContatoResponseDTO> getAll(Integer clienteId) {
        if (clienteId != null) {
            return contatoRepository.findByClienteId(clienteId).stream().map(contatoMapper::toContatoResponseDTO)
                    .toList();
        }

        return contatoRepository.findAll().stream().map(contatoMapper::toContatoResponseDTO).toList();
    }

    public ContatoResponseDTO getContatoById(Integer id) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO_COM_ID + id));
        return contatoMapper.toContatoResponseDTO(contato);
    }

    public ContatoResponseDTO updateContato(Integer id, ContatoRequestDTO dadosContato) {
        Contato updateContato = contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO_COM_ID + id));
        Cliente cliente = clienteRepository.findById(dadosContato.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + id));

        updateContato.setCliente(cliente);
        updateContato.setTipo(dadosContato.tipo());
        updateContato.setValor(dadosContato.valor());
        updateContato.setObservacao(dadosContato.observacao());

        contatoRepository.save(updateContato);

        return contatoMapper.toContatoResponseDTO(updateContato);
    }

    public void deleteContato(Integer id) {
        Contato deleteContato = contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO_COM_ID + id));
        contatoRepository.delete(deleteContato);
    }
}
