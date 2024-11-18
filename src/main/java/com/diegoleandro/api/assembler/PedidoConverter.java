package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.PedidoDTO;
import com.diegoleandro.api.model.PedidoResumoDTO;
import com.diegoleandro.api.model.input.PedidoInput;
import com.diegoleandro.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public PedidoDTO toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollection(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toDto(pedido))
                .collect(Collectors.toList());
    }

    public PedidoResumoDTO converter(Pedido pedido){
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }


    public List<PedidoResumoDTO> converteDTO(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> converter(pedido))
                .collect(Collectors.toList());
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido){
        modelMapper.map(pedidoInput, pedido);
    }


}
