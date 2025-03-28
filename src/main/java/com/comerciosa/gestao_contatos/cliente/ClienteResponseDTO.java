package com.comerciosa.gestao_contatos.cliente;

import java.time.LocalDate;

public record ClienteResponseDTO(Integer id, String nome, String cpf, LocalDate dataNascimento, String endereco) {

    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getDataNascimento(), cliente.getEndereco());
    }
}
