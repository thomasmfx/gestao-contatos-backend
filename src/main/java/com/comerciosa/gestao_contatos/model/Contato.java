package com.comerciosa.gestao_contatos.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "contato")
@Entity(name = "contato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Contato {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String valor;

    @Column(nullable = false)
    private String observacao;
}
