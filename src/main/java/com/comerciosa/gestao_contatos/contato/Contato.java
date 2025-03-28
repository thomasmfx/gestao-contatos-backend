package com.comerciosa.gestao_contatos.contato;

import com.comerciosa.gestao_contatos.cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "contato")
@Entity(name = "contato")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contato {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String valor;

    @Column(nullable = false)
    private String observacao;

    public Contato(ContatoRequestDTO dados, Cliente cliente) {
        this.cliente = cliente;
        this.tipo = dados.tipo();
        this.valor = dados.valor();
        this.observacao = dados.observacao();
    }
}
