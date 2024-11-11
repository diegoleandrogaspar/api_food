package com.diegoleandro.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String msg) {
        super(msg);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
    this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d",
            produtoId, restauranteId));
    }
}
