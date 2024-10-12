package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.CozinhaDTO;
import com.diegoleandro.api.model.input.CozinhaInput;
import com.diegoleandro.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaConverter  implements Converter<Cozinha, CozinhaDTO, CozinhaInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    @Override
    public CozinhaDTO toDto(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    @Override
    public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhaList) {
        return cozinhaList.stream()
                .map(cozinha -> toDto(cozinha))
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}
