package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.FotoProdutoDTO;
import com.diegoleandro.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toDTO(FotoProduto fotoProduto){
        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }
}