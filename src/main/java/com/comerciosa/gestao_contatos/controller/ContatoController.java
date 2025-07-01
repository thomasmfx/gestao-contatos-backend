package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.service.ContatoService;
import com.comerciosa.gestao_contatos.dto.request.ContatoRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ContatoResponseDTO;
import com.comerciosa.gestao_contatos.model.Contato;

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

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("contatos")
@Tag(name = "Contatos", description = "Gestão de contatos")
public class ContatoController {

    private final ContatoService contatoService;
    private static final String CONTATO_NAO_ENCONTRADO_MESSAGE = "Contato não encontrado";

    @PostMapping
    @Operation(
            summary = "Cria um novo contato",
            description = "Cadastra um novo contato associado a um cliente existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Contato criado com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado",
            content = @Content
    )
    public ResponseEntity<Contato> saveContato(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do contato a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ContatoRequestDTO.class)))
            @Valid @RequestBody ContatoRequestDTO dados) {

        return ResponseEntity.ok(contatoService.saveContato(dados));
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os contatos ou filtra por ID do cliente",
            description = "Retorna uma lista de contatos. Se clienteid for fornecido, filtra os contatos do cliente específico."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de contatos",
            content = @Content(schema = @Schema(implementation = ContatoResponseDTO.class))
    )
    public List<ContatoResponseDTO> getAll(
            @Parameter(
                    name = "clienteid",
                    description = "ID do cliente para filtrar contatos",
                    example = "1",
                    schema = @Schema(type = "integer", format = "int32")
            )
            @RequestParam(required = false) Integer clienteid) {

        return contatoService.getAll(clienteid);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Obtém um contato específico",
            description = "Retorna os detalhes de um contato pelo seu ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Contato encontrado",
            content = @Content(schema = @Schema(implementation = ContatoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = CONTATO_NAO_ENCONTRADO_MESSAGE,
            content = @Content
    )
    public ResponseEntity<ContatoResponseDTO> getContatoById(
            @Parameter(
                    name = "id",
                    description = "ID do contato",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id) {

        return ResponseEntity.ok(contatoService.getContatoById(id));
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Atualiza um contato existente",
            description = "Atualiza todos os dados de um contato existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Contato atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = ContatoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Contato ou cliente não encontrado",
            content = @Content
    )
    public ResponseEntity<ContatoResponseDTO> updateContato(
            @Parameter(
                    name = "id",
                    description = "ID do contato a ser atualizado",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do contato",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ContatoRequestDTO.class))
            )
            @Valid @RequestBody ContatoRequestDTO dadosContato) {

        return ResponseEntity.ok(contatoService.updateContato(id, dadosContato));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Remove um contato",
            description = "Exclui permanentemente um contato do sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Contato removido com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = CONTATO_NAO_ENCONTRADO_MESSAGE,
            content = @Content
    )
    public void deleteContato(
            @Parameter(
                    name = "id",
                    description = "ID do contato a ser removido",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id) {

        contatoService.deleteContato(id);
    }
}