package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.PermissaoConverter;
import com.diegoleandro.api.model.PermissaoDTO;
import com.diegoleandro.domain.model.Grupo;
import com.diegoleandro.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{gruposId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private PermissaoConverter permissaoConverter;


    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long gruposId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(gruposId);
        return permissaoConverter.toCollectionDTO(grupo.getPermissoes());
    }

    @PutMapping(value = "/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long gruposId, @PathVariable Long permissaoId) {
        cadastroGrupoService.associarPermissao(gruposId, permissaoId);
    }

    @DeleteMapping(value = "/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dessasociar(@PathVariable Long gruposId, @PathVariable Long permissaoId) {
        cadastroGrupoService.dessasociarPermissao(gruposId, permissaoId);
    }




}
