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

        T1 t1 = new T1(this.transactions);
        T2 t2 = new T2(this.transactions);
        T5 t5 = new T5(this.transactions);
        T8 t8 = new T8(this.transactions);
        T10 t10 = new T10(this.transactions);

        indicators.put("(1) sumStreamP", t1::sumStreamP);
        indicators.put("(2) byDateStreamP", t2::byDateStreamP);
        indicators.put("(3) sortList", t5::sortList);
        indicators.put("(4) biggestTransaction8", t8::biggestTransaction8);

        return indicators;
    }
}
