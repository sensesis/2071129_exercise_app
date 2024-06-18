package com.cookandroid.week10_11;

public class ExercisesItem {
    private String exerciseName;
    private String imageUrl;

    public ExercisesItem(String exerciseName, String imageUrl) {
        this.exerciseName = exerciseName;
        this.imageUrl = imageUrl;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}