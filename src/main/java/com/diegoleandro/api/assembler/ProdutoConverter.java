package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.ProdutoDTO;
import com.diegoleandro.api.model.input.ProdutoInput;
import com.diegoleandro.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class ProdutoConverter implements Converter<Produto, ProdutoDTO, ProdutoInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    @Override
    public ProdutoDTO toDto(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    @Override
    public Collection<ProdutoDTO> toCollectionDTO(Collection<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toDto(produto))
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}
