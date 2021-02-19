package com.company.lesson3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {
    public static <T> void addAll(List<? extends T> source, List<? super T> destination){
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<? extends T> source, T o){
        return source.indexOf(o);
    }

    public static <T> List<T> limit(List<T> source, int size){
        return new ArrayList<>(source.subList(0, size-1));
    }

    public static <T> void add(List<? super T> source, T o){
        source.add(o);
    }

    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2){
        removeFrom.removeAll(c2);
    }

    public static <T> boolean containsAll(List<? super T> c1, List<? extends T> c2){
        return c1.containsAll(c2);
    }

    public static <T> boolean containsAny(List<? super T> c1, List<? extends T> c2){
        for(T obj : c2){
            if (c1.contains(obj)){
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<? super T>> List<T> range(List<T> list, T min, T max){
        List<T> outputList = new ArrayList<>(list);
        Collections.sort(outputList);
        return new ArrayList<>(outputList.subList(outputList.indexOf(min), outputList.indexOf(max)));
    }

    public static <T> List<T> range(List<T> list, T min, T max, Comparator<T> comparator){
        List<T> outputList = new ArrayList<>(list);
        outputList.sort(comparator);
        return new ArrayList<>(outputList.subList(outputList.indexOf(min), outputList.indexOf(max)));
    }

}