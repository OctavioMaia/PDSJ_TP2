package tests;

import utils.TransCaixa;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class T10 implements Test {
    private Collection<TransCaixa> transactions;

    public T10(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) iva", this::iva);
        indicators.put("(2) ivaStream", this::ivaStream);

        return indicators;
    }

    public List<Double> iva() {
        List<Double> ivas = new ArrayList<>(13);

        // Initialize the IVAS with 0
        for (int i = 0; i < 13; i++) {
            ivas.add(0.0);
        }

        for (TransCaixa transaction : this.transactions) {
            Month month = transaction.getData().getMonth();
            Double value = transaction.getValor();
            Double iva = value < 20               ? 0.15 * value :
                        (value > 20 && value < 20 ? 0.20 * value :
                                                    0.23 * value);
            ivas.set(month.getValue(), ivas.get(month.getValue()) + iva);
        }

        return ivas;
    }

    public Map<Month, Double> ivaStream() {
        return this.transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getData().getMonth()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToDouble(TransCaixa::getValor)
                                .map(x -> x < 20           ? 0.15 * x :
                                         (x > 20 && x < 20 ? 0.20 * x :
                                                             0.23 * x ))
                        .sum()
                ));
    }
}
