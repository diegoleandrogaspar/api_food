package com.algaworks.diegofood.api.controller;

import com.algaworks.diegofood.domain.exception.EntidadeEmUsoException;
import com.algaworks.diegofood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.diegofood.domain.model.Estado;
import com.algaworks.diegofood.domain.repository.EstadoRepository;
import com.algaworks.diegofood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar(){
        return estadoRepository.listar();
    }

    @GetMapping("{estadoId}")
    public ResponseEntity<?> buscar(@PathVariable Long estadoId){
        try {
            Estado estado = estadoRepository.buscar(estadoId);

            if (estado != null){
               estado = estadoRepository.salvar(estado);
               return ResponseEntity.ok(estado);
            }
             return ResponseEntity.notFound().build();
        }catch (EntidadeNaoEncontradaException ex){
             return ResponseEntity
                     .badRequest()
                     .body(ex.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId,
              @RequestBody Estado estado){
        try {
            Estado estadoAtual = estadoRepository.buscar(estadoId);

            if(estadoAtual != null) {
                BeanUtils.copyProperties(estado, estadoAtual, "id");
                estadoAtual = cadastroEstadoService.salvar(estadoAtual);
                return ResponseEntity.ok(estadoAtual);
            }
                return ResponseEntity.notFound().build();

           }catch (EntidadeNaoEncontradaException ex){
                return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> deletar(@PathVariable Long estadoId){
        try {
            cadastroEstadoService.excluir(estadoId);
             return ResponseEntity.noContent().build();

        }catch (EntidadeNaoEncontradaException ex){
             return ResponseEntity.notFound().build();

        }catch (EntidadeEmUsoException ex){
             return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }
}
