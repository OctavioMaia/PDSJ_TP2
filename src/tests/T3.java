package tests;


import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class T3 implements Test {
    private int[] values;

    public T3(int nrandom) {
        Random random = new Random();
        this.values = new int[nrandom];

        for (int i = 0; i < nrandom; i++) {
            this.values[i] = random.nextInt(9999);
        }
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.values.length + " random numbers");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("(1) uniqueArray", this::uniqueArray);
        indicators.put("(2) uniqueList", this::uniqueList);
        indicators.put("(3) uniqueIntStream", this::uniqueIntStream);

        return indicators;
    }

    public Integer[] uniqueArray() {
        Set<Integer> nodups = new TreeSet<>();
        for (int value : this.values) {
            nodups.add(value);
        }

        return nodups.toArray(new Integer[nodups.size()]);
    }

    public Integer[] uniqueList() {
        List<Integer> aux = new ArrayList<>();
        for (int value : this.values) {
            aux.add(value);
        }

        List<Integer> nodups = new ArrayList<>(new HashSet<>(aux));
        return nodups.toArray(new Integer[nodups.size()]);
    }

    public int[] uniqueIntStream() {
        IntStream values = new Random().ints(this.values.length, 0, 9999);
        return values.distinct().toArray();
    }
}