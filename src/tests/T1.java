package tests;

import utils.TransCaixa;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class T1 implements Test {
    private Collection<TransCaixa> transactions;

    public T1(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }


    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) sumArray", this::sumArray);
        indicators.put("(2) sumDoubleStream", this::sumDoubleStream);
        indicators.put("(3) sumDoubleStreamP", this::sumDoubleStreamP);
        indicators.put("(4) sumStream", this::sumStream);
        indicators.put("(5) sumStreamP", this::sumStreamP);

        return indicators;
    }

    public double sumArray() {
        double[] values = new double[this.transactions.size()];
        int i = 0;

        for (TransCaixa transaction : this.transactions) {
            values[i++] = transaction.getValor();
        }

        double sum = 0.0;

        for (i = 0; i < values.length; i++) {
            sum += values[i];
        }

        return sum;
    }

    public double sumDoubleStream() {
        DoubleStream values = this.transactions.stream().mapToDouble(TransCaixa::getValor);
        return values.sum();
    }

    public double sumDoubleStreamP() {
        DoubleStream values = this.transactions.parallelStream().mapToDouble(TransCaixa::getValor);
        return values.sum();
    }

    public double sumStream() {
        Stream<Double> values = this.transactions.stream().map(TransCaixa::getValor);
        return values.reduce(0.0, (a,b) -> a + b);
    }

    public double sumStreamP() {
        Stream<Double> values = this.transactions.parallelStream().map(TransCaixa::getValor);
        return values.reduce(0.0, (a,b) -> a + b);
    }
}
