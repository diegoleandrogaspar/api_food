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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
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

/*
    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResumoDTO> pedidoDTO = pedidoConverter.converteDTO(pedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoDTO);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.isNotBlank(campos)) {
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        pedidosWrapper.setFilters(filterProvider);

        return pedidosWrapper;


    }

*/


    @GetMapping
    public List<PedidoResumoDTO> listar (){
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        return pedidoResumoConverter.toCollection(todosPedidos);
    }



    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
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