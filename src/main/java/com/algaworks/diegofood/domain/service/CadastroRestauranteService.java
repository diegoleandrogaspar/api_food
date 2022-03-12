package com.algaworks.diegofood.domain.service;

import com.algaworks.diegofood.domain.exception.EntidadeEmUsoException;
import com.algaworks.diegofood.domain.model.Cozinha;
import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.CozinhaRepository;
import com.algaworks.diegofood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante){
       Long cozinhaId = restaurante.getCozinha().getId();
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

        if (cozinha.isEmpty()){
            throw new EntidadeEmUsoException(
               String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
        }
        restaurante.setCozinha(cozinha.get());

        return restauranteRepository.salvar(restaurante);
    }
}
