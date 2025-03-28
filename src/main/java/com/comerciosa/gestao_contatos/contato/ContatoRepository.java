package com.comerciosa.gestao_contatos.contato;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
    List<Contato> findByClienteId(Integer clienteId);
}
