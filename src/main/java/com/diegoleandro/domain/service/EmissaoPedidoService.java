package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.PedidoNaoEncontradoException;
import com.diegoleandro.domain.model.Pedido;
import com.diegoleandro.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

    }
}