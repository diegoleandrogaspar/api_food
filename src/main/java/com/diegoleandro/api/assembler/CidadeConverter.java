package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.CidadeDTO;
import com.diegoleandro.api.model.input.CidadeInput;
import com.diegoleandro.domain.model.Cidade;
import com.diegoleandro.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeConverter implements Converter<Cidade, CidadeDTO, CidadeInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    @Override
    public CidadeDTO toDto(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    @Override
    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidadeList) {
        return cidadeList.stream()
                .map(cidade -> toDto(cidade))
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {

        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
}
