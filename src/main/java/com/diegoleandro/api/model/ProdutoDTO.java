package com.diegoleandro.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDTO {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;

}
