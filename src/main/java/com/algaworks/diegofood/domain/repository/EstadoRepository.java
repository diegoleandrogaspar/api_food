package com.algaworks.diegofood.domain.repository;

import com.algaworks.diegofood.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();
    public Estado buscar(Long id);
    public Estado salvar(Estado estado);
    void remover(Long estado);
}
