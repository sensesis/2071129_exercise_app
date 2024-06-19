package com.cookandroid.week10_11;

import java.util.List;

public class ExerciseUi {
    private String exerciseName;
    private String exerciseDate;
    private String edit;
    private List<Integer> weights;
    private List<Integer> numbers;
    private List<Float> times;
    private double avgTime;
    private double avgWeight;
    private double avgNumber;
    private int rating;

    public ExerciseUi(String exerciseName, String exerciseDate, String edit, List<Integer> weights, List<Integer> numbers, List<Float> times, double avgTime, double avgWeight, double avgNumber, int rating) {
        this.exerciseName = exerciseName;
        this.exerciseDate = exerciseDate;
        this.edit = edit;
        this.weights = weights;
        this.numbers = numbers;
        this.times = times;
        this.avgTime = avgTime;
        this.avgWeight = avgWeight;
        this.avgNumber = avgNumber;
        this.rating = rating;
    }

    // Getters and setters for all fields
    public String getExerciseName() {
        return exerciseName;
    }
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(String exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public List<Integer> getWeights() {
        return weights;
    }

    public void setWeights(List<Integer> weights) {
        this.weights = weights;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Float> getTimes() {
        return times;
    }

    public void setTimes(List<Float> times) {
        this.times = times;
    }

    public double getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(double avgTime) {
        this.avgTime = avgTime;
    }

    public double getAvgWeight() {
        return avgWeight;
    }


    public void setAvgWeight(double avgWeight) {
        this.avgWeight = avgWeight;
    }


    public double getAvgNumber() {
        return avgNumber;
    }

    public void setAvgNumber(double avgNumber) {
        this.avgNumber = avgNumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}