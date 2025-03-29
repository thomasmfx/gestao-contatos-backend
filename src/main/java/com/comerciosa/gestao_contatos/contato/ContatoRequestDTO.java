package com.comerciosa.gestao_contatos.contato;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContatoRequestDTO(
        @NotNull(message = "O ID do cliente é obrigatório")
        Integer clienteId,
        @NotBlank(message = "O tipo de contato é obrigatório")
        String tipo,
        @NotBlank(message = "O valor do contato é obrigatório")
        String valor,
        String observacao
) {
}
