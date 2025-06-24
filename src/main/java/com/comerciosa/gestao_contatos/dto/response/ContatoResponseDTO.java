package com.comerciosa.gestao_contatos.dto.response;

public record ContatoResponseDTO(Integer id, Integer clienteId, String tipo, String valor, String observacao) {
}