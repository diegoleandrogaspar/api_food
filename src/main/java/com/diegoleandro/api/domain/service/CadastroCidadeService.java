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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService  {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de estado com código %d", estadoId)));
        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeEmUsoException(
                  String.format("Não existe cadastro de cidade com o id fornecido ", cidadeId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade com o código %d não pode ser removido pois está em uso", cidadeId ));
        }
    }
}
