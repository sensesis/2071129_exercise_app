package com.cookandroid.week10_11;

    public class MuscleItem {
    private String name;
    private String imageUrl;

    private String exerciseName;

    public MuscleItem(String name, String imageUrl) {
    this.name = name;
    this.imageUrl = imageUrl;
    }

    public String getName() {
    return name;
    }

    public String getExerciseName() {
            return exerciseName;
    }

    public String getImageUrl() {
    return imageUrl;
    }
    }