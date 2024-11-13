package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.PedidoDTO;
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

    public PedidoDTO toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollection(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toDto(pedido))
                .collect(Collectors.toList());
    }


}
