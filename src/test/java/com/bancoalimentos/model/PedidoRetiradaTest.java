package com.bancoalimentos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PedidoRetiradaTest {

    @Test
    void pedidoNasceAbertoELigadoAInstituicao() {
        Instituicao instituicao = Fixtures.instituicao();
        PedidoRetirada pedido = instituicao.emitirPedidoRetirada(1);
        assertEquals(StatusPedido.ABERTO, pedido.getStatusPedido());
        assertSame(instituicao, pedido.getInstituicao());
        assertEquals(1, instituicao.consultarPedidos().size());
    }

    @Test
    void aprovarECancelarSeguemAsTransicoesPermitidas() {
        PedidoRetirada aprovado = Fixtures.instituicao().emitirPedidoRetirada(1);
        aprovado.aprovar();
        assertEquals(StatusPedido.APROVADO, aprovado.getStatusPedido());

        PedidoRetirada cancelado = Fixtures.instituicao().emitirPedidoRetirada(2);
        cancelado.cancelar();
        assertEquals(StatusPedido.CANCELADO, cancelado.getStatusPedido());
    }

    @Test
    void pedidoCanceladoNaoPodeSerAprovado() {
        PedidoRetirada pedido = Fixtures.instituicao().emitirPedidoRetirada(1);
        pedido.cancelar();
        assertThrows(IllegalStateException.class, pedido::aprovar);
    }

    @Test
    void naoSePulaDeAbertoParaAtendido() {
        PedidoRetirada pedido = Fixtures.instituicao().emitirPedidoRetirada(1);
        assertThrows(IllegalStateException.class, () -> pedido.atualizarStatus(StatusPedido.ATENDIDO));
        pedido.aprovar();
        pedido.atualizarStatus(StatusPedido.ATENDIDO);
        assertEquals(StatusPedido.ATENDIDO, pedido.getStatusPedido());
    }

    @Test
    void lotesSoMudamEnquantoOPedidoEstaAberto() {
        PedidoRetirada pedido = Fixtures.instituicao().emitirPedidoRetirada(1);
        Lote lote = Fixtures.loteQueVence(Fixtures.arrozComSaldo(10), 1, Fixtures.HOJE.plusDays(30));
        pedido.adicionarLote(lote);
        assertThrows(IllegalArgumentException.class, () -> pedido.adicionarLote(lote));
        pedido.aprovar();
        Lote outro = Fixtures.loteQueVence(Fixtures.arrozComSaldo(10), 2, Fixtures.HOJE.plusDays(60));
        assertThrows(IllegalStateException.class, () -> pedido.adicionarLote(outro));
    }
}
