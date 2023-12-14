package com.diegoleandro.api.controller;

import com.diegoleandro.api.domain.exception.EntidadeEmUsoException;
import com.diegoleandro.api.domain.exception.EntidadeNaoEncontradaException;
import com.diegoleandro.api.domain.model.Cidade;
import com.diegoleandro.api.domain.repository.CidadeRepository;
import com.diegoleandro.api.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return cadastroCidadeService.salvar(cidade);
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            return cadastroCidadeService.salvar(cidadeAtual);
    }

    @DeleteMapping("/{cidadeId}")
    public void  deletar(@PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);
    }
}
