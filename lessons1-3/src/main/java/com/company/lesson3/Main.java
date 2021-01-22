package com.company.lesson3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        countMap();
        //CollectionUtils();
    }

    public static void countMap(){
        CountMap<Integer> countMap = new CountMapImpl<>();
        countMap.add(1);
        countMap.add(10);
        countMap.add(11);
        countMap.add(10);
        countMap.add(0);
        countMap.add(1);
        countMap.add(10);
        countMap.add(8);
        countMap.add(777);

        System.out.println("Size: " + countMap.size());
        System.out.println(countMap.getCount(10));
        System.out.println(countMap.remove(10));
        System.out.println("Size: " + countMap.size());

        Map<Number, Number> extMap = new HashMap<>();
        countMap.toMap(extMap);

        System.out.println("Map (key, value):");
        for(Map.Entry<Number, Number> entry : extMap.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }


        System.out.println("-----------------------");
        CountMap<Integer> countMap2 = new CountMapImpl<>();
        countMap2.add(777);
        countMap2.add(999);
        countMap2.add(999);
        countMap2.add(100);
        countMap.addAll(countMap2);
        System.out.println("After addAll: ");
        System.out.println("Size: " + countMap.size());
        System.out.println(countMap.getCount(777));
        System.out.println(countMap.getCount(999));
        System.out.println(countMap.getCount(100));
    }

    public static void CollectionUtils(){
        List<Integer> list = CollectionUtils.newArrayList();
        CollectionUtils.add(list, 3);
        CollectionUtils.add(list, 1);
        CollectionUtils.add(list, 2);
        CollectionUtils.add(list, 0);
        CollectionUtils.add(list, 5);
        CollectionUtils.add(list, 10);
        CollectionUtils.add(list, 7);
        CollectionUtils.add(list, 8);

        List<Integer> list2 = CollectionUtils.newArrayList();
        CollectionUtils.add(list2, 1);
        CollectionUtils.add(list2, 20);
        CollectionUtils.add(list2, 9);

        System.out.println(CollectionUtils.containsAny(list, list2));
        CollectionUtils.addAll(list2, list);

        System.out.println(list.toString());

        System.out.println(CollectionUtils.range(list, 2, 9).toString());
    }
}
