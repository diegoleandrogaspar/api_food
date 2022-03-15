package com.algaworks.diegofood.jpa;

import com.algaworks.diegofood.DiegofoodApiApplication;
import com.algaworks.diegofood.domain.model.Cidade;
import com.algaworks.diegofood.domain.repository.CidadeRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCidadeMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = (ApplicationContext) new SpringApplicationBuilder(DiegofoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

        List<Cidade> todasCidades = cidadeRepository.findAll();

        for (Cidade cidade: todasCidades) {
            System.out.printf("%s - %s\n", cidade.getNome(), cidade.getEstado().getNome());
        }
    }
}
