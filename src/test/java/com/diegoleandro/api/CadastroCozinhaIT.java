package com.diegoleandro.api;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import com.diegoleandro.api.domain.exception.CozinhaNaoEncontradaException;
import com.diegoleandro.api.domain.exception.EntidadeEmUsoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.Assert;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.diegoleandro.api.domain.model.Cozinha;
import com.diegoleandro.api.domain.service.CadastroCozinhaService;


@SpringBootTest
public class CadastroCozinhaIT {

    @Autowired
    CadastroCozinhaService cadastroCozinhaService;

    @Test
    public void deveCadastrarCozinhaComSucesso_QuandoCozinhaCorreta() {
        // cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //ação

        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

        // validação

        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();

    }

    @Test
    public void deveFalhar_AoCadastrarCozinha_QuandoSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cadastroCozinhaService.salvar(novaCozinha);
        });
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado =
                Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
                     cadastroCozinhaService.excluir(1L);
        });
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        CozinhaNaoEncontradaException erroEsperado =
                Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
                    cadastroCozinhaService.buscarOuFalhar(100L);
        });
        assertThat(erroEsperado).isNotNull();
    }



}
