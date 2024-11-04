package com.diegoleandro.domain.service;

import com.diegoleandro.api.model.input.SenhaInput;
import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.exception.UsuarioNaoEncontradoException;
import com.diegoleandro.domain.model.Usuario;
import com.diegoleandro.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {

    public static final String MSG_USUARIO_NAO_ENCONTRADO =
         "Não existe cadastro de grupo com o código %d";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário");
        }
        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void deletar(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        }catch (EmptyResultDataAccessException ex){
            throw new UsuarioNaoEncontradoException(String.format(MSG_USUARIO_NAO_ENCONTRADO, usuarioId));
        }
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        String.format(MSG_USUARIO_NAO_ENCONTRADO, usuarioId)));
    }





}