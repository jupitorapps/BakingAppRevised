package com.example.bakingappproject.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class StepsDataModel implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public StepsDataModel(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    protected StepsDataModel(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<StepsDataModel> CREATOR = new Creator<StepsDataModel>() {
        @Override
        public StepsDataModel createFromParcel(Parcel in) {
            return new StepsDataModel(in);
        }

        @Override
        public StepsDataModel[] newArray(int size) {
            return new StepsDataModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
