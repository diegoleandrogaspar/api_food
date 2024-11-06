package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.GrupoDTO;
import com.diegoleandro.api.model.input.GrupoInput;
import com.diegoleandro.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoConverter implements Converter<Grupo, GrupoDTO, GrupoInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    @Override
    public GrupoDTO toDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    @Override
    public List<GrupoDTO> toCollectionDTO(Collection<Grupo> grupoList) {
         return grupoList.stream()
                 .map(grupo -> toDto(grupo))
                 .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
