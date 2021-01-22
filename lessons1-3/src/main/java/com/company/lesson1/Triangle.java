package com.company.lesson1;

public class Triangle extends Figure implements Resizable {

    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double x, double y, double sideA, double sideB, double sideC, double multiplier) {
        super(x, y);
        setTriangle(sideA, sideB, sideC);
        sizeMultiplier(multiplier);
    }

    private void setTriangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public double getArea() {
        double semiPerimeter = (getPerimeter()/2);
        double area = semiPerimeter * ((semiPerimeter-sideA) * (semiPerimeter-sideB) * (semiPerimeter-sideC));
        return Math.sqrt(area);
    }

    public void sizeMultiplier(double multiplier) {
        sideA = sideA * multiplier;
        sideB = sideB * multiplier;
        sideC = sideC * multiplier;
    }

}
