package com.company.lesson3;

import java.util.Map;

public interface CountMap<E> {
    void add(E obj);

    int getCount(E obj);

    int remove(E obj);

    int size();

    void addAll(CountMap<E> source);

    Map<E, Integer> toMap();

    void toMap(Map<? super E, ? super Integer> destination);
}
