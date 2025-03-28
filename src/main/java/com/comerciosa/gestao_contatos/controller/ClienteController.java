package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.cliente.Cliente;
import com.comerciosa.gestao_contatos.cliente.ClienteRepository;
import com.comerciosa.gestao_contatos.cliente.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.cliente.ClienteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveCliente(@Valid @RequestBody ClienteRequestDTO dados) {
        Cliente dadosCliente = new Cliente(dados);
        repository.save(dadosCliente);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ClienteResponseDTO> getAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf
    ) {

        if (nome != null) {
            return repository.findByNome(nome).stream().map(ClienteResponseDTO::new).toList();
        }

        if (cpf != null) {
            return repository.findByCpf(cpf).stream().map(ClienteResponseDTO::new).toList();
        }

        return repository.findAll().stream().map(ClienteResponseDTO::new).toList();
    }

}
