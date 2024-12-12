package com.diegoleandro.domain.service;

import com.diegoleandro.domain.model.FotoProduto;
import com.diegoleandro.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.diegoleandro.domain.service.FotoStorageService.NovaFoto;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
       Long restauranteId = foto.getRestauranteId();
       Long produtoId = foto.getProduto().getId();
       String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());


       Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.amazenar(novaFoto);

        return foto;
    }
}