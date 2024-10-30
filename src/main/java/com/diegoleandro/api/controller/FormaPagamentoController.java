package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.FormaPagamentoConverter;
import com.diegoleandro.api.model.FormaPagamentoDTO;
import com.diegoleandro.api.model.input.FormaPagamentoInput;
import com.diegoleandro.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.model.FormaPagamento;
import com.diegoleandro.domain.repository.FormaPagamentoRepository;
import com.diegoleandro.domain.service.CadastroPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pagamentos")
public class FormaPagamentoController {

    @Autowired
    FormaPagamentoConverter formaPagamentoConverter;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    CadastroPagamentoService cadastroPagamentoService;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return formaPagamentoConverter.toCollectionDTO(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
       FormaPagamento formaPagamento = cadastroPagamentoService.buscarOuFalhar(formaPagamentoId);
       return formaPagamentoConverter.toDto(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        try {
            FormaPagamento formaPagamento = formaPagamentoConverter.toDomainObject(formaPagamentoInput);
            return  formaPagamentoConverter.toDto(cadastroPagamentoService.salvar(formaPagamento));
        }catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
       try {
           FormaPagamento formaPagamentoAtual = cadastroPagamentoService.buscarOuFalhar(formaPagamentoId);

           formaPagamentoConverter.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

           return formaPagamentoConverter.toDto(cadastroPagamentoService.salvar(formaPagamentoAtual));
       } catch (EntidadeNaoEncontradaException ex) {
           throw new NegocioException(ex.getMessage());
       }
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        cadastroPagamentoService.excluir(formaPagamentoId);
    }
}