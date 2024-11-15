package com.diegoleandro.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private Integer quantidade;
    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    public void calcularPrecoTotal(){
        BigDecimal unitario = this.precoUnitario;
        Integer quant = this.quantidade;

        if (unitario == null){
            unitario = BigDecimal.ZERO;
        }
        if (quant == null){
            quant = 0;
        }
        this.setPrecoTotal(unitario.multiply(new BigDecimal(quant)));
    }
}