package com.douglas.whatafridge.Model.ObjectModels;

import java.util.ArrayList;

public class Ingredients {
    public int id;
    public double amount;
    public String BestBefore;
    public String unit;
    public String unitLong;
    public String unitShort;
    public String aisle;
    public String name;
    public String original;
    public String originalName;
    public ArrayList<String> meta;
    public String extendedName;
    public String image;
    public Ingredients() {
    }
    public Ingredients(double amount, String bestBefore, String name) {
        this.amount = amount;
        this.BestBefore = bestBefore;
        this.name = name;
    }
    public Ingredients(int id,double amount, String bestBefore, String name) {
        this.id =id;
        this.amount = amount;
        this.BestBefore = bestBefore;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", amount=" + amount +
                ", BestBefore='" + BestBefore + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
