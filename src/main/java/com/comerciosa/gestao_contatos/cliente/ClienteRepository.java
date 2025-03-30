package com.comerciosa.gestao_contatos.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeOrCpf(String nome, String cpf);
}
