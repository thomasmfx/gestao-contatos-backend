package com.comerciosa.gestao_contatos.dto.response;

import com.comerciosa.gestao_contatos.model.Contato;

public record ContatoResponseDTO(Integer id, Integer clienteId, String tipo, String valor, String observacao) {

    public ContatoResponseDTO(Contato contato) {
        this(contato.getId(), contato.getCliente().getId(), contato.getTipo(), contato.getValor(), contato.getObservacao());
    }
}
