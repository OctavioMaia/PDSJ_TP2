package tests;

import utils.TransCaixa;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class T11 implements Test {
    private Collection<TransCaixa> transactions;

    public T11(Collection<TransCaixa> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Optional<String> input() {
        return Optional.of(this.transactions.size() + " transactions");
    }

    @Override
    public Map<String, Supplier<?>> indicators() {
        Map<String, Supplier<?>> indicators = new LinkedHashMap<>();

        T2 t2 = new T2(this.transactions);
        T5 t5 = new T5(this.transactions);
        T8 t8 = new T8(this.transactions);
        T10 t10 = new T10(this.transactions);

        indicators.put("(1) byDateStream", t2::byDateStream);
        indicators.put("(2) sortList", t5::sortList);
        indicators.put("(3) biggestTransaction8", t8::biggestTransaction8);
        indicators.put("(4) ivaStream", t10::ivaStream);

        return indicators;
    }
}
