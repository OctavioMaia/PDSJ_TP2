package tests;

import utils.TransCaixa;

import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class T12 implements Test {
    private Collection<TransCaixa> transactions;

    public T12(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }
    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) totalByBox", this::totalByBox);
        indicators.put("(2) totalByBoxConcurrent", this::totalByBoxConcurrentMap);

        return indicators;
    }


    public Map<String, Double> totalByBox() {
        // Tabela que associa a cada nº de caixa a table que contém para
        // cada mês as transações dessa caixa
        Map<String, Map<Month, List<TransCaixa>>> table = this.transactions
                .stream()
                .collect(Collectors.groupingBy(TransCaixa::getCaixa))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        l -> l.getValue().stream()
                                .collect(Collectors.groupingBy(
                                        t -> t.getData().getMonth())
                                ))
                );
        // Cálculo para cada nº de caixa do total faturado
        return table.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().values().stream()
                                .flatMap(Collection::stream)
                                .mapToDouble(TransCaixa::getValor)
                                .sum()));
    }

    public Map<String, Double> totalByBoxConcurrentMap() {
        // Tabela que associa a cada nº de caixa a table que contém para
        // cada mês as transações dessa caixa
        ConcurrentMap<String, Map<Month, List<TransCaixa>>> table = this.transactions
                .stream()
                .collect(Collectors.groupingBy(TransCaixa::getCaixa))
                .entrySet()
                .stream()
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey,
                        l -> l.getValue().stream()
                                .collect(Collectors.groupingBy(
                                        t -> t.getData().getMonth())
                                ))
                );

        // Cálculo para cada nº de caixa do total faturado
        return table.entrySet().stream()
                .collect(Collectors.toConcurrentMap(
                        Map.Entry::getKey,
                        e -> e.getValue().values().stream()
                                .flatMap(Collection::stream)
                                .mapToDouble(TransCaixa::getValor)
                                .sum()));
    }
}
