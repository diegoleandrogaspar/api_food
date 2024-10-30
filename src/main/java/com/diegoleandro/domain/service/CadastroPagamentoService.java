package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.EntidadeEmUsoException;
import com.diegoleandro.domain.exception.PagamentoNaoEncontradoException;
import com.diegoleandro.domain.model.FormaPagamento;
import com.diegoleandro.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPagamentoService {

    public static final String MSG_PAGAMENTO_NAO_ENCONTRADO
            = "Não existe cadastro de pagamento com o código %d";

    public static final String MSG_FORMA_PAGAMENTO_EM_USO
            = "Forma de Pagamento de código %d não pode ser removida, pois está em uso";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
       return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new PagamentoNaoEncontradoException(formaPagamentoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(MSG_FORMA_PAGAMENTO_EM_USO);
        }
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
       return formaPagamentoRepository.findById(formaPagamentoId)
               .orElseThrow(() -> new PagamentoNaoEncontradoException(
                       String.format(MSG_PAGAMENTO_NAO_ENCONTRADO, formaPagamentoId)));
    }
}