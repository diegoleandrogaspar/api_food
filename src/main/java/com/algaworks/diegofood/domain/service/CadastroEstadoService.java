package com.algaworks.diegofood.domain.service;

import com.algaworks.diegofood.domain.exception.EntidadeEmUsoException;
import com.algaworks.diegofood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.diegofood.domain.model.Estado;
import com.algaworks.diegofood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
       return estadoRepository.salvar(estado);
    }

    public void excluir(Long estadoId){
        try {
            estadoRepository.remover(estadoId);
        }catch (EmptyResultDataAccessException ex){
            throw new EntidadeNaoEncontradaException(String.format("não foi feito o registo do código %d", estadoId));

        }catch (DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format("Esse código %d está em uso", estadoId));
        }
    }

}
