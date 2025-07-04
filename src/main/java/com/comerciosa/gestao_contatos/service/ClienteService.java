package com.comerciosa.gestao_contatos.service;

import org.springframework.stereotype.Service;

import com.comerciosa.gestao_contatos.dto.request.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ClienteResponseDTO;
import com.comerciosa.gestao_contatos.exception.ResourceNotFoundException;
import com.comerciosa.gestao_contatos.mapper.ClienteMapper;
import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.model.Endereco;
import com.comerciosa.gestao_contatos.repository.ClienteRepository;
import com.comerciosa.gestao_contatos.repository.EnderecoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ClienteMapper clienteMapper;
    private static final String CLIENTE_NAO_ENCONTRADO_COM_ID = "Cliente não encontrado com o ID: ";
    private static final String ENDERECO_NAO_ENCONTRADO_COM_CLIENTE_ID = "Endereço não encontrado com o Cliente ID: ";

    public void saveCliente(ClienteRequestDTO dados) {
        String cpfLimpo = dados.cpf().replaceAll("[\\D]", "");

        Cliente dadosCliente = Cliente.builder()
            .nome(dados.nome())
            .cpf(cpfLimpo)
            .dataNascimento(dados.dataNascimento())
            .build();

        Cliente clienteSalvo = clienteRepository.save(dadosCliente);

        Endereco endereco = Endereco.builder()
            .rua(dados.endereco().getRua())
            .numero(dados.endereco().getNumero())
            .cidade(dados.endereco().getCidade())
            .estado(dados.endereco().getEstado())
            .cep(dados.endereco().getCep())
            .cliente(clienteSalvo)
            .build();

        enderecoRepository.save(endereco);
    }

    public Object getAll(String search, Integer decada) {
        if (decada != null) {
            return clienteRepository.contarClientesPorDecada(decada).toArray().length;
        }

        if (search != null) {
            return clienteRepository.findByNomeOrCpf(search, search).stream().map(clienteMapper::clienteToClienteResponseDTO).toList();
        }

        return clienteRepository.findAll().stream().map(clienteMapper::clienteToClienteResponseDTO).toList();
    }

    public ClienteResponseDTO getClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + id));
        Endereco enderecoCliente = enderecoRepository.findByClienteId(cliente.getId())
            .orElseThrow(() -> new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO_COM_CLIENTE_ID + id));

        cliente.setEndereco(enderecoCliente);

        return clienteMapper.clienteToClienteResponseDTO(cliente);
    }

    public Cliente updateCliente(Integer id, Cliente dadosCliente) {
        Cliente updateCliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + id));
        Endereco updateEnderecoCliente = enderecoRepository.findByClienteId(id)
            .orElseThrow(() -> new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO_COM_CLIENTE_ID + id));

        Endereco enderecoCliente = dadosCliente.getEndereco();

        updateEnderecoCliente.setRua(enderecoCliente.getRua());
        updateEnderecoCliente.setNumero(enderecoCliente.getNumero());
        updateEnderecoCliente.setCidade(enderecoCliente.getCidade());
        updateEnderecoCliente.setEstado(enderecoCliente.getEstado());
        updateEnderecoCliente.setCep(enderecoCliente.getCep());

        updateCliente.setNome(dadosCliente.getNome());
        updateCliente.setCpf(dadosCliente.getCpf());
        updateCliente.setDataNascimento(dadosCliente.getDataNascimento());
        updateCliente.setEndereco(updateEnderecoCliente);

        return clienteRepository.save(updateCliente);
    }

    public void deleteCliente(Integer id) {
        Cliente deleteCliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO_COM_ID + id));

        clienteRepository.delete(deleteCliente);
    }
}
