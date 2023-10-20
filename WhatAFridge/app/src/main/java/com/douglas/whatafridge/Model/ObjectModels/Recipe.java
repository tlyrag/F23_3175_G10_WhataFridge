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

    public boolean vegetarian;
    public boolean vegan;
    public boolean glutenFree;
    public boolean dairyFree;
    public boolean veryHealthy;
    public boolean cheap;
    public boolean veryPopular;
    public boolean sustainable;
    public boolean lowFodmap;
    public int weightWatcherSmartPoints;
    public String gaps;
    public int preparationMinutes;
    public int cookingMinutes;
    public int aggregateLikes;
    public int healthScore;
    public String creditsText;
    public String license;
    public String sourceName;
    public double pricePerServing;
    public int readyInMinutes;
    public int servings;
    public String sourceUrl;
    public Nutrition nutrition;
    public String summary;
    public ArrayList<Object> cuisines;
    public ArrayList<String> dishTypes;
    public ArrayList<Object> diets;
    public ArrayList<Object> occasions;
    public String instructions;
    public ArrayList<Object> analyzedInstructions;
    public Object originalId;
    public String spoonacularSourceUrl;
}
