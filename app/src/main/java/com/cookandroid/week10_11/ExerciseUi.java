package com.cookandroid.week10_11;

import java.util.List;

public class ExerciseUi {

    private String exerciseName; // 해당 부위이름
    private String exerciseDate; // 해당 운동날짜
    private List<Integer> weights; // 운동무게들
    private double weightAvg; // 평균무게
    private double timeAvg; // 평균시간

    public ExerciseUi(String exerciseName, String exerciseDate, List<Integer> weights, double weightAvg, double timeAvg) {
        this.exerciseName = exerciseName;
        this.exerciseDate = exerciseDate;
        this.weights = weights;
        this.weightAvg = weightAvg;
        this.timeAvg = timeAvg;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseDate() {
        return exerciseDate;
    }

    public List<Integer> getWeights() {
        return weights;
    }

    public double getWeightAvg() {
        return weightAvg;
    }

    public double getTimeAvg() {
        return timeAvg;
    }
}