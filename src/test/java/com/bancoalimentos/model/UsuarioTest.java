package com.bancoalimentos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void autenticaComLoginESenhaCorretos() {
        assertTrue(Fixtures.doador().autenticar("joao", "senha123"));
    }

    @Test
    void recusaSenhaIncorretaOuNula() {
        Doador doador = Fixtures.doador();
        assertFalse(doador.autenticar("joao", "errada"));
        assertFalse(doador.autenticar("joao", null));
        assertFalse(doador.autenticar("outro", "senha123"));
    }

    @Test
    void alterarSenhaTrocaACredencial() {
        Doador doador = Fixtures.doador();
        doador.alterarSenha("nova456");
        assertFalse(doador.autenticar("joao", "senha123"));
        assertTrue(doador.autenticar("joao", "nova456"));
    }

    @Test
    void usuarioInativoNaoAutentica() {
        Doador doador = Fixtures.doador();
        doador.inativar();
        assertFalse(doador.autenticar("joao", "senha123"));
        doador.ativar();
        assertTrue(doador.autenticar("joao", "senha123"));
    }

    @Test
    void usuarioNovoComecaAtivo() {
        assertEquals(StatusUsuario.ATIVO, Fixtures.instituicao().getStatus());
    }

    @Test
    void loginESenhaSaoObrigatorios() {
        DadosDeUsuario semLogin = new DadosDeUsuario(1, " ", "senha123", "123", "439");
        DadosDeUsuario semSenha = new DadosDeUsuario(1, "joao", "", "123", "439");
        assertThrows(IllegalArgumentException.class, () -> new Doador(semLogin, 1, "João"));
        assertThrows(IllegalArgumentException.class, () -> new Doador(semSenha, 1, "João"));
    }

    @Test
    void tipoDeUsuarioDependeDaSubclasse() {
        assertEquals("Doador", Fixtures.doador().getTipoUsuario());
        assertEquals("Instituição", Fixtures.instituicao().getTipoUsuario());
    }
}
