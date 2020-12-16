package com.company.lesson1;

public class TemperatureConverter {
    public static double fromCelsiusToFahrenheit(double temp){
        return temp * 1.8 + 32;
    }

    public static double fromFahrenheitToCelsius(double temp){
        return (temp-32)/1.8;
    }
}
