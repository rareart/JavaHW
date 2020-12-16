package com.company.lesson1;

abstract class Figure {

    protected double x;
    protected double y;

    public Figure(double x, double y){
        this.x = x;
        this.y = y;
    }

    protected void printCoordinates(){
        System.out.println("Координата = " + x + ", " + y);
    }

    protected abstract double getPerimeter();
    protected abstract double getArea();
}

interface Resizable{
    void sizeMultiplier(double multiplier);
}