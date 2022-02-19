package com.algaworks.diegofood.jpa;

import com.algaworks.diegofood.DiegofoodApiApplication;
import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(DiegofoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        List<Restaurante> todosRestaurantes = restauranteRepository.listarTodos();
        for (Restaurante restaurante : todosRestaurantes) {
            System.out.printf("%s - %f - %s\n", restaurante.getNome(),
                    restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
        }
    }
}
