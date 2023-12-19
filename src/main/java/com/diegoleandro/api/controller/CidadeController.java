package com.diegoleandro.api.controller;

import com.diegoleandro.api.domain.exception.EntidadeEmUsoException;
import com.diegoleandro.api.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.api.domain.exception.EstadoNaoEncontradoException;
import com.diegoleandro.api.domain.exception.NegocioException;
import com.diegoleandro.api.domain.model.Cidade;
import com.diegoleandro.api.domain.repository.CidadeRepository;
import com.diegoleandro.api.domain.service.CadastroCidadeService;
import com.diegoleandro.api.exceptionhandler.Problem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CadastroCidadeService cadastroCidadeService;

    @Autowired
    CidadeRepository cidadeRepository;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId) {
      return cadastroCidadeService.buscarOuFalhar(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade) {
       try {
           return cadastroCidadeService.salvar(cidade);
        } catch (EstadoNaoEncontradoException e) {
           throw new NegocioException(e.getMessage());
       }
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

                return cadastroCidadeService.salvar(cidadeAtual);
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
