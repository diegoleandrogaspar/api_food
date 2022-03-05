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
    public List<Estado> listar() {
        return estadoRepository.listar();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        Estado estado = estadoRepository.buscar(estadoId);

        if (estado != null) {
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.notFound()
                .build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
        try {
            estado = estadoRepository.salvar(estado);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(estado);
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity
                    .badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId,
                                  @RequestBody Estado estado) {
        try {
            Estado estadoAtual = estadoRepository.buscar(estadoId);

            if (estadoAtual != null) {
                BeanUtils.copyProperties(estado, estadoAtual, "id");

                estadoAtual = cadastroEstadoService.salvar(estadoAtual);
                return ResponseEntity.ok(estadoAtual);
            }
                return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException ex) {
                return ResponseEntity
                    .badRequest()
                    .body(ex.getMessage());
        }
    }




}
