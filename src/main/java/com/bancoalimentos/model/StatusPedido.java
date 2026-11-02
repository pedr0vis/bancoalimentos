package com.bancoalimentos.model;

/** Situações do pedido de retirada, iguais às do modelo lógico (CHECK de PEDIDO_RETIRADA). */
public enum StatusPedido {
    ABERTO,
    APROVADO,
    ATENDIDO,
    CANCELADO;

    public boolean podeMudarPara(StatusPedido novoStatus) {
        return switch (this) {
            case ABERTO -> novoStatus == APROVADO || novoStatus == CANCELADO;
            case APROVADO -> novoStatus == ATENDIDO || novoStatus == CANCELADO;
            case ATENDIDO, CANCELADO -> false;
        };
    }
}
