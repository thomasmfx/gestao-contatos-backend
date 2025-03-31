package com.comerciosa.gestao_contatos.repository;

import com.comerciosa.gestao_contatos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeOrCpf(String nome, String cpf);
}
