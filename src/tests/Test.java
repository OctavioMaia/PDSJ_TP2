package tests;

import utils.Crono;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Test {
    /**
     *
     * @return
     */
    default Optional<String> input() {
        return Optional.empty();
    }

    /**
     *
     * @return
     */
    Map<String, Supplier<?>> indicators();

    /**
     *
     * @return
     */
    default Map<String, Double> results() {
        return indicators().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> measure(entry.getValue()).getKey()
        ));
    }

    /**
     *
     * @param supplier
     * @param <R>
     * @return
     */
    static <R> AbstractMap.SimpleEntry<Double, R> measure(Supplier<R> supplier) {
        for (int i = 0; i < 5; i++) supplier.get();
        System.gc();

        Crono.start();
        R result = supplier.get();
        Double time = Crono.stop();
        return new AbstractMap.SimpleEntry<>(time, result);
    }
}