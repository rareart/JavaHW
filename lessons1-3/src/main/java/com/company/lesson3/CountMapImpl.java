package com.company.lesson3;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<E> implements CountMap<E> {

    private final Map<E, Integer> internalMap;

    public CountMapImpl() {
        this.internalMap = new HashMap<>();
    }

    @Override
    public void add(E obj) {
        if(internalMap.containsKey(obj)){
            Integer tempCounter = internalMap.get(obj);
            internalMap.replace(obj, ++tempCounter);
        } else {
            internalMap.put(obj, 1);
        }
    }

    @Override
    public int getCount(E obj) {
        return internalMap.getOrDefault(obj, 0);
    }

    @Override
    public int remove(E obj) {
        int tempCounter = internalMap.get(obj);
        internalMap.remove(obj);
        return tempCounter;
    }

    @Override
    public int size() {
        return internalMap.size();
    }

    @Override
    public void addAll(CountMap<E> source) {
        Map<E, Integer> tmpMap = source.toMap();
        for(E key : tmpMap.keySet()){
            if (internalMap.containsKey(key)) {
                int oldValue = internalMap.get(key);
                internalMap.replace(key, oldValue+tmpMap.get(key));
            } else {
                internalMap.put(key, tmpMap.get(key));
            }
        }
    }

    @Override
    public Map<E, Integer> toMap() {
        Map<E, Integer> outputMap = new HashMap<>();
        toMap(outputMap);
        return outputMap;
    }

    @Override
    public void toMap(Map<? super E, ? super Integer> destination) {
        for(Map.Entry<E, Integer> entry : this.internalMap.entrySet()){
            destination.put(entry.getKey(), entry.getValue());
        }
    }
}
