package com.company.lesson1;

public class Square extends Figure implements Resizable {

    private double side;

    public Square(double x, double y, double side, double multiplier) {
        super(x, y);
        setSquare(side);
        sizeMultiplier(multiplier);
    }

    private void setSquare(double side) {
        this.side = side;
    }

    @Override
    public double getPerimeter() {
        return side * 4;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    public void sizeMultiplier(double multiplier) {
        side = side * multiplier;
    }

}
