package com.comerciosa.gestao_contatos.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoViaCep implements Serializable {
    private String cep;
    private String logradouro;
    private String uf;
    private String localidade;
    private Boolean erro;
}
