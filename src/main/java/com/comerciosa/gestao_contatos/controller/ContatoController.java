package com.comerciosa.gestao_contatos.controller;

import com.comerciosa.gestao_contatos.cliente.Cliente;
import com.comerciosa.gestao_contatos.cliente.ClienteRepository;
import com.comerciosa.gestao_contatos.cliente.ClienteResponseDTO;
import com.comerciosa.gestao_contatos.contato.Contato;
import com.comerciosa.gestao_contatos.contato.ContatoRepository;
import com.comerciosa.gestao_contatos.contato.ContatoRequestDTO;
import com.comerciosa.gestao_contatos.contato.ContatoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contatos")
public class ContatoController {

    private final ContatoRepository repository;
    private final ClienteRepository clienteRepository;

    public ContatoController(ContatoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<ContatoResponseDTO> saveContato(@RequestBody ContatoRequestDTO dados) {
        Cliente cliente = clienteRepository.findById(dados.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Contato novoContato = new Contato(dados, cliente);
        Contato contatoSalvo = repository.save(novoContato);

        return ResponseEntity.ok(new ContatoResponseDTO(contatoSalvo));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ContatoResponseDTO> getAll(@RequestParam(required = false) Integer clienteid) {

        if (clienteid != null) {
            return repository.findByClienteId(clienteid).stream().map(ContatoResponseDTO::new).toList();
        }

        return repository.findAll().stream().map(ContatoResponseDTO::new).toList();
    }

}
