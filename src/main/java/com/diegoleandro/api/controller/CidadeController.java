package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.CidadeConverter;
import com.diegoleandro.api.model.CidadeDTO;
import com.diegoleandro.api.model.input.CidadeInput;
import com.diegoleandro.domain.exception.EstadoNaoEncontradoException;
import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.model.Cidade;
import com.diegoleandro.domain.repository.CidadeRepository;
import com.diegoleandro.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeConverter cidadeConverter;

    @GetMapping
    public List<CidadeDTO> listar() {
        List<Cidade> todasCidade = cidadeRepository.findAll();
        return cidadeConverter.toCollectionDTO(todasCidade);
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
        return cidadeConverter.toDto(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
       try {
           Cidade cidade = cidadeConverter.toDomainObject(cidadeInput);

           cidade = cadastroCidadeService.salvar(cidade);

           return cidadeConverter.toDto(cidade);

        } catch (EstadoNaoEncontradoException e) {
           throw new NegocioException(e.getMessage());
       }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

            cidadeConverter.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

            return cidadeConverter.toDto(cidadeAtual);
            }
            catch (EstadoNaoEncontradoException e){
                throw new NegocioException(e.getMessage(), e );
            }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deletar(@PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);
    }
}
