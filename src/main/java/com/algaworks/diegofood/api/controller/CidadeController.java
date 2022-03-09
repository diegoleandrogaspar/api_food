package com.algaworks.diegofood.api.controller;

import com.algaworks.diegofood.domain.exception.EntidadeEmUsoException;
import com.algaworks.diegofood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.diegofood.domain.model.Cidade;
import com.algaworks.diegofood.domain.repository.CidadeRepository;
import com.algaworks.diegofood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private CadastroCidadeService cadastroCidadeService;

  @GetMapping
  public List<Cidade> listar(Cidade cidade){
        return cidadeRepository.listar();
  }

  @GetMapping("/{cidadeId}")
  public ResponseEntity<?> buscar(@PathVariable Long cidadeId){
    Cidade cidade = cidadeRepository.buscar(cidadeId);

    if (cidade != null){
        return ResponseEntity.ok(cidade);
    }
        return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> adicionar(@RequestBody Cidade cidade){
     try {
        cidade = cadastroCidadeService.salvar(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidade);

     }catch (EntidadeNaoEncontradaException ex){
         return ResponseEntity.badRequest().body(ex.getMessage());
     }
  }

  @PutMapping("/{cidadeId}")
  public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,@RequestBody Cidade cidade){
       Cidade cidadeAtual = cidadeRepository.buscar(cidadeId);

    if (cidadeAtual != null){
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);
        return ResponseEntity.ok(cidadeAtual);
      }
        return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{cidadeId}")
  public ResponseEntity<?> deletar(@PathVariable Long cidadeId){

    try{
       cadastroCidadeService.deletar(cidadeId);
       return ResponseEntity.noContent().build();

    }catch (EntidadeNaoEncontradaException ex){
        return ResponseEntity.notFound().build();

    }catch (EntidadeEmUsoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
  }




}
