package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ASSOCIAÇÕES "Registrar":
 *   RelatorioEstoque (1..1) -- Descarte (0..*)
 *   RelatorioEstoque (1..1) -- Distribuicao (0..*)
 * Um relatório agrega vários registros de descarte e de distribuição;
 * cada Descarte/Distribuicao pertence a exatamente um relatório.
 *
 * OBSERVAÇÃO IMPORTANTE: o atributo quantidadeDescarte é calculado
 * automaticamente a partir dos Descartes registrados (veja
 * registrarDescarte), evitando que alguém "digite" um valor incoerente
 * com a lista real.
 */
public class RelatorioEstoque {

    private int idRelatorioEstoque;
    private String proxVencimento;
    private int totalDoadoMes;
    private int quantidadeDescarte;
    private String dataRelatorio;

    private List<Descarte> descartes = new ArrayList<>();
    private List<Distribuicao> distribuicoes = new ArrayList<>();

    public RelatorioEstoque(int idRelatorioEstoque, String proxVencimento, String dataRelatorio) {
        this.idRelatorioEstoque = idRelatorioEstoque;
        this.proxVencimento = proxVencimento;
        this.dataRelatorio = dataRelatorio;
    }

    public int getIdRelatorioEstoque() { return idRelatorioEstoque; }

    public String getProxVencimento() { return proxVencimento; }
    public void setProxVencimento(String proxVencimento) { this.proxVencimento = proxVencimento; }

    public int getTotalDoadoMes() { return totalDoadoMes; }
    public void setTotalDoadoMes(int totalDoadoMes) { this.totalDoadoMes = totalDoadoMes; }

    public int getQuantidadeDescarte() { return quantidadeDescarte; }

    public String getDataRelatorio() { return dataRelatorio; }

    public List<Descarte> getDescartes() { return descartes; }
    public List<Distribuicao> getDistribuicoes() { return distribuicoes; }

    public void registrarDescarte(Descarte descarte) {
        descartes.add(descarte);
        descarte.setRelatorioEstoque(this);
        quantidadeDescarte += descarte.getQuantidade();
    }

    public void registrarDistribuicao(Distribuicao distribuicao) {
        distribuicoes.add(distribuicao);
        distribuicao.setRelatorioEstoque(this);
    }
}
