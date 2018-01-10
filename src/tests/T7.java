package tests;

import utils.TransCaixa;

import java.util.*;
import java.util.function.Supplier;

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
        Spliterator<TransCaixa> fst = this.transactions.stream().spliterator();
        Spliterator<TransCaixa> snd = fst.trySplit();
        Spliterator<TransCaixa> trd = fst.trySplit();
        Spliterator<TransCaixa> frt = snd.trySplit();


        return 0.0;
    }

    public double sumStreamP() {
        return this.transactions.parallelStream()
                .mapToDouble(TransCaixa::getValor)
                .sum();
    }

    public double sumPartitionStreamP() {
        return 0.0;
    }

}
