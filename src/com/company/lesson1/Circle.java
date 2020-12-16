package com.company.lesson1;

public class Circle extends Figure implements Resizable {

    private final double pi = 3.1415D;
    private double radius;

    public Circle(double x, double y, double radius, double multiplier) {
        super(x,y);
        setCircle(radius);
        sizeMultiplier(multiplier);
    }

    private void setCircle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getPerimeter() {
        return (radius * 2 * pi);
    }

    @Override
    public double getArea() {
        return (radius * radius * pi);
    }

    public void sizeMultiplier(double multiplier) {
        radius = radius * multiplier;
    }

}
