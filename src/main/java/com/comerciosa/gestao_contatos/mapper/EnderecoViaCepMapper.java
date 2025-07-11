package com.comerciosa.gestao_contatos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.comerciosa.gestao_contatos.model.EnderecoViaCep;
import com.comerciosa.gestao_contatos.dto.response.EnderecoResponseDTO;

@Mapper(componentModel = "spring")
public interface EnderecoViaCepMapper {

    @Mapping(source = "logradouro", target = "rua")
    @Mapping(source = "localidade", target = "cidade")
    @Mapping(source = "uf", target = "estado")
    @Mapping(target = "numero", ignore = true)
    EnderecoResponseDTO toEnderecoResponseDTO(EnderecoViaCep enderecoViaCep);
}
