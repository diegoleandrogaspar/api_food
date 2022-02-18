package com.algaworks.diegofood.domain.repository;

import com.algaworks.diegofood.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> listar();
    public  Permissao buscar(Long id);
    public Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);
}
