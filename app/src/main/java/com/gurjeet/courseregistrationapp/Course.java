package com.gurjeet.courseregistrationapp;

public class Course {
    private String name;
    private double fees;
    private int hours;

    public Course(String name, double fees, int hours) {
        this.name = name;
        this.fees = fees;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", fees=" + fees +
                ", hours=" + hours +
                '}';
    }
}
