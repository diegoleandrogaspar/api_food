package com.diegoleandro.api.domain.repository;

import com.diegoleandro.api.domain.model.Cozinha;
import com.diegoleandro.api.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();
    Estado buscar(Long id);
    Cozinha salvar(Estado estado);
    void remover(Estado estado);
}
