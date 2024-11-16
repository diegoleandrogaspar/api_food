package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.model.Pedido;
import com.diegoleandro.domain.model.StatusPedido;
import org.aspectj.weaver.patterns.IfPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void entregue(Long pedidoId) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        pedido.cancelar();
    }
}