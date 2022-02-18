package com.algaworks.diegofood.domain.repository;

import com.algaworks.diegofood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listarTodos();
    public Restaurante buscar(Long id);
    public Restaurante salvar(Restaurante restaurante);
    void remover(Restaurante restaurante);
}
