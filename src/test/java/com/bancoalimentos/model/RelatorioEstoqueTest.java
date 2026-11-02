package com.bancoalimentos.model;

import static com.bancoalimentos.model.Fixtures.HOJE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class RelatorioEstoqueTest {

    private final Doador doador = Fixtures.doador();
    private final Alimento arroz = Fixtures.arrozComSaldo(100);
    private final RelatorioEstoque relatorio = new RelatorioEstoque(1, HOJE);

    private DoacaoRecebida doacaoCom(int id, LocalDate data, Lote... lotes) {
        DoacaoRecebida doacao = doador.realizarDoacao(id, data);
        for (Lote lote : lotes) {
            doacao.adicionarLote(lote);
        }
        return doacao;
    }

    @Test
    void totalDoadoContaSomenteDoacoesDoMesDoRelatorio() {
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(90));
        relatorio.registrarDoacao(doacaoCom(1, HOJE.minusDays(3), lote));
        relatorio.registrarDoacao(doacaoCom(2, HOJE.minusMonths(1), lote));
        assertEquals(1, relatorio.calcularTotalDoadoMes());
    }

    @Test
    void doacaoSemLoteNaoEntraNoRelatorio() {
        DoacaoRecebida vazia = doador.realizarDoacao(1, HOJE);
        assertThrows(IllegalStateException.class, () -> relatorio.registrarDoacao(vazia));
    }

    @Test
    void quantidadeDescartadaEhASomaDosDescartesRegistrados() {
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(10));
        relatorio.registrarDescarte(new Descarte(1, "Danificado", 5, HOJE, lote));
        relatorio.registrarDescarte(new Descarte(2, "Danificado", 7, HOJE, lote));
        assertEquals(12, relatorio.getQuantidadeDescarte());
        assertEquals(88, arroz.getQuantidade());
    }

    @Test
    void registrarLigaODescarteEADistribuicaoAoRelatorio() {
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(10));
        Descarte descarte = new Descarte(1, "Danificado", 1, HOJE, lote);
        Distribuicao distribuicao = new Distribuicao(1, 2, HOJE, lote);
        relatorio.registrarDescarte(descarte);
        relatorio.registrarDistribuicao(distribuicao);
        assertSame(relatorio, descarte.getRelatorioEstoque());
        assertSame(relatorio, distribuicao.getRelatorioEstoque());
        assertTrue(descarte.isRegistrado() && distribuicao.isRegistrada());
    }

    @Test
    void mesmoDescarteNaoEntraDuasVezes() {
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(10));
        Descarte descarte = new Descarte(1, "Danificado", 1, HOJE, lote);
        relatorio.registrarDescarte(descarte);
        assertThrows(IllegalArgumentException.class, () -> relatorio.registrarDescarte(descarte));
        assertEquals(99, arroz.getQuantidade());
    }

    @Test
    void proximosVencimentosIgnoramVencidosELongeDeVencer() {
        Lote vencido = Fixtures.loteQueVence(arroz, 1, HOJE.minusDays(1));
        Lote vencePerto = Fixtures.loteQueVence(arroz, 2, HOJE.plusDays(10));
        Lote venceMaisTarde = Fixtures.loteQueVence(arroz, 3, HOJE.plusDays(25));
        Lote longe = Fixtures.loteQueVence(arroz, 4, HOJE.plusDays(120));
        relatorio.registrarDoacao(doacaoCom(1, HOJE, longe, venceMaisTarde, vencido, vencePerto));
        assertEquals(List.of(vencePerto, venceMaisTarde), relatorio.listarProximosVencimentos());
        assertEquals(Optional.of(HOJE.plusDays(10)), relatorio.getProxVencimento());
    }

    @Test
    void semLotePertoDeVencerOProximoVencimentoFicaVazio() {
        Lote longe = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(120));
        relatorio.registrarDoacao(doacaoCom(1, HOJE, longe));
        assertEquals(Optional.empty(), relatorio.getProxVencimento());
    }

    @Test
    void gerarRelatorioResumeOsIndicadores() {
        Lote lote = Fixtures.loteQueVence(arroz, 1, HOJE.plusDays(10));
        relatorio.registrarDoacao(doacaoCom(1, HOJE, lote));
        relatorio.registrarDescarte(new Descarte(1, "Danificado", 4, HOJE, lote));
        String texto = relatorio.gerarRelatorio();
        assertTrue(texto.contains("Doações no mês: 1"));
        assertTrue(texto.contains("Quantidade descartada: 4"));
        assertTrue(texto.contains("Próximo vencimento: " + HOJE.plusDays(10)));
    }
}
