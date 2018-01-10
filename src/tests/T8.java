package tests;

import utils.TransCaixa;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class T8 implements Test {
    private Collection<TransCaixa> transactions;

    public T8(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) biggestTransaction7", this::biggestTransaction7);
        indicators.put("(2) biggestTransaction8", this::biggestTransaction8);


        return indicators;
    }

    public String biggestTransaction7() {
        List<TransCaixa> transactions = new ArrayList<>(this.transactions);

        transactions.sort(new Comparator<TransCaixa>() {
            @Override
            public int compare(TransCaixa t1, TransCaixa t2) {
                return Double.compare(t1.getValor(), t2.getValor());
            }
        });

        for (TransCaixa transaction : transactions) {
            int hour = transaction.getData().getHour();

            if (hour >= 16 && hour <= 20) {
                return transaction.getTrans();
            }
        }

        return null;
    }

    public Optional<String> biggestTransaction8() {
        Predicate<TransCaixa> timeInRange =  t ->
                t.getData().getHour() >= 16 && t.getData().getHour() <= 20;

        return this.transactions.stream()
                .filter(timeInRange)
                .max(Comparator.comparing(TransCaixa::getValor))
                .map(TransCaixa::getTrans);
    }
}
