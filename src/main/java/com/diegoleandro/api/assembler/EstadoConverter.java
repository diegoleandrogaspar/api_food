package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.EstadoDTO;
import com.diegoleandro.api.model.input.EstadoInput;
import com.diegoleandro.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoConverter implements Converter<Estado, EstadoDTO, EstadoInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Estado toDomainObject(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    @Override
    public EstadoDTO toDto(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    @Override
    public List<EstadoDTO> toCollectionDTO(List<Estado> estadoList) {
        return estadoList.stream()
                .map(estado -> toDto(estado))
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
