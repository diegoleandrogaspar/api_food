package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.FormaPagamentoDTO;
import com.diegoleandro.api.model.input.FormaPagamentoInput;
import com.diegoleandro.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoConverter implements Converter<FormaPagamento, FormaPagamentoDTO, FormaPagamentoInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    @Override
    public FormaPagamentoDTO toDto(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    @Override
    public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> formaPagamentoList) {
        return formaPagamentoList.stream()
                .map(list -> toDto(list))
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}