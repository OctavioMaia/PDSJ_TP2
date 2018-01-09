package tests;

import utils.TransCaixa;

import java.time.temporal.ChronoField;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class T9 implements Test {
    private Collection<TransCaixa> transactions;

    public T9(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) totalInWeekList", this::totalInWeekList);
        indicators.put("(2) totalInWeekStream", this::totalInWeekStream);

        return indicators;
    }

    public double totalInWeekList() {
        final int week = 12;
        List<List<TransCaixa>> byWeek = new ArrayList<>();

        // Inicializar cada uma das listas
        for (int i = 0; i < 54; i++) {
            byWeek.add(i, new ArrayList<>());
        }

        for (TransCaixa transaction : this.transactions) {
            byWeek.get(transaction.getData()
                    .get(ChronoField.ALIGNED_WEEK_OF_YEAR))
                    .add(transaction);
        }

        // Calcular total faturado
        double total = 0.0;

        for (TransCaixa transaction : byWeek.get(week)) {
            total += transaction.getValor();
        }

        return total;
    }

    public double totalInWeekStream() {
        final int week = 12;
        Map<Integer, List<TransCaixa>> byWeek = this.transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().get(ChronoField.ALIGNED_WEEK_OF_YEAR)));
        return byWeek.entrySet().stream()
                .filter(e -> e.getKey() == week)
                .findFirst()
                .map(e -> e.getValue().stream().mapToDouble(TransCaixa::getValor).sum())
                .orElse(0.0);
    }
}
