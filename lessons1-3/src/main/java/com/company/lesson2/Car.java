package com.company.lesson2;

import java.util.Objects;

public class Car implements Comparable<Car> {
    private final String model;
    private final String type;

    public Car(String model, String type){
        this.model = model;
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(Car o) {
        return this.type.compareTo(o.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(type, car.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}