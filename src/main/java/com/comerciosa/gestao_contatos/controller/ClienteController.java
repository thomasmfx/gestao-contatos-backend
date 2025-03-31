package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.cliente.Cliente;
import com.comerciosa.gestao_contatos.cliente.ClienteRepository;
import com.comerciosa.gestao_contatos.cliente.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.cliente.ClienteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("clientes")
@Tag(name = "Clientes", description = "Operações relacionadas ao cadastro de clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Operation(
            summary = "Cadastra um novo cliente",
            description = "Cria um novo registro de cliente com os dados fornecidos"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cliente criado com sucesso"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos",
            content = @Content
    )
    public void saveCliente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do cliente a ser cadastrado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteRequestDTO.class))
            )
            @Valid @RequestBody ClienteRequestDTO dados) {
        Cliente dadosCliente = new Cliente(dados);
        repository.save(dadosCliente);
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os clientes",
            description = "Retorna uma lista de clientes cadastrados, podendo filtrar por nome ou CPF"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de clientes",
            content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))
    )
    public List<ClienteResponseDTO> getAll(
            @Parameter(
                    name = "search",
                    description = "Termo para busca por nome ou CPF",
                    example = "João",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false) String search) {

        if (search != null) {
            return repository.findByNomeOrCpf(search, search).stream().map(ClienteResponseDTO::new).toList();
        }

        return repository.findAll().stream().map(ClienteResponseDTO::new).toList();
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
            description = "Cliente não encontrado",
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
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        return ResponseEntity.ok(new ClienteResponseDTO(cliente));
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
            description = "Cliente não encontrado",
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
        Cliente updateCliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        updateCliente.setNome(dadosCliente.getNome());
        updateCliente.setCpf(dadosCliente.getCpf());
        updateCliente.setEndereco(dadosCliente.getEndereco());
        updateCliente.setDataNascimento(dadosCliente.getDataNascimento());

        repository.save(updateCliente);

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
            description = "Cliente não encontrado",
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
        Cliente deleteCliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        repository.delete(deleteCliente);
    }
}