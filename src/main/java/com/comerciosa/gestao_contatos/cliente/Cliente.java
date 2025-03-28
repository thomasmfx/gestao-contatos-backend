package com.comerciosa.gestao_contatos.cliente;

import com.comerciosa.gestao_contatos.contato.Contato;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "cliente")
@Entity(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Contato> contatos;

    public Cliente(ClienteRequestDTO dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf().replaceAll("[^0-9]", "");
        this.dataNascimento = dados.dataNascimento();
        this.endereco = dados.endereco();
    }

}
