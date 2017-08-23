package com.udacityscholar.alikhsan778.bakkingapps;



import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * alikhsan
 * cus
 *
 */


public class RecipeObject implements Parcelable {


    int recipeId;
    String name;
    ArrayList<IngredientObject> ingredientsArrayList;
    ArrayList<StepsObject> stepsArrayList;
    int servings;


    public RecipeObject(int recipeId, String name, ArrayList<IngredientObject> ingredientsArrayList, ArrayList<StepsObject> stepsArrayList, int servings) {
        this.recipeId = recipeId;
        this.name = name;
        this.ingredientsArrayList = ingredientsArrayList;
        this.stepsArrayList = stepsArrayList;
        this.servings = servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.recipeId);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredientsArrayList);
        dest.writeTypedList(this.stepsArrayList);
        dest.writeInt(this.servings);
    }

    protected RecipeObject(Parcel in) {
        this.recipeId = in.readInt();
        this.name = in.readString();
        this.ingredientsArrayList = in.createTypedArrayList(IngredientObject.CREATOR);
        this.stepsArrayList = in.createTypedArrayList(StepsObject.CREATOR);
        this.servings = in.readInt();
    }

    public static final Parcelable.Creator<RecipeObject> CREATOR = new Parcelable.Creator<RecipeObject>() {
        @Override
        public RecipeObject createFromParcel(Parcel source) {
            return new RecipeObject(source);
        }

        @Override
        public RecipeObject[] newArray(int size) {
            return new RecipeObject[size];
        }
    };
}