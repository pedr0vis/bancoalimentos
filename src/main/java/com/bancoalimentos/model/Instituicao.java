package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HERANÇA: Instituicao extends Usuario (Instituicao é um Usuario).
 * ASSOCIAÇÃO: Instituicao (1..1) -- PedidoRetirada (0..*), rotulada "Emitir".
 * Uma instituição pode emitir vários pedidos de retirada; cada pedido pertence a exatamente uma instituição.
 */
public class Instituicao extends Usuario {

    private int idInstituicao;
    private String nomeFantasia;
    private String endereco;
    private String email;
    
    private final List<PedidoRetirada> pedidos = new ArrayList<>();

    public Instituicao(DadosDeUsuario dados, int idInstituicao, String nomeFantasia, String endereco, String email) {
        super(dados);
        this.idInstituicao = idInstituicao;
        this.nomeFantasia = Validacao.textoObrigatorio(nomeFantasia, "nome fantasia");
        this.endereco = Validacao.textoObrigatorio(endereco, "endereço");
        this.email = Validacao.textoObrigatorio(email, "e-mail");
    }

    /** Único caminho para criar um pedido, o que garante que ele sempre tem uma instituição. */
    public PedidoRetirada emitirPedidoRetirada(int idPedido) {
        PedidoRetirada pedido = new PedidoRetirada(idPedido, this);
        pedidos.add(pedido);
        return pedido;
    }

    public List<PedidoRetirada> consultarPedidos() {
        return Collections.unmodifiableList(pedidos);
    }

    public int getIdInstituicao() { return idInstituicao; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = Validacao.textoObrigatorio(nomeFantasia, "nome fantasia");
    }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = Validacao.textoObrigatorio(endereco, "endereço"); }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = Validacao.textoObrigatorio(email, "e-mail"); }

    @Override
    public String getTipoUsuario() {
        return "Instituição";
    }
}
