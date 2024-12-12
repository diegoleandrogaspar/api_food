package com.diegoleandro.domain.repository;

import com.diegoleandro.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);
    void delete(FotoProduto foto);
}
