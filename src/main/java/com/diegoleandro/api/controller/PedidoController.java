package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.PedidoConverter;
import com.diegoleandro.api.assembler.PedidoResumoConverter;
import com.diegoleandro.api.model.PedidoDTO;
import com.diegoleandro.api.model.PedidoResumoDTO;
import com.diegoleandro.api.model.input.PedidoInput;
import com.diegoleandro.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.model.Pedido;
import com.diegoleandro.domain.model.Usuario;
import com.diegoleandro.domain.repository.PedidoRepository;
import com.diegoleandro.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private PedidoResumoConverter pedidoResumoConverter;

    @GetMapping
    public List<PedidoResumoDTO> listar (){
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        return pedidoResumoConverter.toCollection(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        return pedidoConverter.toDto(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@RequestBody PedidoInput pedidoInput) {
        try {
            Pedido pedidoNovo = pedidoConverter.toDomainObject(pedidoInput);

            pedidoNovo.setCliente(new Usuario());
            pedidoNovo.getCliente().setId(1L);

            pedidoNovo = emissaoPedidoService.emitir(pedidoNovo);
            return pedidoConverter.toDto(pedidoNovo);

        } catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }
}