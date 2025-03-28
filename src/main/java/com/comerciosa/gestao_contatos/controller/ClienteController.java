package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.cliente.Cliente;
import com.comerciosa.gestao_contatos.cliente.ClienteRepository;
import com.comerciosa.gestao_contatos.cliente.ClienteRequestDTO;
import com.comerciosa.gestao_contatos.cliente.ClienteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void saveCliente(@Valid @RequestBody ClienteRequestDTO dados) {
        Cliente dadosCliente = new Cliente(dados);
        repository.save(dadosCliente);
    }

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

    @PutMapping("{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @Valid @RequestBody Cliente dadosCliente) {
        Cliente updateCliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        updateCliente.setNome(dadosCliente.getNome());
        updateCliente.setCpf(dadosCliente.getCpf());
        updateCliente.setEndereco(dadosCliente.getEndereco());
        updateCliente.setDataNascimento(dadosCliente.getDataNascimento());

        repository.save(updateCliente);

        return ResponseEntity.ok(updateCliente);
    }
}
