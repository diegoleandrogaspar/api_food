package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.PedidoConverter;
import com.diegoleandro.api.model.PedidoDTO;
import com.diegoleandro.domain.model.Pedido;
import com.diegoleandro.domain.repository.PedidoRepository;
import com.diegoleandro.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoConverter pedidoConverter;

    @GetMapping
    public List<PedidoDTO> listar (){
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        return pedidoConverter.toCollection(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        return pedidoConverter.toDto(pedido);
    }
}