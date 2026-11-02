package com.bancoalimentos.model;

import static com.bancoalimentos.model.Fixtures.HOJE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DoacaoETrocaDeEstoqueTest {

    @Test
    void doacaoNasceLigadaAoDoador() {
        Doador doador = Fixtures.doador();
        DoacaoRecebida doacao = doador.realizarDoacao(1, HOJE);
        assertSame(doador, doacao.getDoador());
        assertEquals(1, doador.consultarDoacoes().size());
    }

    @Test
    void listaDeDoacoesNaoPodeSerAlteradaPorFora() {
        Doador doador = Fixtures.doador();
        DoacaoRecebida doacao = doador.realizarDoacao(1, HOJE);
        assertThrows(UnsupportedOperationException.class, () -> doador.consultarDoacoes().add(doacao));
    }

    @Test
    void doacaoSemLoteNaoPodeSerRegistrada() {
        DoacaoRecebida doacao = Fixtures.doador().realizarDoacao(1, HOJE);
        assertThrows(IllegalStateException.class, doacao::registrarDoacao);
    }

    @Test
    void mesmoLoteNaoEntraDuasVezesNaDoacao() {
        DoacaoRecebida doacao = Fixtures.doador().realizarDoacao(1, HOJE);
        Lote lote = Fixtures.loteQueVence(Fixtures.arrozComSaldo(10), 1, HOJE.plusDays(90));
        doacao.adicionarLote(lote);
        assertThrows(IllegalArgumentException.class, () -> doacao.adicionarLote(lote));
    }

    @Test
    void distribuicaoDaBaixaNoEstoque() {
        Alimento arroz = Fixtures.arrozComSaldo(100);
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(30));
        Distribuicao distribuicao = new Distribuicao(1, 40, HOJE, lote);
        distribuicao.registrarDistribuicao();
        assertEquals(60, arroz.getQuantidade());
        assertTrue(distribuicao.isRegistrada());
    }

    @Test
    void distribuicaoMaiorQueOSaldoEhRecusada() {
        Alimento arroz = Fixtures.arrozComSaldo(10);
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(30));
        Distribuicao distribuicao = new Distribuicao(1, 11, HOJE, lote);
        assertThrows(EstoqueInsuficienteException.class, distribuicao::registrarDistribuicao);
        assertEquals(10, arroz.getQuantidade());
    }

    @Test
    void loteVencidoNaoPodeSerDistribuido() {
        Alimento arroz = Fixtures.arrozComSaldo(10);
        Lote vencido = Fixtures.loteQueVence(arroz, 1, HOJE.minusDays(1));
        Distribuicao distribuicao = new Distribuicao(1, 5, HOJE, vencido);
        assertThrows(IllegalStateException.class, distribuicao::registrarDistribuicao);
        assertEquals(10, arroz.getQuantidade());
    }

    @Test
    void distribuicaoNaoPodeSerRegistradaDuasVezes() {
        Alimento arroz = Fixtures.arrozComSaldo(100);
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(30));
        Distribuicao distribuicao = new Distribuicao(1, 10, HOJE, lote);
        distribuicao.registrarDistribuicao();
        assertThrows(IllegalStateException.class, distribuicao::registrarDistribuicao);
        assertEquals(90, arroz.getQuantidade());
    }

    @Test
    void descarteDaBaixaMesmoEmLoteVencido() {
        Alimento arroz = Fixtures.arrozComSaldo(20);
        Lote vencido = Fixtures.loteQueVence(arroz, 1, HOJE.minusDays(5));
        new Descarte(1, "Validade vencida", 8, HOJE, vencido).registrarDescarte();
        assertEquals(12, arroz.getQuantidade());
    }

    @Test
    void descarteExigeMotivoEQuantidadePositiva() {
        Lote lote = Fixtures.loteQueVence(Fixtures.arrozComSaldo(20), 1, HOJE.plusDays(5));
        assertThrows(IllegalArgumentException.class, () -> new Descarte(1, " ", 3, HOJE, lote));
        assertThrows(IllegalArgumentException.class, () -> new Descarte(1, "Danificado", 0, HOJE, lote));
    }
}
