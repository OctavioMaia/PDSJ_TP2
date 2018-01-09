package tests;


import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class T4 implements Test {
    private int[] values;

    public T4(int nrandom) {
        Random random = new Random();
        this.values = new int[nrandom];

        for (int i = 0; i < nrandom; i++) {
            this.values[i] = random.nextInt(9999);
        }
    }

    @Override
    public String name() {
        return "Comparação entre a aplicação de método estático, BiFunction e Lambda";
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.values.length + " random numbers");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        indicators.put("divSMethodStream", this::divSMethodStream);
        indicators.put("divSMethodStreamP", this::divSMethodStreamP);

        indicators.put("divBiFunStream", this::divBiFunStream);
        indicators.put("divBiFunStreamP", this::divBiFunStreamP);

        indicators.put("divLambdaStream", this::divLambdaStream);
        indicators.put("divLambdaStreamP", this::divLambdaStreamP);

        return indicators;
    }

    public static int div(int x, int y) {
        return x / y;
    }

    public int[] divSMethodStream() {
        return Arrays.stream(this.values).map(x -> div(x, 2)).toArray();
    }

    public int[] divSMethodStreamP() {
        return Arrays.stream(this.values).parallel().map(x -> div(x, 2)).toArray();
    }

    public int[] divBiFunStream() {
        BiFunction<Integer, Integer, Integer> f = (x, y) -> x / y;

        return Arrays.stream(this.values).map(x -> f.apply(x, 2)).toArray();
    }

    public int[] divBiFunStreamP() {
        BiFunction<Integer, Integer, Integer> f = (x, y) -> x / y;

        return Arrays.stream(this.values).parallel().map(x -> f.apply(x, 2)).toArray();
    }

    public int[] divLambdaStream() {
        return Arrays.stream(this.values).map(x -> x / 2).toArray();
    }

    public int[] divLambdaStreamP() {
        return Arrays.stream(this.values).parallel().map(x -> x / 2).toArray();
    }
}
