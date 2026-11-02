package com.bancoalimentos.model;

/** Agrupa os dados comuns de Doador e Instituicao para não repetir cinco argumentos em cada construtor. */
public final class DadosDeUsuario {

    private final int idUsuario;
    private final String login;
    private final String senha;
    private final String documento;
    private final String telefone;

    public DadosDeUsuario(int idUsuario, String login, String senha, String documento, String telefone) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.documento = documento;
        this.telefone = telefone;
    }

    int getIdUsuario() { return idUsuario; }
    String getLogin() { return login; }
    String getSenha() { return senha; }
    String getDocumento() { return documento; }
    String getTelefone() { return telefone; }
}
