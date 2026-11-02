package com.bancoalimentos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * COMPOSIÇÃO: Alimento (1..1) ◆-- Lote (0..*).
 * A criação de Lote é feita somente através do método adicionarLote() desta classe.
 */
public class Alimento {

    // Private Final = valor só pode ser atribuído uma única vez e não pode ser alterado depois. É visível apenas dentro da própria classe.
    private final int idAlimento;
    private String nome;
    private String descricao;
    private String categoria;
    private int quantidade;
    
    private final List<Lote> lotes = new ArrayList<>();

    public Alimento(int idAlimento, String nome, String descricao, String categoria, int quantidade) {
        this.idAlimento = idAlimento;
        this.nome = Validacao.textoObrigatorio(nome, "nome");
        this.descricao = descricao;
        this.categoria = Validacao.textoObrigatorio(categoria, "categoria");
        this.quantidade = Validacao.naoNegativo(quantidade, "quantidade");
    }

    public Lote adicionarLote(int idLote, LocalDate dataValidade, LocalDate dataFabricacao, int numeroLote) {
        Lote lote = new Lote(idLote, dataValidade, dataFabricacao, numeroLote, this);
        lotes.add(lote);
        return lote;
    }

    public void atualizarQuantidade(int novaQuantidade) {
        this.quantidade = Validacao.naoNegativo(novaQuantidade, "quantidade");
    }

    /** Lotes em ordem de validade (FEFO). O primeiro da lista é o primeiro a sair. */
    public List<Lote> consultarLotes() {
        List<Lote> ordenados = new ArrayList<>(lotes);
        ordenados.sort(Comparator.comparing(Lote::getDataValidade));
        return Collections.unmodifiableList(ordenados);
    }

    void retirar(int quantidadeRetirada) {
        if (quantidadeRetirada > quantidade) {
            throw new EstoqueInsuficienteException(nome, quantidade, quantidadeRetirada);
        }
        quantidade -= quantidadeRetirada;
    }

    public int getIdAlimento() { return idAlimento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = Validacao.textoObrigatorio(nome, "nome"); }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) {
        this.categoria = Validacao.textoObrigatorio(categoria, "categoria");
    }

    public int getQuantidade() { return quantidade; }
}
