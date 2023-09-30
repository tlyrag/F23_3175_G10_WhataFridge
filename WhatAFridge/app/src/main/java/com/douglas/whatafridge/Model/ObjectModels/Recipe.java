package com.douglas.whatafridge.Model.ObjectModels;

import com.douglas.whatafridge.Model.ObjectModels.Ingredients;

import java.util.ArrayList;

public class Recipe {
    public int id;
    public String title;
    public String image;
    public String imageType;
    public int usedIngredientCount;
    public int missedIngredientCount;
    public ArrayList<Ingredients> missedIngredients;
    public ArrayList<Ingredients> usedIngredients;
    public ArrayList<Object> unusedIngredients;
    public int likes;
}
