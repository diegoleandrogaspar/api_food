package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.UsuarioConverter;
import com.diegoleandro.api.model.UsuarioDTO;
import com.diegoleandro.domain.model.Restaurante;
import com.diegoleandro.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        return usuarioConverter.toCollectionDTO(restaurante.getResponsaveis());
    }

    @PutMapping(value = "/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        cadastroRestauranteService.associarResponsavel(restauranteId, responsavelId);
    }

    @DeleteMapping(value = "/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        cadastroRestauranteService.desassociarResponsavel(restauranteId, responsavelId);
    }




}
