package tests;

import utils.TransCaixa;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class T7 implements Test {
    private List<TransCaixa> transactions;

    public T7(List<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) sum", this::sum);
        indicators.put("(2) sumPartition", this::sumPartition);
        indicators.put("(3) sumP", this::sumStream);
        indicators.put("(4) sumPartitionStream", this::sumPartitionStream);
        indicators.put("(5) sumStreamP", this::sumStreamP);
        indicators.put("(6) sumPartitionStreamP", this::sumPartitionStreamP);

        return indicators;
    }

    public double sum() {
        double res = 0.0;
        for (TransCaixa transCaixa : this.transactions) {
            res += transCaixa.getValor();
        }

        return res;
    }

    public double sumPartition() {
        // Make the partitions
        List<List<TransCaixa>> partitions = new ArrayList<>();
        int[] pos = {
                0,
                this.transactions.size() - 3 * transactions.size() / 4,
                this.transactions.size() - 2 * transactions.size() / 4,
                this.transactions.size() - 1 * transactions.size() / 4,
                this.transactions.size() - 0 * transactions.size() / 4,
        };

        partitions.add(this.transactions.subList(pos[0], pos[1]));
        partitions.add(this.transactions.subList(pos[1], pos[2]));
        partitions.add(this.transactions.subList(pos[2], pos[3]));
        partitions.add(this.transactions.subList(pos[3], pos[4]));

        // Compute the result
        double res = 0.0;
        for (List<TransCaixa> list : partitions) {
            for (TransCaixa transCaixa : list) {
                res += transCaixa.getValor();
            }
        }

        return res;
    }

    public double sumStream() {
        return this.transactions.stream()
                .mapToDouble(TransCaixa::getValor)
                .sum();
    }

    public double sumPartitionStream() {
        double res = 0.0;
        Spliterator<TransCaixa> split = this.transactions.stream().spliterator();

        for (int i = 0; i < 4; i++) {
            res += StreamSupport.stream(split.trySplit(), false)
                    .mapToDouble(TransCaixa::getValor)
                    .sum();
        }

        return res;
    }

    public double sumStreamP() {
        return this.transactions.parallelStream()
                .mapToDouble(TransCaixa::getValor)
                .sum();
    }

    public double sumPartitionStreamP() {
        double res = 0.0;
        Spliterator<TransCaixa> split = this.transactions.stream().spliterator();

        for (int i = 0; i < 4; i++) {
            res += StreamSupport.stream(split.trySplit(), true)
                    .mapToDouble(TransCaixa::getValor)
                    .sum();
        }

        return res;
    }

}
