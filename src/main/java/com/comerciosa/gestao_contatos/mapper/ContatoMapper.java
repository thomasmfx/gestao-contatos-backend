package com.comerciosa.gestao_contatos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.comerciosa.gestao_contatos.dto.request.ContatoRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ContatoResponseDTO;
import com.comerciosa.gestao_contatos.model.Contato;

@Mapper(componentModel = "spring")
public interface ContatoMapper {
    
    @Mapping(source = "cliente.id", target = "clienteId")
    ContatoRequestDTO contatoToContatoRequestDTO(Contato contato);
    
    @Mapping(source = "cliente.id", target = "clienteId")
    ContatoResponseDTO contatoToContatoResponseDTO(Contato contato);
}
