package com.bancoalimentos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ASSOCIAÇÃO "Ter": DoacaoRecebida é o lado "0..*" da relação com Doador
 * (1..1).
 *
 * ASSOCIAÇÃO com Lote: a doação recebida aponta para
 * o Lote gerado por ela, é essa ligação que fecha a cadeia de
 * rastreabilidade: Doador -> DoacaoRecebida -> Lote -> Alimento.
 */
public class DoacaoRecebida {

    private int idDoacao;
    private LocalDate data;

    private Doador doador;
    private final List<Lote> lotes = new ArrayList<>();

    DoacaoRecebida(int idDoacao, LocalDate data, Doador doador) {
        this.idDoacao = idDoacao;
        this.data = Validacao.obrigatorio(data, "data da doação");
        this.doador = doador;
    }

    public void adicionarLote(Lote lote) {
        Validacao.obrigatorio(lote, "lote");
        if (lotes.contains(lote)) {
            throw new IllegalArgumentException("O lote " + lote.getNumeroLote() + " já está nesta doação");
        }
        lotes.add(lote);
    }

    public List<Lote> listarLotes() {
        return Collections.unmodifiableList(lotes);
    }

    /** Confirma a doação, exigindo ao menos um lote. */
    public void registrarDoacao() {
        if (lotes.isEmpty()) {
            throw new IllegalStateException("A doação " + idDoacao + " precisa ter ao menos um lote");
        }
    }

    public int getIdDoacao() { return idDoacao; }
    public LocalDate getData() { return data; }
    public Doador getDoador() { return doador; }
}
