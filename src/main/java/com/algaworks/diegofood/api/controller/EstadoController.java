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
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar(Estado estado) {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }
          return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId,
                                       @RequestBody Estado estado) {
        Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);

        if (estadoAtual.isPresent()) {
          BeanUtils.copyProperties(estado, estadoAtual.get(), "id");

          Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual.get());
          return ResponseEntity.ok(estadoAtual);
        }
          return ResponseEntity.notFound().build();
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
