package com.comerciosa.gestao_contatos.dto.request;

import com.comerciosa.gestao_contatos.model.Endereco;
import com.comerciosa.gestao_contatos.validation.Documento;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ClienteRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    String nome,
    @NotBlank(message = "O CPF é obrigatório")
    @Documento
    String cpf,
    @Column(name = "data_nascimento")
    LocalDate dataNascimento,
    Endereco endereco
) {

}
