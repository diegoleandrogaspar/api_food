package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.PedidoConverter;
import com.diegoleandro.api.assembler.PedidoResumoConverter;
import com.diegoleandro.api.model.PedidoDTO;
import com.diegoleandro.api.model.PedidoResumoDTO;
import com.diegoleandro.api.model.input.PedidoInput;
import com.diegoleandro.core.data.PageableTranslator;
import com.diegoleandro.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.model.Pedido;
import com.diegoleandro.domain.model.Usuario;
import com.diegoleandro.domain.repository.PedidoRepository;
import com.diegoleandro.domain.filter.PedidoFilter;
import com.diegoleandro.domain.service.EmissaoPedidoService;
import com.diegoleandro.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 2) Pageable pageable){

        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

        List<PedidoResumoDTO> pedidoResumoDTO = pedidoResumoConverter.toCollection(pedidoPage.getContent());

        Page<PedidoResumoDTO> pedidoResumoPage = new PageImpl<>(pedidoResumoDTO, pageable, pedidoPage.getTotalElements());

        return pedidoResumoPage;
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

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                    "restaurante.nome", "restaurante.nome",
                    "nomeCliente", "cliente.nome",
                    "valorTotal", "valorTotal"
                );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}