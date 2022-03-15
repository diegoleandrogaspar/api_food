package com.algaworks.diegofood.domain.service;

import com.algaworks.diegofood.domain.exception.EntidadeEmUsoException;
import com.algaworks.diegofood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.diegofood.domain.model.Cidade;
import com.algaworks.diegofood.domain.model.Estado;
import com.algaworks.diegofood.domain.repository.CidadeRepository;
import com.algaworks.diegofood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private EstadoRepository estadoRepository;

  public Cidade salvar(Cidade cidade){
      Long estadoId = cidade.getEstado().getId();
      Estado estado = estadoRepository.findById(estadoId)
           .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro com o código %d", estadoId)));

      cidade.setEstado(estado);
      return cidadeRepository.save(cidade);
  }

  public void deletar(Long cidadeId){
    try {
       cidadeRepository.deleteById(cidadeId);

    }catch (EmptyResultDataAccessException ex){
        throw new EntidadeNaoEncontradaException(String.format("Este código %d não foi encontrado", cidadeId));

    }catch (DataIntegrityViolationException ex){
        throw new EntidadeEmUsoException(String.format("Esse recurso %d não pode ser deletado, pois está em uso", cidadeId));
    }
   }
}
