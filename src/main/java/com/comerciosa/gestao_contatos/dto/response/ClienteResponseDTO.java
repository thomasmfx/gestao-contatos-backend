package com.comerciosa.gestao_contatos.dto.response;

import java.time.LocalDate;

public record ClienteResponseDTO(Integer id, String nome, String cpf, LocalDate dataNascimento, String endereco) {
}
