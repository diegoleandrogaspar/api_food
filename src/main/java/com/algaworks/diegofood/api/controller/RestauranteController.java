package com.algaworks.diegofood.api.controller;

import com.algaworks.diegofood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.RestauranteRepository;
import com.algaworks.diegofood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.listarTodos();
    }

    @GetMapping("{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
          Restaurante restaurante = restauranteRepository.buscar(restauranteId);

        if (restaurante != null){
          return ResponseEntity.ok(restaurante);
        }
          return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED)
                  .body(restaurante);

        } catch (EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest()
                    .body(ex.getMessage());
        }
    }

    @PutMapping("{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
              @RequestBody Restaurante restaurante){
        try {
            Restaurante restauranteAtual =  restauranteRepository.buscar(restauranteId);

            if (restauranteAtual != null){
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

            restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);
            return ResponseEntity.ok(restaurante);

            }
            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


}
