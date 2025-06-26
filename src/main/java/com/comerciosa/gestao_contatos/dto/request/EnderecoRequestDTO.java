package com.comerciosa.gestao_contatos.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(
    @NotBlank(message = "O nome da rua é obrigatório")
    String rua,
    String numero,
    String cidade,
    String estado,
    String cep
) {
}
