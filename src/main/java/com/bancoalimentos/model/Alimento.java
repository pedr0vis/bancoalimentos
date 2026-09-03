package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * COMPOSIÇÃO: Alimento (1..1) ◆-- Lote (0..*).
 * Alimento é o "todo": um Lote não existe sem um Alimento e seu ciclo de
 * vida está atrelado a ele. Por isso a criação de Lote é feita SOMENTE
 * através do método adicionarLote() desta classe.
 */
public class Alimento {

    private int idAlimento;
    private String nome;
    private String descricao;
    private String categoria;
    private int quantidade;

    private List<Lote> lotes = new ArrayList<>();

    public Alimento(int idAlimento, String nome, String descricao, String categoria, int quantidade) {
        this.idAlimento = idAlimento;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.quantidade = quantidade;
    }

    public int getIdAlimento() { return idAlimento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public List<Lote> getLotes() {
        return lotes;
    }

    /**
     * Único ponto de criação de um Lote. É impossível criar um Lote sem um Alimento associado, porque
     * o construtor de Lote não é público.
     */
    public Lote adicionarLote(int idLote, String dataValidade, String dataFabricacao, int numeroLote) {
        Lote lote = new Lote(idLote, dataValidade, dataFabricacao, numeroLote, this);
        lotes.add(lote);
        return lote;
    }
}
