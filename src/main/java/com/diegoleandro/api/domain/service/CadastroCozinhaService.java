package com.diegoleandro.api.domain.service;

import com.diegoleandro.api.domain.exception.CozinhaNaoEncontradaException;
import com.diegoleandro.api.domain.exception.EntidadeEmUsoException;
import com.diegoleandro.api.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.api.domain.model.Cozinha;
import com.diegoleandro.api.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA
          = "Não existe um cadastro de cozinha com um código %d";

    public static final String MSG_COZINHA_EM_USO
          = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId) {
       try {
           cozinhaRepository.deleteById(cozinhaId);
       } catch (EmptyResultDataAccessException e){
           throw new CozinhaNaoEncontradaException(
                String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId));

       }catch (DataIntegrityViolationException e){
           throw new EntidadeEmUsoException(
                String.format(MSG_COZINHA_EM_USO, cozinhaId));
       }
    }

    public Cozinha buscarOuFalhar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
              .orElseThrow(() -> new CozinhaNaoEncontradaException(
                    String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));
    }


}
