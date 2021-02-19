package com.company.lesson2;

import java.util.ArrayList;

public class CarListLoader {
    public static void init(ArrayList<Car> carArrayList){
        carArrayList.add(new Car("Toyota", "Sedan"));
        carArrayList.add(new Car("Mazda", "Hatchback"));
        carArrayList.add(new Car("Volkswagen", "Sedan"));
        carArrayList.add(new Car("Volkswagen", "SUV"));
        carArrayList.add(new Car("Mercedes-Benz", "Sedan"));
        carArrayList.add(new Car("BMW", "SUV"));
        carArrayList.add(new Car("Mercedes-Benz", "SUV"));
        carArrayList.add(new Car("Nissan", "Sedan"));
        carArrayList.add(new Car("BMW", "Sedan"));
        carArrayList.add(new Car("KIA", "Hatchback"));
    }

    public static void print(ArrayList<Car> carArrayList){
        for (Car temp : carArrayList) {
            System.out.println(temp.getModel() + " " + temp.getType());
        }
    }
}
