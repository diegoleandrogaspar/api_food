package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.ProdutoNaoEncontradoException;
import com.diegoleandro.domain.model.Produto;
import com.diegoleandro.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroProdutoService {

    public static final String MSG_PRODUTO_NAO_ENCONTRADO =
            "Não existe produto cadastrado com o código %d";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    @Transactional
    public void excluir(Long produtoId) {
        try{
            produtoRepository.deleteById(produtoId);
            produtoRepository.flush();
        } catch (EmptyResultDataAccessException ex){
            throw new ProdutoNaoEncontradoException(
                    String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId));
        }
    }

    public Produto buscarOuFalhar(Long produtoId){
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId)));
    }
}
