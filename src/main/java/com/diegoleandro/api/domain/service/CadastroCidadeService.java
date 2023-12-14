package com.diegoleandro.api.domain.service;

import com.diegoleandro.api.domain.exception.EntidadeEmUsoException;
import com.diegoleandro.api.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.api.domain.model.Cidade;
import com.diegoleandro.api.domain.model.Estado;
import com.diegoleandro.api.domain.repository.CidadeRepository;
import com.diegoleandro.api.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService  {

    public static final String MSG_CIDADE_NAO_CADASTRADA
            = "Não existe cadastro de cidade com o id fornecido";
    public static final String MSG_CIDADE_EM_USO
            = "Cidade com o código %d não pode ser removido pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                  String.format(MSG_CIDADE_NAO_CADASTRADA, cidadeId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId ));
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
             .orElseThrow(() -> new EntidadeNaoEncontradaException(
                     String.format(MSG_CIDADE_NAO_CADASTRADA, cidadeId)));
    }




}
