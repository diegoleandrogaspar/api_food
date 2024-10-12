package com.diegoleandro.domain.exception;

public class CozinhaNaoEncontradaException  extends EntidadeNaoEncontradaException{

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        this(String.format("Não existe um cadastro d ecozinha com código %d", cozinhaId));
    }
}
