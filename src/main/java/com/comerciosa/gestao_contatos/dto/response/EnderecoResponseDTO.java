package com.comerciosa.gestao_contatos.dto.response;

public record EnderecoResponseDTO(
    Integer id,
    String rua,
    String numero,
    String cidade,
    String estado,
    String cep
) {
}
