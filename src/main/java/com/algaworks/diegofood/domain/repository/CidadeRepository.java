package com.algaworks.diegofood.domain.repository;

import com.algaworks.diegofood.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();
    public Cidade buscar(Long id);
    public Cidade salvar(Cidade cidade);
    void remover(Long id);
}
