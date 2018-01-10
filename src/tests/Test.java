package tests;

import utils.Crono;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Test {
    /**
     * Returns a textual description of the input of this test.
     * If no input is given, then it should return an empty Optional.
     *
     * @return textual description of the input if it exists.
     */
    default Optional<String> input() {
        return Optional.empty();
    }

    /**
     * Returns all the indicators that this test should run.
     * Each indicator is an association between its textual description
     * and the function (Supplier) that it should run.
     *
     * @return a map, where the keys are the textual description of the
     *         indicator, and the values are the supplier to run.
     */
    Map<String, Supplier<?>> indicators();

    /**
     * Calculates the times needed to run each one of the indicators.
     *
     * @return a map, where the keys are the textual description of the
     *         indicator, and the values are theirs results in seconds.
     */
    default Map<String, Double> results() {
        return indicators().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> measure(entry.getValue()).getKey()
        ));
    }

    /**
     * Measures the required time to run the given supplier.
     * Before the supplier is ran, it does a 5 turns warmup and
     * cleans the memory.
     * @author FMM
     *
     * @param supplier supplier to run
     * @param <R> type of the result of the supplier
     * @return An entry composed by the time in seconds that the
     *         supplier took to run, and the result of that supplier.
     */
    static <R> SimpleEntry<Double, R> measure(Supplier<R> supplier) {
        for (int i = 0; i < 5; i++) supplier.get();
        System.gc();

        Crono.start();
        R result = supplier.get();
        Double time = Crono.stop();
        return new SimpleEntry<>(time, result);
    }
}