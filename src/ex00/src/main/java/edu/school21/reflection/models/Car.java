package edu.school21.reflection.models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Car {
    private String make;
    private String model;
    private double price;

    public Car() {
        this.model = "Default model";
        this.make = "Default make";
        this.price = 0.;
    }

    public Car(String model, String make, double price) {
        this.model = model;
        this.make = make;
        this.price = price;
    }

    public double priceIncrease(double value){
        this.price += value;
        return price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", make='" + make + '\'' +
                ", price=" + price +
                '}';
    }
}
