package com.bancoalimentos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * HERANÇA: Instituicao extends Usuario (Instituicao É-UM Usuario).
 * ASSOCIAÇÃO: Instituicao (1..1) -- PedidoRetirada (0..*), rotulada "Emitir".
 * Uma instituição pode emitir vários pedidos de retirada; cada pedido pertence a exatamente uma instituição.
 */
public class Instituicao extends Usuario {

    private int idInstituicao;
    private String nomeFantasia;
    private String endereco;
    private String email;

    // Lado "muitos" da associação com PedidoRetirada
    private List<PedidoRetirada> pedidos = new ArrayList<>();

    public Instituicao(int idUsuario, String login, String senha, String documento, String telefone,
                        String status, int idInstituicao, String nomeFantasia, String endereco, String email) {
        super(idUsuario, login, senha, documento, telefone, status);
        this.idInstituicao = idInstituicao;
        this.nomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.email = email;
    }

    public int getIdInstituicao() { return idInstituicao; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<PedidoRetirada> getPedidos() {
        return pedidos;
    }

    /**
     * Mantém os dois lados da associação sincronizados, igual ao padrão usado em Doador.registrarDoacao().
     */
    public void emitirPedido(PedidoRetirada pedido) {
        pedidos.add(pedido);
        pedido.setInstituicao(this);
    }

    @Override
    public String getTipoUsuario() {
        return "Instituição";
    }
}
