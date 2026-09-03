package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ASSOCIAÇÃO "Emitir": PedidoRetirada é o lado "0..*" da relação com
 * Instituicao (1..1). setInstituicao() é package-private pelo mesmo motivo
 * dos outros exemplos, o vínculo é sempre comandado por
 * Instituicao.emitirPedido().
 *
 * ASSOCIAÇÃO "Conter": PedidoRetirada (1..*) -- Lote (1..*). Um pedido de
 * retirada pode incluir vários lotes, e um mesmo lote
 * pode aparecer em mais de um pedido ao longo do tempo.
 */
public class PedidoRetirada {

    private int idPedido;
    private String statusPedido;

    private Instituicao instituicao;
    private List<Lote> lotes = new ArrayList<>();

    public PedidoRetirada(int idPedido, String statusPedido) {
        this.idPedido = idPedido;
        this.statusPedido = statusPedido;
    }

    public int getIdPedido() { return idPedido; }
    public String getStatusPedido() { return statusPedido; }
    public void setStatusPedido(String statusPedido) { this.statusPedido = statusPedido; }

    public Instituicao getInstituicao() { return instituicao; }
    void setInstituicao(Instituicao instituicao) { this.instituicao = instituicao; }

    public List<Lote> getLotes() { return lotes; }
    public void adicionarLote(Lote lote) { lotes.add(lote); }
}
