package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.UsuarioDTO;
import com.diegoleandro.api.model.input.UsuarioInput;
import com.diegoleandro.api.model.input.UsuarioComSenhaInput;
import com.diegoleandro.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioConverter implements Converter<Usuario, UsuarioDTO, UsuarioInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    @Override
    public UsuarioDTO toDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> toCollectionDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toDto(usuario))
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }


}