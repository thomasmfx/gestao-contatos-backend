package com.comerciosa.gestao_contatos.contato;

import jakarta.persistence.Column;

public record ContatoRequestDTO(
        @Column(name = "cliente_id")
        Integer clienteId,
        String tipo,
        String valor,
        String observacao
) {
}
