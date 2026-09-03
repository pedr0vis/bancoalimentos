package com.bancoalimentos.model;

/**
 * Classe abstrata que representa um usuário do sistema.
 * É a superclasse de Doador e Instituicao (relação de HERANÇA/GENERALIZAÇÃO).
 *
 * No domínio do Banco de Alimentos, um "Usuario" puro nunca
 * existe sozinho: todo usuário cadastrado é OBRIGATORIAMENTE um Doador ou uma
 * Instituicao. Tornar a classe abstrata impede "new Usuario(...)" e obriga o
 * uso de uma das subclasses, refletindo exatamente a regra de negócio.
 */
public abstract class Usuario {

    private int idUsuario;
    private String login;
    private String senha;
    private String documento; // CPF (Doador) ou CNPJ (Instituicao)
    private String telefone;
    private String status;

    public Usuario(int idUsuario, String login, String senha, String documento,
                    String telefone, String status) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.documento = documento;
        this.telefone = telefone;
        this.status = status;
    }

    public int getIdUsuario() { return idUsuario; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getDocumento() { return documento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public abstract String getTipoUsuario();
}
