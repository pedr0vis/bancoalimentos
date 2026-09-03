package com.bancoalimentos.model;

/**
 * ASSOCIAÇÃO "Ter": DoacaoRecebida é o lado "0..*" da relação com Doador
 * (1..1). O setDoador() é package-private porque quem comanda o vínculo é
 * sempre Doador.registrarDoacao(). Evita que o código externo "quebre" a
 * consistência ligando a doação a um doador por fora do fluxo esperado.
 *
 * ASSOCIAÇÃO com Lote: a doação recebida aponta para
 * o Lote gerado por ela, é essa ligação que fecha a cadeia de
 * rastreabilidade: Doador -> DoacaoRecebida -> Lote -> Alimento.
 */
public class DoacaoRecebida {

    private int idDoacao;
    private String data;

    private Doador doador;
    private Lote lote;

    public DoacaoRecebida(int idDoacao, String data) {
        this.idDoacao = idDoacao;
        this.data = data;
    }

    public int getIdDoacao() { return idDoacao; }
    public String getData() { return data; }

    public Doador getDoador() { return doador; }
    void setDoador(Doador doador) { this.doador = doador; }

    public Lote getLote() { return lote; }
    public void setLote(Lote lote) { this.lote = lote; }
}
