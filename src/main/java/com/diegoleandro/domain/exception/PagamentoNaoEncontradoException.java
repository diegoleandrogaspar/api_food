package com.diegoleandro.domain.exception;

public class PagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
