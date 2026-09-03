package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ASSOCIAÇÃO: Descarte (1..1) -- RelatorioEstoque (o "1..1" aparece do lado
 * de Descarte no diagrama, ou seja, cada Descarte pertence a um único
 * relatório). O setter é "package-private" (sem 'public') porque quem deve
 * comandar esse vínculo é sempre RelatorioEstoque.registrarDescarte().
 *
 * ASSOCIAÇÃO "Conter": Descarte (1..1) -- Lote (0..*), ou seja, um mesmo
 * registro de descarte pode abranger itens de mais de um lote.
 */
public class Descarte {

    private int idDescarte;
    private String motivo;
    private int quantidade;
    private String data;

    private RelatorioEstoque relatorioEstoque;
    private List<Lote> lotes = new ArrayList<>();

    public Descarte(int idDescarte, String motivo, int quantidade, String data) {
        this.idDescarte = idDescarte;
        this.motivo = motivo;
        this.quantidade = quantidade;
        this.data = data;
    }

    public int getIdDescarte() { return idDescarte; }
    public String getMotivo() { return motivo; }
    public int getQuantidade() { return quantidade; }
    public String getData() { return data; }

    public RelatorioEstoque getRelatorioEstoque() { return relatorioEstoque; }
    void setRelatorioEstoque(RelatorioEstoque relatorioEstoque) { this.relatorioEstoque = relatorioEstoque; }

    public List<Lote> getLotes() { return lotes; }
    public void adicionarLote(Lote lote) { lotes.add(lote); }
}
