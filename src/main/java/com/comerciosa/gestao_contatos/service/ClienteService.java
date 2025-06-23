package com.comerciosa.gestao_contatos.service;

import org.springframework.stereotype.Service;

import com.comerciosa.gestao_contatos.dto.request.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ClienteResponseDTO;
import com.comerciosa.gestao_contatos.exception.ResourceNotFoundException;
import com.comerciosa.gestao_contatos.mapper.ClienteMapper;
import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper clienteMapper;
    private static final String CLIENTE_NAO_ENCONTRADO_COM_ID = "Cliente não encontrado com o ID: ";

    public void saveCliente(ClienteRequestDTO dados) {
        Cliente dadosCliente = new Cliente(dados);
        repository.save(dadosCliente);
    }

    public Object getAll(String search, Integer decada) {
        if (decada != null) {
            return repository.contarClientesPorDecada(decada).toArray().length;
        }

        if (search != null) {
            return repository.findByNomeOrCpf(search, search).stream().map(ClienteResponseDTO::new).toList();
        }

        return repository.findAll().stream().map(ClienteResponseDTO::new).toList();
    }

    public ClienteResponseDTO getClienteById(Integer id) {
        Cliente cliente = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + id));
        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }

    public Cliente updateCliente(Integer id, Cliente dadosCliente) {
        Cliente updateCliente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + id));

        updateCliente.setNome(dadosCliente.getNome());
        updateCliente.setCpf(dadosCliente.getCpf());
        updateCliente.setEndereco(dadosCliente.getEndereco());
        updateCliente.setDataNascimento(dadosCliente.getDataNascimento());

        repository.save(updateCliente);

        return updateCliente;
    }

    public void deleteCliente(Integer id) {
        Cliente deleteCliente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + id));
        repository.delete(deleteCliente);
    }
}
