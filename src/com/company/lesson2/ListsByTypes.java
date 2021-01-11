package com.company.lesson2;

import java.util.*;

public class ListsByTypes {

    private final List<List<Car>> arrayLists;
    private final ArrayList<Car> inputCarArrayList;

    public ListsByTypes(ArrayList<Car> carArrayList){
        this.inputCarArrayList = carArrayList;
        arrayLists = new ArrayList<>(sizeOf());
        generate();
    }

    public void printLists(){
        int counter = 1;
        for(List<Car> carList : arrayLists){
            System.out.println("List number " + counter++);
            CarListLoader.print((ArrayList<Car>) carList);
        }
    }

    private int sizeOf(){
        HashSet<Car> uniqueTypes = new HashSet<>(inputCarArrayList);
        return uniqueTypes.size();
    }

    private void generate(){
        Collections.sort(inputCarArrayList);
        int startIndex = 0;
        int indexOfArr = 0;
        Car lastObj = inputCarArrayList.get(0);
       for (Car car : inputCarArrayList){
           if(!car.getType().equals(lastObj.getType())){
               arrayLists.add(indexOfArr, new ArrayList<>(inputCarArrayList.subList(startIndex, inputCarArrayList.indexOf(car))));
               startIndex = inputCarArrayList.indexOf(car);
               indexOfArr++;
           }
           lastObj = car;
       }
        arrayLists.add(indexOfArr, new ArrayList<>(inputCarArrayList.subList(startIndex, inputCarArrayList.size())));
    }
}
