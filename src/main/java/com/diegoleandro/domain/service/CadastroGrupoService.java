package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.GrupoNaoEncontradoException;
import com.diegoleandro.domain.model.Grupo;
import com.diegoleandro.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

    public static final String MSG_GRUPO_NAO_ENCONTRADO =
            "Não existe cadastro de grupo com o código %d";

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void deletar(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();

        } catch (EmptyResultDataAccessException ex){
            throw new GrupoNaoEncontradoException(grupoId);
        }
    }

    public Grupo buscarOuFalhar(Long grupoId) {
         return grupoRepository.findById(grupoId)
                 .orElseThrow(() -> new GrupoNaoEncontradoException(
                     String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId)));
    }
}