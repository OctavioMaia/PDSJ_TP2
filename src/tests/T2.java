package tests;

import utils.TransCaixa;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class T2 implements Test {
    private Collection<TransCaixa> transactions;

    public T2(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) byDateList", this::byDateList);
        indicators.put("(2) byDateSet", this::byDateSet);
        indicators.put("(3) byDateStream", this::byDateStream);
        indicators.put("(4) byDateStreamP", this::byDateStreamP);

        return indicators;
    }

    public SimpleEntry<List<TransCaixa>, List<TransCaixa>> byDateList() {
        int nelems = 20 * this.transactions.size() / 100;

        List<TransCaixa> sorted = new ArrayList<>(this.transactions);
        sorted.sort(Comparator.comparing(TransCaixa::getData));

        List<TransCaixa> first = sorted.subList(0, nelems);
        List<TransCaixa> last = sorted.subList(
                sorted.size() - 1 - nelems, sorted.size() - 1);

        return new SimpleEntry<>(first, last);
    }

    public SimpleEntry<List<TransCaixa>, List<TransCaixa>> byDateSet() {
        int nelems = 20 * this.transactions.size() / 100;

        TreeSet<TransCaixa> sorted = new TreeSet<>(
                // Com este comparador garante-se que a lista é ordenada
                // e não se removem os elementos iguais
                (t1, t2) -> t1.getData().isBefore(t2.getData()) ? -1 : 1
        );
        sorted.addAll(this.transactions);

        List<TransCaixa> first = new ArrayList<>(sorted)
                .subList(0, nelems);
        List<TransCaixa> last = new ArrayList<>(sorted.descendingSet())
                .subList(0, nelems);

        return new SimpleEntry<>(first, last);
    }

    public SimpleEntry<List<TransCaixa>, List<TransCaixa>> byDateStream() {
        int nelems = 20 * this.transactions.size() / 100;

        List<TransCaixa> first = this.transactions.stream()
                .sorted(Comparator.comparing(TransCaixa::getData))
                .limit(nelems)
                .collect(Collectors.toList());
        List<TransCaixa> last = this.transactions.stream()
                .sorted((t1, t2) -> t2.getData().compareTo(t1.getData()))
                .limit(nelems)
                .collect(Collectors.toList());

        return new SimpleEntry<>(first, last);
    }

    public SimpleEntry<List<TransCaixa>, List<TransCaixa>> byDateStreamP() {
        int nelems = 20 * this.transactions.size() / 100;

        List<TransCaixa> first = this.transactions.stream()
                .sorted(Comparator.comparing(TransCaixa::getData))
                .limit(nelems)
                .collect(Collectors.toList());
        List<TransCaixa> last = this.transactions.stream()
                .sorted((t1, t2) -> t2.getData().compareTo(t1.getData()))
                .limit(nelems)
                .collect(Collectors.toList());

        return new SimpleEntry<>(first, last);
    }
}
