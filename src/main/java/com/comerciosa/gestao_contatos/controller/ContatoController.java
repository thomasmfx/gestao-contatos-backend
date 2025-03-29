package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.cliente.Cliente;
import com.comerciosa.gestao_contatos.cliente.ClienteRepository;
import com.comerciosa.gestao_contatos.contato.Contato;
import com.comerciosa.gestao_contatos.contato.ContatoRepository;
import com.comerciosa.gestao_contatos.contato.ContatoRequestDTO;
import com.comerciosa.gestao_contatos.contato.ContatoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("contatos")
public class ContatoController {

    private final ContatoRepository repository;
    private final ClienteRepository clienteRepository;

    public ContatoController(ContatoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public void saveContato(@Valid @RequestBody ContatoRequestDTO dados) {
        Cliente cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Contato novoContato = new Contato(dados, cliente);
        repository.save(novoContato);
    }

    @GetMapping
    public List<ContatoResponseDTO> getAll(@RequestParam(required = false) Integer clienteid) {

        if (clienteid != null) {
            return repository.findByClienteId(clienteid).stream().map(ContatoResponseDTO::new).toList();
        }

        return repository.findAll().stream().map(ContatoResponseDTO::new).toList();
    }

    @PutMapping("{id}")
    public ResponseEntity<ContatoResponseDTO> updateContato(@PathVariable Integer id, @Valid @RequestBody ContatoRequestDTO dadosContato) {
        Contato updateContato = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contato não encontrado"));

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
    public void deleteContato(@PathVariable Integer id){
        Contato deleteContato = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contato não encontrado"));

        repository.delete(deleteContato);
    }

}
