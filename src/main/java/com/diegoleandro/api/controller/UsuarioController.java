package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.UsuarioConverter;
import com.diegoleandro.api.model.UsuarioDTO;
import com.diegoleandro.api.model.input.SenhaInput;
import com.diegoleandro.api.model.input.UsuarioInput;
import com.diegoleandro.api.model.input.UsuarioComSenhaInput;
import com.diegoleandro.domain.model.Usuario;
import com.diegoleandro.domain.repository.UsuarioRepository;
import com.diegoleandro.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( value = "/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @GetMapping
    public List<UsuarioDTO> listar() {
        List<Usuario> todosUsuarios = usuarioRepository.findAll();
        return usuarioConverter.toCollectionDTO(todosUsuarios);

    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
       Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
       return usuarioConverter.toDto(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
          Usuario usuario = usuarioConverter.toDomainObject(usuarioInput);
          usuario = cadastroUsuarioService.salvar(usuario);

          return usuarioConverter.toDto(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput){
            Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
            usuarioConverter.copyToDomainObject(usuarioInput, usuarioAtual);
            usuarioAtual = cadastroUsuarioService.salvar(usuarioAtual);

            return usuarioConverter.toDto(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
            cadastroUsuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}