package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.model.Cliente;
import com.comerciosa.gestao_contatos.repository.ClienteRepository;
import com.comerciosa.gestao_contatos.model.Contato;
import com.comerciosa.gestao_contatos.repository.ContatoRepository;
import com.comerciosa.gestao_contatos.dto.request.ContatoRequestDTO;
import com.comerciosa.gestao_contatos.dto.response.ContatoResponseDTO;
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
@RequestMapping("contatos")
@Tag(name = "Contatos", description = "Gestão de contatos")
public class ContatoController {

    private final ContatoRepository repository;
    private final ClienteRepository clienteRepository;
    private static final String CONTATO_NAO_ENCONTRADO_MESSAGE = "Contato não encontrado";

    public ContatoController(ContatoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

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
    public void saveContato(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do contato a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ContatoRequestDTO.class)))
            @Valid @RequestBody ContatoRequestDTO dados) {

        Cliente cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Contato novoContato = new Contato(dados, cliente);
        repository.save(novoContato);
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

        if (clienteid != null) {
            return repository.findByClienteId(clienteid).stream().map(ContatoResponseDTO::new).toList();
        }
        return repository.findAll().stream().map(ContatoResponseDTO::new).toList();
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

        Contato contato = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(CONTATO_NAO_ENCONTRADO_MESSAGE));

        return ResponseEntity.ok(new ContatoResponseDTO(contato));
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

        Contato updateContato = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(CONTATO_NAO_ENCONTRADO_MESSAGE));

        Cliente cliente = clienteRepository.findById(dadosContato.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        updateContato.setCliente(cliente);
        updateContato.setTipo(dadosContato.tipo());
        updateContato.setValor(dadosContato.valor());
        updateContato.setObservacao(dadosContato.observacao());

        repository.save(updateContato);

        return ResponseEntity.ok(new ContatoResponseDTO(updateContato));
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

        Contato deleteContato = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(CONTATO_NAO_ENCONTRADO_MESSAGE));

        repository.delete(deleteContato);
    }
}