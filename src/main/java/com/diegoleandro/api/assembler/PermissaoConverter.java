package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.PermissaoDTO;
import com.diegoleandro.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoConverter{

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO toDto(Permissao permissao){
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toDto(permissao))
                .collect(Collectors.toList());
    }
}