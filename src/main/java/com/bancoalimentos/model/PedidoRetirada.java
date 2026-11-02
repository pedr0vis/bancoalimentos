package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.Collections;
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

    private final int idPedido;
    private StatusPedido statusPedido = StatusPedido.ABERTO;
    
    private final Instituicao instituicao;
    private final List<Lote> lotes = new ArrayList<>();

    PedidoRetirada(int idPedido, Instituicao instituicao) {
        this.idPedido = idPedido;
        this.instituicao = instituicao;
    }

    public void adicionarLote(Lote lote) {
        Validacao.obrigatorio(lote, "lote");
        if (statusPedido != StatusPedido.ABERTO) {
            throw new IllegalStateException("Só é possível alterar os lotes de um pedido aberto");
        }
        if (lotes.contains(lote)) {
            throw new IllegalArgumentException("O lote " + lote.getNumeroLote() + " já está neste pedido");
        }
        lotes.add(lote);
    }

    public void aprovar() { atualizarStatus(StatusPedido.APROVADO); }

    public void cancelar() { atualizarStatus(StatusPedido.CANCELADO); }

    public void atualizarStatus(StatusPedido novoStatus) {
        if (!statusPedido.podeMudarPara(novoStatus)) {
            throw new IllegalStateException(String.format(
                    "Pedido %d: não é possível mudar de %s para %s", idPedido, statusPedido, novoStatus));
        }
        statusPedido = novoStatus;
    }

    public List<Lote> consultarLotes() {
        return Collections.unmodifiableList(lotes);
    }

    public int getIdPedido() { return idPedido; }
    public StatusPedido getStatusPedido() { return statusPedido; }
    public Instituicao getInstituicao() { return instituicao; }
}

