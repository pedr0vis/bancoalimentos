package com.bancoalimentos.model;

import java.time.LocalDate;

/**
 * ASSOCIAÇÃO: Distribuicao (1..1) -- RelatorioEstoque (0..* do lado do
 * relatório), mesmo padrão de Descarte.
 * 
 */
public class Distribuicao {

    private int idDistribuicao;
    private int itensRetirados;
    private final LocalDate dataEntrega;
    private final Lote lote;

    private RelatorioEstoque relatorioEstoque;
    private boolean registrada;

    public Distribuicao(int idDistribuicao, int itensRetirados, LocalDate dataEntrega, Lote lote) {
        this.idDistribuicao = idDistribuicao;
        this.itensRetirados = Validacao.positivo(itensRetirados, "itens retirados");
        this.dataEntrega = Validacao.obrigatorio(dataEntrega, "data de entrega");
        this.lote = Validacao.obrigatorio(lote, "lote");
    }

    /** Dá baixa no estoque. Lote vencido não pode ser distribuído. */
    public void registrarDistribuicao() {
        if (registrada) {
            throw new IllegalStateException("A distribuição " + idDistribuicao + " já foi registrada");
        }
        if (lote.estaVencido(dataEntrega)) {
            throw new IllegalStateException("O lote " + lote.getNumeroLote() + " está vencido e não pode ser distribuído");
        }
        lote.baixarEstoque(itensRetirados);
        registrada = true;
    }

    void setRelatorioEstoque(RelatorioEstoque relatorioEstoque) { this.relatorioEstoque = relatorioEstoque; }

    public boolean isRegistrada() { return registrada; }
    public int getIdDistribuicao() { return idDistribuicao; }
    public int getItensRetirados() { return itensRetirados; }
    public LocalDate getDataEntrega() { return dataEntrega; }
    public Lote getLote() { return lote; }
    public RelatorioEstoque getRelatorioEstoque() { return relatorioEstoque; }
}
