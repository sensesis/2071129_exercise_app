package com.cookandroid.week10_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ExerciseListAdapter extends BaseAdapter {
    private Context context;
    private List<ExerciseUi> exerciseList;

    public ExerciseListAdapter(Context context, List<ExerciseUi> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public int getCount() {
        return exerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        return exerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exerciseui, parent, false);
        }

        TextView weight1 = convertView.findViewById(R.id.weight1);
        TextView weight2 = convertView.findViewById(R.id.weight2);
        TextView weight3 = convertView.findViewById(R.id.weight3);
        TextView weight4 = convertView.findViewById(R.id.weight4);
        TextView weight5 = convertView.findViewById(R.id.weight5);
        TextView partText = convertView.findViewById(R.id.partText);
        TextView dateText = convertView.findViewById(R.id.dateText);
        TextView weightAvgText = convertView.findViewById(R.id.weightAvgText);
        TextView timeAvgText = convertView.findViewById(R.id.timeAvgText);

        ExerciseUi exercise = exerciseList.get(position);

        List<Integer> weights = exercise.getWeights();
        weight1.setText(String.valueOf(weights.get(0)));
        weight2.setText(String.valueOf(weights.get(1)));
        weight3.setText(String.valueOf(weights.get(2)));
        weight4.setText(String.valueOf(weights.get(3)));
        weight5.setText(String.valueOf(weights.get(4)));

        partText.setText(exercise.getExerciseName());
        dateText.setText(exercise.getExerciseDate());
        weightAvgText.setText("평균무게: " + exercise.getWeightAvg());
        timeAvgText.setText("평균시간: " + exercise.getTimeAvg());

        return convertView;
    }
}