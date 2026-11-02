package com.bancoalimentos.model;

import java.time.LocalDate;

/** Objetos prontos para os testes, para cada teste ficar curto e focado em uma regra. */
final class Fixtures {

    static final LocalDate HOJE = LocalDate.of(2026, 11, 20);

    private Fixtures() {
    }

    static DadosDeUsuario acesso(int id, String login) {
        return new DadosDeUsuario(id, login, "senha123", "12345678900", "43999990000");
    }

    static Doador doador() {
        return new Doador(acesso(1, "joao"), 1, "João");
    }

    static Instituicao instituicao() {
        return new Instituicao(acesso(2, "creche"), 1, "Creche Luz", "Rua A, 1", "creche@luz.org");
    }

    static Alimento arrozComSaldo(int saldo) {
        return new Alimento(1, "Arroz", "Pacote 5kg", "Grãos", saldo);
    }

    static Lote loteQueVence(Alimento alimento, int id, LocalDate validade) {
        return alimento.adicionarLote(id, validade, validade.minusMonths(6), 500 + id);
    }
}
