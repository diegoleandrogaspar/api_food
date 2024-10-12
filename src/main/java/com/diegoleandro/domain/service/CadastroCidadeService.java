package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.CidadeNaoEncontradaException;
import com.diegoleandro.domain.exception.EntidadeEmUsoException;
import com.diegoleandro.domain.model.Cidade;
import com.diegoleandro.domain.model.Estado;
import com.diegoleandro.domain.repository.CidadeRepository;
import com.diegoleandro.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(
                  String.format(MSG_CIDADE_NAO_CADASTRADA, cidadeId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId ));
        }
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
             .orElseThrow(() -> new CidadeNaoEncontradaException(
                     String.format(MSG_CIDADE_NAO_CADASTRADA, cidadeId)));
    }




}
