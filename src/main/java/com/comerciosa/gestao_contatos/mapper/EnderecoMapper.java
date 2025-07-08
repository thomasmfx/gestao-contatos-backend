package com.comerciosa.gestao_contatos.mapper;

import org.mapstruct.Mapper;

import com.comerciosa.gestao_contatos.dto.response.EnderecoResponseDTO;
import com.comerciosa.gestao_contatos.model.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoResponseDTO toEnderecoResponseDTO(Endereco endereco);
}
