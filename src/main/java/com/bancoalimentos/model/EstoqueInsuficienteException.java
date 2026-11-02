package com.bancoalimentos.model;

/** O saldo de um alimento nunca pode ficar negativo. */
public class EstoqueInsuficienteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EstoqueInsuficienteException(String alimento, int disponivel, int solicitado) {
        super(String.format("Estoque insuficiente de %s: disponível %d, solicitado %d",
                alimento, disponivel, solicitado));
    }
}
