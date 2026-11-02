package com.bancoalimentos;

import com.bancoalimentos.model.*;

import com.bancoalimentos.model.Alimento;
import com.bancoalimentos.model.DadosDeUsuario;
import com.bancoalimentos.model.Descarte;
import com.bancoalimentos.model.Distribuicao;
import com.bancoalimentos.model.DoacaoRecebida;
import com.bancoalimentos.model.Doador;
import com.bancoalimentos.model.Instituicao;
import com.bancoalimentos.model.Lote;
import com.bancoalimentos.model.PedidoRetirada;
import com.bancoalimentos.model.RelatorioEstoque;
import com.bancoalimentos.model.Usuario;

import java.time.LocalDate;
import java.util.List;
/**
 * Classe de demonstração: cria um pequeno cenário do Banco de Alimentos e
 * mostra os relacionamentos funcionando na prática (herança, composição e
 * associações). Rode esta classe para ver a saída no console.
 */
public class Main {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 20);

    public static void main(String[] args) {
        Doador doador = criarDoador();
        Instituicao instituicao = criarInstituicao();

        Alimento arroz = new Alimento(1, "Arroz", "Arroz tipo 1, pacote 5kg", "Grãos", 200);
        Lote loteMaisNovo = arroz.adicionarLote(2, LocalDate.of(2027, 6, 1), LocalDate.of(2026, 8, 1), 502);
        Lote loteMaisAntigo = arroz.adicionarLote(1, LocalDate.of(2026, 12, 1), LocalDate.of(2026, 6, 1), 501);

        DoacaoRecebida doacao = doador.realizarDoacao(1, HOJE.minusDays(2));
        doacao.adicionarLote(loteMaisNovo);
        doacao.adicionarLote(loteMaisAntigo);

        RelatorioEstoque relatorio = new RelatorioEstoque(1, HOJE);
        relatorio.registrarDoacao(doacao);
        relatorio.registrarDistribuicao(new Distribuicao(1, 50, HOJE, loteMaisAntigo));
        relatorio.registrarDescarte(new Descarte(1, "Embalagem danificada", 5, HOJE, loteMaisAntigo));

        PedidoRetirada pedido = instituicao.emitirPedidoRetirada(1);
        pedido.adicionarLote(loteMaisAntigo);
        pedido.aprovar();

        imprimirUsuarios(List.of(doador, instituicao));
        imprimirRastreabilidade(doador, doacao);
        imprimirEstoque(arroz);
        System.out.println(System.lineSeparator() + "=== " + relatorio.gerarRelatorio());
        System.out.println(System.lineSeparator() + "=== Pedido de retirada ===");
        System.out.println(instituicao.getNomeFantasia() + " | pedido #1: " + pedido.getStatusPedido());
    }

    private static Doador criarDoador() {
        DadosDeUsuario acesso = new DadosDeUsuario(1, "joao.mercado", "senha123", "12345678900", "43999990000");
        Doador doador = new Doador(acesso, 1, "João - Mercado Central");
        doador.setInstituicaoBeneficiada("Casa de Acolhimento Esperança");
        return doador;
    }

    private static Instituicao criarInstituicao() {
        DadosDeUsuario acesso = new DadosDeUsuario(2, "creche.luz", "senha456", "12345678000199", "43988880000");
        return new Instituicao(acesso, 1, "Creche Luz do Amanhã", "Rua das Flores, 100 - Londrina", "contato@crecheluz.org");
    }

    private static void imprimirUsuarios(List<Usuario> usuarios) {
        System.out.println("=== Usuários cadastrados ===");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.getLogin() + " -> " + usuario.getTipoUsuario() + " (" + usuario.getStatus() + ")");
        }
    }

    private static void imprimirRastreabilidade(Doador doador, DoacaoRecebida doacao) {
        System.out.println(System.lineSeparator() + "=== Rastreabilidade da doação ===");
        for (Lote lote : doacao.listarLotes()) {
            System.out.println("Doação #" + doacao.getIdDoacao() + " de " + doador.getNome()
                    + " -> lote " + lote.getNumeroLote() + " -> " + lote.getAlimento().getNome());
        }
    }

    private static void imprimirEstoque(Alimento alimento) {
        System.out.println(System.lineSeparator() + "=== Estoque de " + alimento.getNome() + " (ordem FEFO) ===");
        System.out.println("Saldo: " + alimento.getQuantidade());
        for (Lote lote : alimento.consultarLotes()) {
            System.out.println("Lote " + lote.getNumeroLote() + " vence em " + lote.getDataValidade()
                    + " (" + lote.diasParaVencer(HOJE) + " dias)");
        }
    }
}
