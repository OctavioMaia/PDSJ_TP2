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
    public String name() {
        return "Cálculo da soma dos valores de todas as transações";
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) double[]", this::sumValuesArray);
        indicators.put("(2) Double Stream", this::sumValuesDoubleStream);
        indicators.put("(3) Double Stream (parallel)", this::sumValuesDoubleStreamParallel);
        indicators.put("(4) Stream <Double>", this::sumValuesStream);
        indicators.put("(5) Stream <Double> (parallel)", this::sumValuesStreamParalell);

        return indicators;
    }

    public double sumValuesArray() {
        double[] values = new double[transactions.size()];
        int i = 0;

        for (TransCaixa transaction : transactions) {
            values[i++] = transaction.getValor();
        }

        double sum = 0.0;

        for (i = 0; i < values.length; i++) {
            sum += values[i];
        }

        return sum;
    }

    public double sumValuesDoubleStream() {
        DoubleStream values = transactions.stream().mapToDouble(TransCaixa::getValor);
        return values.sum();
    }

    public double sumValuesDoubleStreamParallel() {
        DoubleStream values = transactions.parallelStream().mapToDouble(TransCaixa::getValor);
        return values.sum();
    }

    public double sumValuesStream() {
        Stream<Double> values = transactions.stream().map(TransCaixa::getValor);
        return values.reduce(0.0, (a,b) -> a + b);
    }

    public double sumValuesStreamParalell() {
        Stream<Double> values = transactions.parallelStream().map(TransCaixa::getValor);
        return values.reduce(0.0, (a,b) -> a + b);
    }
}
