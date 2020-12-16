package com.company.lesson1;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //Запускается отсюда:

        //demoForArrays();
        //demoForGeometricFigures();

        //System.out.print(TemperatureConverter.fromCelsiusToFahrenheit(20));
        //System.out.print(TemperatureConverter.fromFahrenheitToCelsius(68));
    }

    private static void demoForArrays(){
        double[] someArr = {222, 1, 14, 86, 92, 0, 39, 90.1, 2, 100};
        SortArrays objArr = new SortArrays(someArr);
        objArr.arrayOptions();
        System.out.print("массив объекта на выходе: " + Arrays.toString(objArr.getArr()));
    }

    private static void demoForGeometricFigures(){
        System.out.println("Круг:");
        Circle objCircle = new Circle(22.5, -7, 5, 2);
        objCircle.printCoordinates();
        System.out.println("Периметр = " + String.format("%.3f",objCircle.getPerimeter()));
        System.out.println("Площадь = "+ String.format("%.3f",objCircle.getArea()));

        System.out.println("Прямоугольник:");
        Rect objRect = new Rect(-77.1, 52.2, 10, 21, 0.5);
        objRect.printCoordinates();
        System.out.println("Периметр = " + String.format("%.3f",objRect.getPerimeter()));
        System.out.println("Площадь = "+ String.format("%.3f",objRect.getArea()));

        System.out.println("Квадрат:");
        Square objSquare = new Square(12, 76, 4.4, 3);
        objSquare.printCoordinates();
        System.out.println("Периметр = " + String.format("%.3f",objSquare.getPerimeter()));
        System.out.println("Площадь = "+ String.format("%.3f",objSquare.getArea()));

        System.out.println("Треугольник:");
        Triangle objTriangle = new Triangle(-2.2, -18, 22.6, 14, 24, 1.1);
        objTriangle.printCoordinates();
        System.out.println("Периметр = " + String.format("%.3f",objTriangle.getPerimeter()));
        System.out.println("Площадь = "+ String.format("%.3f",objTriangle.getArea()));
    }
}