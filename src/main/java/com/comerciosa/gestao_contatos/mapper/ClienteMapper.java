package com.comerciosa.gestao_contatos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.dto.request.ClienteRequestDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "endereco", source = "endereco")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "dataNascimento", source = "dataNascimento")
    ClienteRequestDTO clienteToClienteRequestDTO(Cliente cliente);
}
