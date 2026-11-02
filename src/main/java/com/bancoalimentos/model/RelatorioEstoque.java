package com.bancoalimentos.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * ASSOCIAÇÕES "Registrar":
 *   RelatorioEstoque (1..1) -- Descarte (0..*)
 *   RelatorioEstoque (1..1) -- Distribuicao (0..*)
 * Um relatório agrega vários registros de descarte e de distribuição;
 * cada Descarte/Distribuicao pertence a exatamente um relatório.
 *
 * OBSERVAÇÃO IMPORTANTE: o atributo quantidadeDescarte é calculado
 * automaticamente a partir dos Descartes registrados (veja
 * registrarDescarte), evitando que alguém "digite" um valor incoerente
 * com a lista real.
 */
public class RelatorioEstoque {

    private static final int DIAS_ALERTA_VENCIMENTO = 30;

    private final int idRelatorioEstoque;
    private final LocalDate dataRelatorio;
    
    private final List<DoacaoRecebida> doacoes = new ArrayList<>();
    private final List<Distribuicao> distribuicoes = new ArrayList<>();
    private final List<Descarte> descartes = new ArrayList<>();
public RelatorioEstoque(int idRelatorioEstoque, LocalDate dataRelatorio) {
        this.idRelatorioEstoque = idRelatorioEstoque;
        this.dataRelatorio = Validacao.obrigatorio(dataRelatorio, "data do relatório");
    }

    public void registrarDoacao(DoacaoRecebida doacao) {
        exigirNovo(doacoes, doacao, "doação");
        doacao.registrarDoacao();
        doacoes.add(doacao);
    }

    public void registrarDistribuicao(Distribuicao distribuicao) {
        exigirNovo(distribuicoes, distribuicao, "distribuição");
        distribuicao.registrarDistribuicao();
        distribuicao.setRelatorioEstoque(this);
        distribuicoes.add(distribuicao);
    }

    public void registrarDescarte(Descarte descarte) {
        exigirNovo(descartes, descarte, "descarte");
        descarte.registrarDescarte();
        descarte.setRelatorioEstoque(this);
        descartes.add(descarte);
    }

    /** Doações registradas no mesmo mês do relatório. */
    public int calcularTotalDoadoMes() {
        YearMonth mesDoRelatorio = YearMonth.from(dataRelatorio);
        return (int) doacoes.stream()
                .filter(doacao -> YearMonth.from(doacao.getData()).equals(mesDoRelatorio))
                .count();
    }

    public int getQuantidadeDescarte() {
        return descartes.stream().mapToInt(Descarte::getQuantidade).sum();
    }

    /** Lotes que vencem nos próximos 30 dias, do que vence primeiro para o que vence depois. */
    public List<Lote> listarProximosVencimentos() {
        return doacoes.stream()
                .flatMap(doacao -> doacao.listarLotes().stream())
                .distinct()
                .filter(lote -> !lote.estaVencido(dataRelatorio))
                .filter(lote -> lote.diasParaVencer(dataRelatorio) <= DIAS_ALERTA_VENCIMENTO)
                .sorted(Comparator.comparing(Lote::getDataValidade))
                .toList();
    }

    public Optional<LocalDate> getProxVencimento() {
        return listarProximosVencimentos().stream().map(Lote::getDataValidade).findFirst();
    }

    public String gerarRelatorio() {
        String quebra = System.lineSeparator();
        return "Relatório de estoque #" + idRelatorioEstoque + " (" + dataRelatorio + ")" + quebra
                + "Doações no mês: " + calcularTotalDoadoMes() + quebra
                + "Distribuições registradas: " + distribuicoes.size() + quebra
                + "Quantidade descartada: " + getQuantidadeDescarte() + quebra
                + "Próximo vencimento: " + getProxVencimento().map(LocalDate::toString).orElse("nenhum lote perto de vencer");
    }

    public int getIdRelatorioEstoque() { return idRelatorioEstoque; }
    public LocalDate getDataRelatorio() { return dataRelatorio; }
    public List<Distribuicao> getDistribuicoes() { return Collections.unmodifiableList(distribuicoes); }
    public List<Descarte> getDescartes() { return Collections.unmodifiableList(descartes); }

    private static <T> void exigirNovo(List<T> registrados, T item, String nome) {
        Validacao.obrigatorio(item, nome);
        if (registrados.contains(item)) {
            throw new IllegalArgumentException("Registro duplicado no relatório: " + nome);
        }
    }
}
