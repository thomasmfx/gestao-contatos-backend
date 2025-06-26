package com.comerciosa.gestao_contatos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comerciosa.gestao_contatos.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    Optional<Endereco> findByClienteId(Integer clienteId);
}
