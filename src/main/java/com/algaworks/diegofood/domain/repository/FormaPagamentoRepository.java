package com.algaworks.diegofood.domain.repository;

import com.algaworks.diegofood.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();
    public FormaPagamento buscar(Long id);
    public FormaPagamento salvar(FormaPagamento formaPagamento);
    void remover(FormaPagamento formaPagamento);
}
