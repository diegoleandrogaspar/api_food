package com.diegoleandro.domain.model;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp()
    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable( name = "usuario_grupo",
         joinColumns = @JoinColumn(name = "usuario_id"),
         inverseJoinColumns = @JoinColumn( name = "grupo_id"))
    private List<Grupo> grupos = new ArrayList<>();
}
