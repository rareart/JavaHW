package Streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Streams<T> {

    private final List<T> list;

    private Streams(List<? extends T> inputList){
        this.list = new ArrayList<>(inputList);
    }

    public static <V> Streams<V> of(List<? extends V> list){
        return new Streams<V>(list);
    }

    public Streams<T> filter(Predicate<? super T> predicate){
        list.removeIf(obj -> !predicate.test(obj));
        return this;
    }

    public Streams<T> transform(UnaryOperator<T> lambda){
        for(T obj : list){
            list.set(list.indexOf(obj), lambda.apply(obj));
        }
        return this;
    }

    public <K,V> Map<K,V> toMap(Function<? super T, ? extends K> keyLambda, Function<? super T, ? extends V> valueLambda){
        Map<K,V> outputMap = new HashMap<>();
        for(T obj : list){
            outputMap.put(keyLambda.apply(obj), valueLambda.apply(obj));
        }
        return outputMap;
    }

}
