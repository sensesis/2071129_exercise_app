package com.cookandroid.week10_11;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

        TextView number1 = convertView.findViewById(R.id.number1);
        TextView number2 = convertView.findViewById(R.id.number2);
        TextView number3 = convertView.findViewById(R.id.number3);
        TextView number4 = convertView.findViewById(R.id.number4);
        TextView number5 = convertView.findViewById(R.id.number5);

        TextView time1 = convertView.findViewById(R.id.time1);
        TextView time2 = convertView.findViewById(R.id.time2);
        TextView time3 = convertView.findViewById(R.id.time3);
        TextView time4 = convertView.findViewById(R.id.time4);
        TextView time5 = convertView.findViewById(R.id.time5);

        TextView partText = convertView.findViewById(R.id.partText);
        TextView dateText = convertView.findViewById(R.id.dateText);
        TextView weightAvgText = convertView.findViewById(R.id.weightAvgText);
        TextView numberAvgText = convertView.findViewById(R.id.numberAvgText);
        TextView timeAvgText = convertView.findViewById(R.id.timeAvgText);

        ExerciseUi exercise = exerciseList.get(position);

        List<Integer> weights = exercise.getWeights();
        weight1.setText(String.valueOf(weights.get(0)));
        weight2.setText(String.valueOf(weights.get(1)));
        weight3.setText(String.valueOf(weights.get(2)));
        weight4.setText(String.valueOf(weights.get(3)));
        weight5.setText(String.valueOf(weights.get(4)));

        List<Integer> numbers = exercise.getNumbers();
        number1.setText(String.valueOf(numbers.get(0)));
        number2.setText(String.valueOf(numbers.get(1)));
        number3.setText(String.valueOf(numbers.get(2)));
        number4.setText(String.valueOf(numbers.get(3)));
        number5.setText(String.valueOf(numbers.get(4)));

        List<Float> times = exercise.getTimes();
        time1.setText(String.valueOf(times.get(0)));
        time2.setText(String.valueOf(times.get(1)));
        time3.setText(String.valueOf(times.get(2)));
        time4.setText(String.valueOf(times.get(3)));
        time5.setText(String.valueOf(times.get(4)));

        partText.setText(exercise.getExerciseName());
        dateText.setText(exercise.getExerciseDate());
        weightAvgText.setText("평균무게: " + exercise.getAvgWeight());
        numberAvgText.setText("평균횟수: " + exercise.getAvgNumber());
        timeAvgText.setText("평균시간: " + exercise.getAvgTime());

        Log.d("ExerciseListAdapter", "Set view for exercise: " + exercise.getExerciseName());

        return convertView;
    }
}