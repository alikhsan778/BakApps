package com.udacityscholar.alikhsan778.bakkingapps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * alikhsan778
 */


public class StepsObject implements Parcelable {
    int stepid;
    String shortDescription;
    String description;
    String videoUrl;
    String thumbnailUrl;

    public StepsObject(int stepid, String shortDescription , String description, String videoUrl , String thumbnailUrl){
        this.stepid=stepid;
        this.shortDescription=shortDescription;
        this.description=description;
        this.videoUrl=videoUrl;
        this.thumbnailUrl=thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stepid);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoUrl);
        dest.writeString(this.thumbnailUrl);
    }

    protected StepsObject(Parcel in) {
        this.stepid = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoUrl = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<StepsObject> CREATOR = new Parcelable.Creator<StepsObject>() {
        @Override
        public StepsObject createFromParcel(Parcel source) {
            return new StepsObject(source);
        }

        @Override
        public StepsObject[] newArray(int size) {
            return new StepsObject[size];
        }
    };
}
