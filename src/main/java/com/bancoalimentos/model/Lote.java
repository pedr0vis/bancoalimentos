package com.bancoalimentos.model;

/**
 * Lado "parte" da composição com Alimento.
 *
 * O construtor é package-private (sem modificador de acesso) DE PROPÓSITO:
 * assim, só a classe Alimento (que está no mesmo pacote) pode criar um
 * Lote, ninguém de fora consegue fazer "new Lote(...)" diretamente.
 *
 * O atributo 'alimento' é final: uma vez criado, o Lote nunca troca de
 * Alimento "pai", reforça que a existência do Lote depende do Alimento.
 */
public class Lote {

    private int idLote;
    private String dataValidade;
    private String dataFabricacao;
    private int numeroLote;

    private final Alimento alimento;

    Lote(int idLote, String dataValidade, String dataFabricacao, int numeroLote, Alimento alimento) {
        this.idLote = idLote;
        this.dataValidade = dataValidade;
        this.dataFabricacao = dataFabricacao;
        this.numeroLote = numeroLote;
        this.alimento = alimento;
    }

    public int getIdLote() { return idLote; }

    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }

    public String getDataFabricacao() { return dataFabricacao; }

    public int getNumeroLote() { return numeroLote; }

    public Alimento getAlimento() { return alimento; }
}
