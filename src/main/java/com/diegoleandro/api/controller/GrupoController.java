package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.GrupoConverter;
import com.diegoleandro.api.model.GrupoDTO;
import com.diegoleandro.api.model.input.GrupoInput;
import com.diegoleandro.domain.model.Grupo;
import com.diegoleandro.domain.repository.GrupoRepository;
import com.diegoleandro.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/{grupos}")
public class GrupoController {

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoConverter grupoConverter;

    @GetMapping()
    public List<GrupoDTO> listar() {
        return grupoConverter.toCollectionDTO(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        return grupoConverter.toDto(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoConverter.toDomainObject(grupoInput);
        return grupoConverter.toDto(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
            Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
            grupoConverter.copyToDomainObject(grupoInput, grupoAtual);
            return grupoConverter.toDto(cadastroGrupoService.salvar(grupoAtual));
    }

    @DeleteMapping("/{grupoId}")
    public void deletar(@PathVariable Long grupoId) {
        cadastroGrupoService.deletar(grupoId);
    }
}