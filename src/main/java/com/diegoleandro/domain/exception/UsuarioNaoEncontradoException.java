package com.diegoleandro.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long usuarioId) {
        this(String.format("Não existe cadastro de grupo com o id fornecido %d", usuarioId));
    }
}
