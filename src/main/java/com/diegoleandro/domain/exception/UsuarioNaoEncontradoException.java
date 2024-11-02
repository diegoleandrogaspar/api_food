package com.diegoleandro.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
