package com.algaworks.diegofood;

import com.algaworks.diegofood.modelo.Cliente;
import com.algaworks.diegofood.service.AtivacaoClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeuPrimeiroController {

    private AtivacaoClienteService ativacaoClienteService;

    public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
        this.ativacaoClienteService = ativacaoClienteService;

        System.out.println("Meu primeiro controller" + ativacaoClienteService);
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {

        Cliente diego = new Cliente("diego", "diego@xui", "23445667");

        ativacaoClienteService.ativar(diego);



        return "Olá!";
    }

}
