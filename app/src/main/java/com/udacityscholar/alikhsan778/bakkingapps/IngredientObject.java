package com.udacityscholar.alikhsan778.bakkingapps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus on 3/6/17.
 * cus alikhsan778
 */


public class IngredientObject implements Parcelable {
    double quantity;
    String measure;
    String ingredients;

    public IngredientObject(double quantity , String measure , String ingredients){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredients);
    }

    protected IngredientObject(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredients = in.readString();
    }

    public static final Parcelable.Creator<IngredientObject> CREATOR = new Parcelable.Creator<IngredientObject>() {
        @Override
        public IngredientObject createFromParcel(Parcel source) {
            return new IngredientObject(source);
        }

        @Override
        public IngredientObject[] newArray(int size) {
            return new IngredientObject[size];
        }
    };
}
