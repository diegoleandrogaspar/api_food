package com.diegoleandro.api.controller;


import com.diegoleandro.api.assembler.EstadoConverter;
import com.diegoleandro.api.model.EstadoDTO;
import com.diegoleandro.api.model.input.EstadoInput;
import com.diegoleandro.domain.model.Estado;
import com.diegoleandro.domain.repository.EstadoRepository;
import com.diegoleandro.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoConverter estadoConverter;


    @GetMapping
    public List<EstadoDTO> listar() {
        List<Estado> todosEstados = estadoRepository.findAll();

        return estadoConverter.toCollectionDTO(todosEstados);

    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId) {
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);

        return  estadoConverter.toDto(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado =  estadoConverter.toDomainObject(estadoInput);

        estado = cadastroEstadoService.salvar(estado);

        return estadoConverter.toDto(estado);
    }


    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {

        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);

        estadoConverter.copyToDomainObject(estadoInput,estadoAtual);

        estadoAtual = cadastroEstadoService.salvar(estadoAtual);

        return estadoConverter.toDto(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    public void remover(@PathVariable Long estadoId) {
        cadastroEstadoService.excluir(estadoId);
    }
}
