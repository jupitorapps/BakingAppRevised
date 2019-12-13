package com.example.bakingappproject.DataModels;

import java.io.Serializable;

public class StepsDataModel implements Serializable {

    private final int id;
    private final String shortDescription;
    private final String description;
    private final String videoURL;
    private final String thumbnailURL;

    public StepsDataModel(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

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
}
