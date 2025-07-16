package com.comerciosa.gestao_contatos.repository;

import com.comerciosa.gestao_contatos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
    List<Contato> findByClienteId(Integer clienteId);

    @Query("""
            SELECT c FROM contato c
            WHERE c.cliente.id = :clienteId
            AND (
                c.tipo LIKE %:search%
                OR c.valor LIKE %:search%
                OR c.observacao LIKE %:search%
            )
            """)
    List<Contato> findByClienteIdAndTipoOrValorOrObservacao(Integer clienteId, String search);
}
