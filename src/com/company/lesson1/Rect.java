package com.company.lesson1;

public class Rect extends Figure implements Resizable {

    private double height;
    private double width;

    public Rect(double x, double y, double height, double width, double multiplier){
        super(x, y);
        setRect(height, width);
        sizeMultiplier(multiplier);
    }

    private void setRect(double height, double width){
        this.height = height;
        this.width = width;
    }

    @Override
    public double getPerimeter(){
        return (height+width)*2;
    }

    @Override
    public double getArea(){
        return height*width;
    }

    public void sizeMultiplier(double multiplier){
        height = height * multiplier;
        width = width * multiplier;
    }
}
