package com.algaworks.diegofood.domain.repository;

import com.algaworks.diegofood.domain.model.Cozinha;
import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();
    Cozinha buscar(Long id);
    Cozinha salvar(Cozinha cozinha);
    void remover(Cozinha cozinha);
}
