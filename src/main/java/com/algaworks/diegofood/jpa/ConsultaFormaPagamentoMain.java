package com.algaworks.diegofood.jpa;

import com.algaworks.diegofood.DiegofoodApiApplication;
import com.algaworks.diegofood.domain.model.FormaPagamento;
import com.algaworks.diegofood.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaFormaPagamentoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = (ApplicationContext) new SpringApplicationBuilder(DiegofoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

        List<FormaPagamento> ListarformaPagamentos = formaPagamentoRepository.listar();

        for( FormaPagamento formaPagamento: ListarformaPagamentos) {
            System.out.println(formaPagamento.getDescricao());
        }
    }
}
