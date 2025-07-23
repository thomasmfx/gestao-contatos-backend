package com.comerciosa.gestao_contatos.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.comerciosa.gestao_contatos.dto.request.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ClienteResponseDTO;
import com.comerciosa.gestao_contatos.exception.BadRequestException;
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
    private static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";
    private static final String ENDERECO_NAO_ENCONTRADO = "Endereço não encontrado";

    public Cliente saveCliente(ClienteRequestDTO dados) {
        String cpfLimpo = dados.cpf().replaceAll("[\\D]", "");

        Cliente dadosCliente = Cliente.builder()
                .nome(dados.nome())
                .cpf(cpfLimpo)
                .dataNascimento(dados.dataNascimento())
                .build();

        try {
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

            return clienteSalvo;
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("CPF já cadastrado.");
        }
    }

    public Object getAll(String search, Integer decada) {
        if (decada != null) {
            return clienteRepository.contarClientesPorDecada(decada).toArray().length;
        }

        if (search != null) {
            return clienteRepository.findByNomeOrCpf(search, search).stream().map(clienteMapper::toClienteResponseDTO)
                    .toList();
        }

        return clienteRepository.findAll().stream().map(clienteMapper::toClienteResponseDTO).toList();
    }

    public ClienteResponseDTO getClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO));
        Endereco enderecoCliente = enderecoRepository.findByClienteId(cliente.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO));

        cliente.setEndereco(enderecoCliente);

        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public Cliente updateCliente(Integer id, Cliente dadosCliente) {
        Cliente updateCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO));
        Endereco updateEnderecoCliente = enderecoRepository.findByClienteId(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO));

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

        try {
            return clienteRepository.save(updateCliente);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("CPF já cadastrado.");
        }
    }

    public void deleteCliente(Integer id) {
        Cliente deleteCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO));

        clienteRepository.delete(deleteCliente);
    }
}
