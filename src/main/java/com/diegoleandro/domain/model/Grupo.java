package com.diegoleandro.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Grupo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
         joinColumns = @JoinColumn(name = "grupo_id"),
         inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    @ManyToMany(mappedBy = "grupos")
    private List<Usuario> usuarios = new ArrayList<>();

    public Boolean adicionarPermissao(Permissao permissao){
        return getPermissoes().add(permissao);
    }

    public Boolean removerPermissao(Permissao permissao){
        return getPermissoes().remove(permissao);
    }
}
