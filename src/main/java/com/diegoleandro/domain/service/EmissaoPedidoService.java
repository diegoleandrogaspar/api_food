package com.diegoleandro.domain.service;

import com.diegoleandro.domain.exception.NegocioException;
import com.diegoleandro.domain.exception.PedidoNaoEncontradoException;
import com.diegoleandro.domain.model.*;
import com.diegoleandro.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private CadastroPagamentoService cadastroPagamentoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    public void validarPedido(Pedido pedido) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
        FormaPagamento formaPagamento = cadastroPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.setRestaurante(restaurante);
        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(usuario);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)){
            throw new NegocioException(String.format("Forma de Pagamento '%s' não é aceita por esse restaurante",
                    formaPagamento.getDescricao()));
        }
    }

    public void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = cadastroProdutoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}