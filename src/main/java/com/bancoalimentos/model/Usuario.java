package com.bancoalimentos.model;

import com.bancoalimentos.seguranca.HashDeSenha;

/**
 * Classe abstrata que representa um usuário do sistema.
 * É a superclasse de Doador e Instituicao (relação de HERANÇA/GENERALIZAÇÃO).
 *
 * No domínio do Banco de Alimentos, um "Usuario" puro nunca
 * existe sozinho: todo usuário cadastrado é obrigatoriamente um Doador ou uma
 * Instituicao. Tornar a classe abstrata impede "new Usuario(...)" e obriga o
 * uso de uma das subclasses, refletindo exatamente a regra de negócio.
 */
public abstract class Usuario {

    private final int idUsuario;
    private String login;
    private String senhaHash;
    private final String documento; // CPF (Doador) ou CNPJ (Instituicao)
    private String telefone;
    private StatusUsuario status = StatusUsuario.ATIVO;

    protected Usuario(DadosDeUsuario dados) {
        this.idUsuario = dados.getIdUsuario();
        this.login = Validacao.textoObrigatorio(dados.getLogin(), "login");
        this.senhaHash = HashDeSenha.gerar(Validacao.textoObrigatorio(dados.getSenha(), "senha"));
        this.documento = Validacao.textoObrigatorio(dados.getDocumento(), "documento");
        this.telefone = dados.getTelefone();
    }

    public boolean autenticar(String login, String senha) {
        return estaAtivo()
                && this.login.equals(login)
                && senha != null
                && HashDeSenha.confere(senha, senhaHash);
    }

    public void alterarSenha(String novaSenha) {
        this.senhaHash = HashDeSenha.gerar(Validacao.textoObrigatorio(novaSenha, "nova senha"));
    }

    public void ativar() { status = StatusUsuario.ATIVO; }

    public void inativar() { status = StatusUsuario.INATIVO; }

    public boolean estaAtivo() { return status == StatusUsuario.ATIVO; }

    public int getIdUsuario() { return idUsuario; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = Validacao.textoObrigatorio(login, "login"); }

    public String getDocumento() { return documento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public StatusUsuario getStatus() { return status; }

    public abstract String getTipoUsuario();
}
