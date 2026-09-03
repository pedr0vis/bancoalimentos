package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * HERANÇA: Doador extends Usuario (Doador É-UM Usuario).
 * ASSOCIAÇÃO: Doador (1..1) -- DoacaoRecebida (0..*), rotulada "Ter".
 * Um doador pode ter várias doações; cada doação pertence a um único doador. 
 * Por isso o Doador guarda uma List<DoacaoRecebida>.
 */
public class Doador extends Usuario {

    private int idDoador;
    private String nome;
    private String historicoDoacao;
    private String instituicaoBeneficiada;

    // Lado "muitos" da associação com DoacaoRecebida
    private List<DoacaoRecebida> doacoesRealizadas = new ArrayList<>();

    public Doador(int idUsuario, String login, String senha, String documento, String telefone,
                   String status, int idDoador, String nome) {
        super(idUsuario, login, senha, documento, telefone, status);
        this.idDoador = idDoador;
        this.nome = nome;
    }

    public int getIdDoador() { return idDoador; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getHistoricoDoacao() { return historicoDoacao; }
    public void setHistoricoDoacao(String historicoDoacao) { this.historicoDoacao = historicoDoacao; }

    public String getInstituicaoBeneficiada() { return instituicaoBeneficiada; }
    public void setInstituicaoBeneficiada(String instituicaoBeneficiada) {
        this.instituicaoBeneficiada = instituicaoBeneficiada;
    }

    public List<DoacaoRecebida> getDoacoesRealizadas() {
        return doacoesRealizadas;
    }

    /**
     * Ponto único de registro de uma doação: mantém os dois lados da
     * associação sincronizados (Doador -> lista de doações, e DoacaoRecebida -> doador que a originou).
     */
    public void registrarDoacao(DoacaoRecebida doacao) {
        doacoesRealizadas.add(doacao);
        doacao.setDoador(this);
    }

    @Override
    public String getTipoUsuario() {
        return "Doador";
    }
}
