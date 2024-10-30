package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.PagamentoNaoEncontradoException;
import com.diegoleandro.domain.model.FormaPagamento;
import com.diegoleandro.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPagamentoService {

    public static final String MSG_PAGAMENTO_NAO_ENCONTRADO
            = "Não existe cadastro de pagamento com o código %d";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
       return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
       return formaPagamentoRepository.findById(formaPagamentoId)
               .orElseThrow(() -> new PagamentoNaoEncontradoException(
                       String.format(MSG_PAGAMENTO_NAO_ENCONTRADO, formaPagamentoId)));
    }
}