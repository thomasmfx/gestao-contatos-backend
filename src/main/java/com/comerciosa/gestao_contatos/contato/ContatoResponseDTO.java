package com.comerciosa.gestao_contatos.contato;

public record ContatoResponseDTO(Integer id, Integer clienteId, String tipo, String valor, String obervacao) {

    public ContatoResponseDTO(Contato contato) {
        this(contato.getId(), contato.getCliente().getId(), contato.getTipo(), contato.getValor(), contato.getObservacao());
    }
}
