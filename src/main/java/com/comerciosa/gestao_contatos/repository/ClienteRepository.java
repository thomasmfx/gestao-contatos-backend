package com.comerciosa.gestao_contatos.repository;

import com.comerciosa.gestao_contatos.dto.response.ClienteDecadaResponseDTO;
import com.comerciosa.gestao_contatos.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query("""
            SELECT new com.comerciosa.gestao_contatos.dto.response.ClienteDecadaResponseDTO(
                (YEAR(c.dataNascimento) / 10) * 10,
                COUNT(c)
            )
            FROM cliente c
            WHERE YEAR(c.dataNascimento) BETWEEN :decada AND :decada + 9
            GROUP BY (YEAR(c.dataNascimento) / 10) * 10
            ORDER BY (YEAR(c.dataNascimento) / 10) * 10
            """)
    List<ClienteDecadaResponseDTO> contarClientesPorDecada(@Param("decada") Integer decada);

    List<Cliente> findByNomeOrCpf(String nome, String cpf);
}