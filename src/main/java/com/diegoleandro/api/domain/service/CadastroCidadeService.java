package com.diegoleandro.api.domain.service;

import com.diegoleandro.api.domain.model.Cidade;
import com.diegoleandro.api.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService  {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.salvar(cidade);
    }

}
