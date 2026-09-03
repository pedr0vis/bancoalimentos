package com.bancoalimentos;

import com.bancoalimentos.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de demonstração: cria um pequeno cenário do Banco de Alimentos e
 * mostra os relacionamentos funcionando na prática (herança, composição e
 * associações). Rode esta classe para ver a saída no console.
 */
public class Main {

    public static void main(String[] args) {

        // --- Herança: Doador e Instituicao são Usuario ---
        Doador doador = new Doador(1, "joao.mercado", "senha123", "12345678900",
                "43999990000", "Ativo", 1, "João - Mercado Central");
        doador.setInstituicaoBeneficiada("Casa de Acolhimento Esperança");

        Instituicao instituicao = new Instituicao(2, "creche.luz", "senha456", "12345678000199",
                "43988880000", "Ativo", 1, "Creche Luz do Amanhã",
                "Rua das Flores, 100 - Londrina", "contato@crecheluz.org");

        // --- Composição: Alimento cria seu próprio Lote ---
        Alimento arroz = new Alimento(1, "Arroz", "Arroz tipo 1, pacote 5kg", "Grãos", 200);
        Lote loteArroz = arroz.adicionarLote(1, "2026-12-01", "2026-06-01", 501);

        // --- Associação "Ter": Doador -> DoacaoRecebida, e vínculo com o Lote ---
        DoacaoRecebida doacao = new DoacaoRecebida(1, "2026-08-30");
        doacao.setLote(loteArroz);
        doador.registrarDoacao(doacao);

        // --- Associação "Registrar": RelatorioEstoque -> Descarte/Distribuicao ---
        RelatorioEstoque relatorio = new RelatorioEstoque(1, "2026-12-01", "2026-08-31");

        Descarte descarte = new Descarte(1, "Validade vencida", 5, "2026-08-29");
        descarte.adicionarLote(loteArroz);
        relatorio.registrarDescarte(descarte);

        Distribuicao distribuicao = new Distribuicao(1, 50, "2026-08-30");
        distribuicao.adicionarAlimento(arroz);
        relatorio.registrarDistribuicao(distribuicao);

        // --- Associação "Emitir": Instituicao -> PedidoRetirada ---
        PedidoRetirada pedido = new PedidoRetirada(1, "Pendente");
        pedido.adicionarLote(loteArroz);
        instituicao.emitirPedido(pedido);

        // --- Polimorfismo: tratando Doador e Instituicao como Usuario ---
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(doador);
        usuarios.add(instituicao);

        System.out.println("=== Usuários cadastrados ===");
        for (Usuario u : usuarios) {
            System.out.println(u.getLogin() + " -> " + u.getTipoUsuario());
        }

        System.out.println("\n=== Rastreabilidade da doação ===");
        System.out.println("Doação #" + doacao.getIdDoacao() + " feita por " + doador.getNome()
                + " -> Lote " + doacao.getLote().getNumeroLote()
                + " -> Alimento: " + doacao.getLote().getAlimento().getNome());

        System.out.println("\n=== Relatório de estoque ===");
        System.out.println("Quantidade descartada (calculada automaticamente): "
                + relatorio.getQuantidadeDescarte());
        System.out.println("Distribuições registradas: " + relatorio.getDistribuicoes().size());

        System.out.println("\n=== Pedido de retirada ===");
        System.out.println("Instituição: " + instituicao.getNomeFantasia()
                + " | Pedidos emitidos: " + instituicao.getPedidos().size()
                + " | Status do pedido #1: " + pedido.getStatusPedido());
    }
}
