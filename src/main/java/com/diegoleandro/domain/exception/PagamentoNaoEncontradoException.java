package com.diegoleandro.domain.exception;

public class PagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PagamentoNaoEncontradoException(Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de Forma de Pagamento com o código %d", formaPagamentoId));
    }
}
