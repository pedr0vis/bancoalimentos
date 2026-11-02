package com.bancoalimentos.model;

import java.time.LocalDate;

/**
 * ASSOCIAÇÃO: Descarte (1..1) -- RelatorioEstoque (o "1..1" representa que cada Descarte pertence a um único relatório).
 *
 * ASSOCIAÇÃO "Conter": Descarte (1..1) -- Lote (0..*), ou seja, um mesmo
 * registro de descarte pode abranger itens de mais de um lote.
 */
public class Descarte {

    private int idDescarte;
    private String motivo;
    private int quantidade;
    private final LocalDate data;
    private final Lote lote;

    private RelatorioEstoque relatorioEstoque;
    private boolean registrado;

    public Descarte(int idDescarte, String motivo, int quantidade, LocalDate data, Lote lote) {
        this.idDescarte = idDescarte;
        this.motivo = Validacao.textoObrigatorio(motivo, "motivo");
        this.quantidade = Validacao.positivo(quantidade, "quantidade");
        this.data = Validacao.obrigatorio(data, "data do descarte");
        this.lote = Validacao.obrigatorio(lote, "lote");
    }

    /** Dá baixa no estoque da quantidade descartada. */
    public void registrarDescarte() {
        if (registrado) {
            throw new IllegalStateException("O descarte " + idDescarte + " já foi registrado");
        }
        lote.baixarEstoque(quantidade);
        registrado = true;
    }

    void setRelatorioEstoque(RelatorioEstoque relatorioEstoque) { this.relatorioEstoque = relatorioEstoque; }

    public boolean isRegistrado() { return registrado; }
    public int getIdDescarte() { return idDescarte; }
    public String getMotivo() { return motivo; }
    public int getQuantidade() { return quantidade; }
    public LocalDate getData() { return data; }
    public Lote getLote() { return lote; }
    public RelatorioEstoque getRelatorioEstoque() { return relatorioEstoque; }
}
