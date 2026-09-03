package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ASSOCIAÇÃO: Distribuicao (1..1) -- RelatorioEstoque (0..* do lado do
 * relatório), mesmo padrão de Descarte.
 * ASSOCIAÇÃO "Conter": Distribuicao (1..1) -- Alimento (0..*). Uma mesma
 * distribuição pode levar mais de um tipo de alimento.
 */
public class Distribuicao {

    private int idDistribuicao;
    private int itensRetirados;
    private String dataEntrega;

    private RelatorioEstoque relatorioEstoque;
    private List<Alimento> alimentos = new ArrayList<>();

    public Distribuicao(int idDistribuicao, int itensRetirados, String dataEntrega) {
        this.idDistribuicao = idDistribuicao;
        this.itensRetirados = itensRetirados;
        this.dataEntrega = dataEntrega;
    }

    public int getIdDistribuicao() { return idDistribuicao; }
    public int getItensRetirados() { return itensRetirados; }
    public String getDataEntrega() { return dataEntrega; }

    public RelatorioEstoque getRelatorioEstoque() { return relatorioEstoque; }
    void setRelatorioEstoque(RelatorioEstoque relatorioEstoque) { this.relatorioEstoque = relatorioEstoque; }

    public List<Alimento> getAlimentos() { return alimentos; }
    public void adicionarAlimento(Alimento alimento) { alimentos.add(alimento); }
}
