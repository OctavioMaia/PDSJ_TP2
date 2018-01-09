package tests;

import utils.TransCaixa;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class T5 implements Test {
    private Collection<TransCaixa> transactions;

    public T5(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String name() {
        return "Comparação entre o collect para o TreeSet e a operação sort para uma List";
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) sortTreeSet", this::sortTreeSet);
        indicators.put("(2) sortList", this::sortList);


        return indicators;
    }

    public TreeSet<TransCaixa> sortTreeSet() {
        Comparator<TransCaixa> byDate = Comparator.comparing(TransCaixa::getData);

        return this.transactions.stream()
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(byDate))
                );
    }

    public List<TransCaixa> sortList() {
        Comparator<TransCaixa> byDate = Comparator.comparing(TransCaixa::getData);

        return this.transactions.stream()
                .sorted(byDate).collect(Collectors.toList());
    }
}
