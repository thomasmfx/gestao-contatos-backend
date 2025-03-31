package com.comerciosa.gestao_contatos.dto.response;

import com.comerciosa.gestao_contatos.model.Cliente;

import java.time.LocalDate;

public record ClienteResponseDTO(Integer id, String nome, String cpf, LocalDate dataNascimento, String endereco) {

    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getDataNascimento(), cliente.getEndereco());
    }
}
