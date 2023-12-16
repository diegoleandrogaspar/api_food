package com.diegoleandro.api.domain.exception;

public class RestauranteNaoEncontradoException  extends EntidadeNaoEncontradaException{

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
