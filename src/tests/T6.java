package tests;

import utils.TransCaixa;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class T6 implements Test {
    private Collection<TransCaixa> transactions;

    public T6(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) catalog", this::catalog);
        indicators.put("(2) catalogStream", this::catalogStream);

        return indicators;
    }


    public Map<LocalDateTime, List<TransCaixa>> catalog() {
        Map<LocalDateTime, List<TransCaixa>> catalog = new TreeMap<>();

        for (TransCaixa transaction : this.transactions) {
            if (!catalog.containsKey(transaction.getData())) {
                catalog.put(transaction.getData(), new ArrayList<>());
            }

            catalog.get(transaction.getData()).add(transaction);
        }

        return catalog;
    }

    public Map<LocalDateTime, List<TransCaixa>> catalogStream() {
        return this.transactions.stream()
                .collect(Collectors.groupingBy(TransCaixa::getData));
    }
}
