package com.comerciosa.gestao_contatos.cliente;

import jakarta.persistence.Column;

import java.time.LocalDate;

public record ClienteRequestDTO(
        String nome,
        String cpf,
        @Column(name = "data_nascimento")
        LocalDate dataNascimento,
        String endereco
) {
}
