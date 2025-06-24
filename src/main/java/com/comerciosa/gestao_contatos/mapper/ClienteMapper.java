package com.comerciosa.gestao_contatos.mapper;

import org.mapstruct.Mapper;

import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.dto.request.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ClienteResponseDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteRequestDTO clienteToClienteRequestDTO(Cliente cliente);
    ClienteResponseDTO clienteToClienteResponseDTO(Cliente cliente);
}
