package com.bancoalimentos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlimentoELoteTest {

    @Test
    void quantidadeNaoPodeSerNegativa() {
        Alimento arroz = Fixtures.arrozComSaldo(10);
        assertThrows(IllegalArgumentException.class, () -> arroz.atualizarQuantidade(-1));
        assertThrows(IllegalArgumentException.class, () -> Fixtures.arrozComSaldo(-5));
    }

    @Test
    void atualizarQuantidadeSubstituiOSaldo() {
        Alimento arroz = Fixtures.arrozComSaldo(10);
        arroz.atualizarQuantidade(25);
        assertEquals(25, arroz.getQuantidade());
    }

    @Test
    void loteCriadoPeloAlimentoPertenceAEle() {
        Alimento arroz = Fixtures.arrozComSaldo(10);
        Lote lote = Fixtures.loteQueVence(arroz, 1, LocalDate.of(2027, 1, 10));
        assertSame(arroz, lote.getAlimento());
        assertEquals(List.of(lote), arroz.consultarLotes());
    }

    @Test
    void consultarLotesDevolveEmOrdemFefo() {
        Alimento arroz = Fixtures.arrozComSaldo(10);
        Lote tardio = Fixtures.loteQueVence(arroz, 1, LocalDate.of(2027, 6, 1));
        Lote cedo = Fixtures.loteQueVence(arroz, 2, LocalDate.of(2026, 12, 1));
        assertEquals(List.of(cedo, tardio), arroz.consultarLotes());
    }

    @Test
    void listaDeLotesNaoPodeSerAlteradaPorFora() {
        Alimento arroz = Fixtures.arrozComSaldo(10);
        Lote lote = Fixtures.loteQueVence(arroz, 1, LocalDate.of(2027, 1, 10));
        assertThrows(UnsupportedOperationException.class, () -> arroz.consultarLotes().add(lote));
    }

    @Test
    void loteVenceDepoisDaDataDeValidade() {
        Lote lote = Fixtures.loteQueVence(Fixtures.arrozComSaldo(1), 1, LocalDate.of(2026, 11, 20));
        assertFalse(lote.estaVencido(LocalDate.of(2026, 11, 20)));
        assertTrue(lote.estaVencido(LocalDate.of(2026, 11, 21)));
    }

    @Test
    void diasParaVencerFicaNegativoQuandoJaVenceu() {
        Lote lote = Fixtures.loteQueVence(Fixtures.arrozComSaldo(1), 1, LocalDate.of(2026, 11, 20));
        assertEquals(10, lote.diasParaVencer(LocalDate.of(2026, 11, 10)));
        assertEquals(-3, lote.diasParaVencer(LocalDate.of(2026, 11, 23)));
    }

    @Test
    void fabricacaoNaoPodeSerDepoisDaValidade() {
        Alimento arroz = Fixtures.arrozComSaldo(1);
        assertThrows(IllegalArgumentException.class, () ->
                arroz.adicionarLote(1, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1), 501));
    }
}
