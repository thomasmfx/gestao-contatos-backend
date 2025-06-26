package com.comerciosa.gestao_contatos.dto.response;

import java.time.LocalDate;

import com.comerciosa.gestao_contatos.model.Endereco;

public record ClienteResponseDTO(Integer id, String nome, String cpf, LocalDate dataNascimento, Endereco endereco) {
}
