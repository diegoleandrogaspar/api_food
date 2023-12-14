package com.diegoleandro.api.domain.service;

import com.diegoleandro.api.domain.exception.EntidadeEmUsoException;
import com.diegoleandro.api.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.api.domain.model.Estado;
import com.diegoleandro.api.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_NAO_ENCONTRADO
            = "Não existe estado %d cadastrado";
    public static final String ESTADO_NAO_PODE_SER_REMOVIDO
            = "Estado %d não pode ser removido pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excluir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                  String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(ESTADO_NAO_PODE_SER_REMOVIDO, estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
    }


}


