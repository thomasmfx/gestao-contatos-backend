package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.service.ClienteService;
import com.comerciosa.gestao_contatos.dto.request.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ClienteResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("clientes")
@Tag(name = "Clientes", description = "Gestão de clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";

    @PostMapping
    @Operation(
            summary = "Cadastra um novo cliente",
            description = "Cria um novo registro de cliente com os dados fornecidos"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Cliente criado com sucesso"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos",
            content = @Content
    )
    public ResponseEntity<Cliente> saveCliente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do cliente a ser cadastrado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteRequestDTO.class))
            )
            @Valid @RequestBody ClienteRequestDTO dados) {

        return ResponseEntity.ok(clienteService.saveCliente(dados));
    }

    @GetMapping
    @Operation(
            summary = "Lista de clientes ou quantidade por década",
            description = "Retorna uma lista de clientes ou, se for passado um parâmetro 'decada', a quantidade por década"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de clientes",
            content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))
    )
    public Object getAll(
            @Parameter(
                name = "search",
                description = "Termo para busca por nome ou CPF",
                example = "João"
            )
            @RequestParam(required = false) String search,
            
            @Parameter(
                name = "decada",    
                description = "Ano base da década",
                example = "2000"
            )
            @RequestParam(required = false) Integer decada
        ) {

            return clienteService.getAll(search, decada);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Obtém um cliente específico",
            description = "Retorna os detalhes de um cliente pelo seu ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado",
            content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = CLIENTE_NAO_ENCONTRADO,
            content = @Content
    )
    public ResponseEntity<ClienteResponseDTO> getClienteById(
            @Parameter(
                    name = "id",
                    description = "ID do cliente",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id) {

        ClienteResponseDTO clienteResponse = clienteService.getClienteById(id);
        return ResponseEntity.ok(clienteResponse);
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Atualiza um cliente existente",
            description = "Atualiza todos os dados de um cliente existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cliente atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Cliente.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = CLIENTE_NAO_ENCONTRADO,
            content = @Content
    )
    public ResponseEntity<Cliente> updateCliente(
            @Parameter(
                    name = "id",
                    description = "ID do cliente a ser atualizado",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do cliente",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Cliente.class))
            )
            @Valid @RequestBody Cliente dadosCliente) {
                
        Cliente updateCliente = clienteService.updateCliente(id, dadosCliente);
        return ResponseEntity.ok(updateCliente);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Remove um cliente",
            description = "Exclui permanentemente um cliente do sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cliente removido com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = CLIENTE_NAO_ENCONTRADO,
            content = @Content
    )
    public void deleteCliente(
            @Parameter(
                    name = "id",
                    description = "ID do cliente a ser removido",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id) {
        
        clienteService.deleteCliente(id);
    }
}