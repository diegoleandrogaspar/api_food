package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.PedidoResumoDTO;
import com.diegoleandro.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTO toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }

    public List<PedidoResumoDTO> toCollection(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toDto(pedido))
                .collect(Collectors.toList());
    }
}