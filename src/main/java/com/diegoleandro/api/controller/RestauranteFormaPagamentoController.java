package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.FormaPagamentoConverter;
import com.diegoleandro.api.assembler.RestauranteConverter;
import com.diegoleandro.api.model.FormaPagamentoDTO;
import com.diegoleandro.api.model.RestauranteDTO;
import com.diegoleandro.api.model.input.RestauranteInput;
import com.diegoleandro.domain.exception.CidadeNaoEncontradaException;
import com.diegoleandro.domain.exception.CozinhaNaoEncontradaException;
import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.model.Restaurante;
import com.diegoleandro.domain.repository.RestauranteRepository;
import com.diegoleandro.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoConverter formaPagamentoConverter;

    @GetMapping
    public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
            Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
            return formaPagamentoConverter.toCollectionDTO(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
            cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }
}