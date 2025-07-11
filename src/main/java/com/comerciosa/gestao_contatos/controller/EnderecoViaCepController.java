package com.comerciosa.gestao_contatos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comerciosa.gestao_contatos.dto.response.EnderecoResponseDTO;
import com.comerciosa.gestao_contatos.service.EnderecoViaCepService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("endereco")
@Tag(name = "Endereço", description = "Busca de endereços por CEP")
public class EnderecoViaCepController {
    private final EnderecoViaCepService enderecoViaCepService;
    
    @GetMapping("{cep}")
    @Operation(
            summary = "Obtém um endereço específico",
            description = "Retorna os detalhes de endereço pelo seu CEP"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Endereço encontrado",
            content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Endereço não encontrado",
            content = @Content
    )
    @ApiResponse(
            responseCode = "400",
            description = "CEP informado possui um formato inválido",
            content = @Content
    )
    public ResponseEntity<EnderecoResponseDTO> getEnderecoByCep(@PathVariable String cep) {
        EnderecoResponseDTO enderecoResponse = enderecoViaCepService.getEnderecoByCep(cep);
        return ResponseEntity.ok(enderecoResponse);
    }
    
}
