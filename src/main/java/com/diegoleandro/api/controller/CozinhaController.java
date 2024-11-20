package com.diegoleandro.api.controller;

import com.diegoleandro.api.assembler.CozinhaConverter;
import com.diegoleandro.api.model.CozinhaDTO;
import com.diegoleandro.api.model.input.CozinhaInput;
import com.diegoleandro.domain.model.Cozinha;
import com.diegoleandro.domain.repository.CozinhaRepository;
import com.diegoleandro.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaConverter cozinhaConverter;

    @GetMapping
    public Page<CozinhaDTO> listar(Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        List<CozinhaDTO> cozinhaDTO = cozinhaConverter
                .toCollectionDTO(cozinhasPage.getContent());

        Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<>(cozinhaDTO, pageable,
                cozinhasPage.getTotalElements());

        return cozinhasDTOPage;

    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
       Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
       return cozinhaConverter.toDto(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaConverter.toDomainObject(cozinhaInput);

        cozinha = cadastroCozinhaService.salvar(cozinha);

        return cozinhaConverter.toDto(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        cozinhaConverter.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);

        return cozinhaConverter.toDto(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cozinhaId){
            cadastroCozinhaService.excluir(cozinhaId);
    }
}
