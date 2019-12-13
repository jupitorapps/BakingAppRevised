package com.example.bakingappproject.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BakingReceipeDataModel  implements  Parcelable{

    private final int id;
    private final String name;
    private ArrayList<IngrediendsDataModel> ingredients;
    private ArrayList<StepsDataModel> steps;
    private final int servings;
    private final String imageUrl;

    public BakingReceipeDataModel(int id, String name, ArrayList<IngrediendsDataModel> ingredients, ArrayList<StepsDataModel> steps, int servings, String imageUrl) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.imageUrl = imageUrl;
    }

    private BakingReceipeDataModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        imageUrl = in.readString();
    }

    public static final Creator<BakingReceipeDataModel> CREATOR = new Creator<BakingReceipeDataModel>() {
        @Override
        public BakingReceipeDataModel createFromParcel(Parcel in) {
            return new BakingReceipeDataModel(in);
        }

        @Override
        public BakingReceipeDataModel[] newArray(int size) {
            return new BakingReceipeDataModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<IngrediendsDataModel> getIngredients() {
        return ingredients;
    }

    public ArrayList<StepsDataModel> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(imageUrl);
    }


}
