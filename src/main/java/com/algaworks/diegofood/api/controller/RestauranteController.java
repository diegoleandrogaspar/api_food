package com.algaworks.diegofood.api.controller;

import com.algaworks.diegofood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.RestauranteRepository;
import com.algaworks.diegofood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
         Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);

        if (restaurante.isPresent()){
          return ResponseEntity.ok(restaurante.get());
        }
          return ResponseEntity.notFound().build();
    }

    @PostMapping
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

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                 @RequestBody Restaurante restaurante){
        try {
              Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);

           if (restauranteAtual != null){
               BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

               restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);
               return ResponseEntity.ok(restauranteAtual);
           }
               return ResponseEntity.notFound().build();

           }catch(EntidadeNaoEncontradaException ex){
            return ResponseEntity.badRequest()
                 .body(ex.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
               @RequestBody Map<String, Object> campos){

        Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);

        if (restauranteAtual == null){
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtual);

        return atualizar(restauranteId, restauranteAtual);
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {

        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {

            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        } );
    }
}
