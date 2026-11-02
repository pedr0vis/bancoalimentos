package com.bancoalimentos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HERANÇA: Doador extends Usuario (Doador é um Usuario).
 * ASSOCIAÇÃO: Doador (1..1) -- DoacaoRecebida (0..*), rotulada "Ter".
 * Um doador pode ter várias doações; cada doação pertence a um único doador. 
 * Por isso o Doador guarda uma List<DoacaoRecebida>.
 */
public class Doador extends Usuario {

    private int idDoador;
    private String nome;
    private String instituicaoBeneficiada;
    private final List<DoacaoRecebida> doacoes = new ArrayList<>();

    public Doador(DadosDeUsuario dados, int idDoador, String nome) {
        super(dados);
        this.idDoador = idDoador;
        this.nome = Validacao.textoObrigatorio(nome, "nome");
    }

    /** Único caminho para criar uma doação, o que garante que ela sempre tem um doador. */
    public DoacaoRecebida realizarDoacao(int idDoacao, LocalDate data) {
        DoacaoRecebida doacao = new DoacaoRecebida(idDoacao, data, this);
        doacoes.add(doacao);
        return doacao;
    }

    public List<DoacaoRecebida> consultarDoacoes() {
        return Collections.unmodifiableList(doacoes);
    }

    public int getIdDoador() { return idDoador; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = Validacao.textoObrigatorio(nome, "nome"); }

    public String getInstituicaoBeneficiada() { return instituicaoBeneficiada; }
    public void setInstituicaoBeneficiada(String instituicaoBeneficiada) {
        this.instituicaoBeneficiada = instituicaoBeneficiada;
    }

    @Override
    public String getTipoUsuario() {
        return "Doador";
    }
}
