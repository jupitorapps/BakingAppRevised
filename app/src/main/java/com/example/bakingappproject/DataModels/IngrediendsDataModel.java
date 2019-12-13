package com.example.bakingappproject.DataModels;

import com.google.gson.annotations.SerializedName;

public class IngrediendsDataModel {


    @SerializedName("quantity")
    private final float quantity;
    @SerializedName("measure")
    private final String measure;
    private final String ingredient;


    public IngrediendsDataModel(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
