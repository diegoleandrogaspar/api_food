package com.algaworks.diegofood.jpa;

import com.algaworks.diegofood.DiegofoodApiApplication;
import com.algaworks.diegofood.domain.model.Cozinha;
import com.algaworks.diegofood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class BuscaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = (ApplicationContext) new SpringApplicationBuilder(DiegofoodApiApplication.class)
             .web(WebApplicationType.NONE)
             .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha = cozinhaRepository.buscar(1L);

        System.out.println(cozinha.getNome());


    }
}
