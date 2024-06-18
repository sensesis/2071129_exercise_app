package com.cookandroid.week10_11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private List<ExercisesItem> exercisesList;
    private Context context;

    public ExercisesAdapter(Context context, List<ExercisesItem> exercisesList) {
        this.context = context;
        this.exercisesList = exercisesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercisesitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 1. 현재 위치에 해당하는 MuscleItem 객체를 가져옵니다.
        ExercisesItem exercisesItem = exercisesList.get(position);

        // 2. TextView에 MuscleItem의 이름을 설정합니다.
        holder.textView.setText(exercisesItem.getExerciseName());

        // 3. 이미지 URL이 비어 있거나 null인지 확인합니다.
        if (exercisesItem.getImageUrl() == null || exercisesItem.getImageUrl().isEmpty()) {

            // 이미지 URL이 비어 있거나 null이면 기본 이미지를 설정합니다.
            holder.imageView.setImageResource(R.drawable.default_image);
        }

        else {
            // 이미지 URL이 유효하면 Picasso를 사용하여 이미지를 로드합니다.
            Picasso.get()
                    .load(exercisesItem.getImageUrl())  // 이미지 URL을 로드합니다.
                    .placeholder(R.drawable.default_image)  // 로딩되는 동안 기본 이미지를 보여줍니다.
                    .into(holder.imageView);  // 로드된 이미지를 imageView에 설정합니다.
        }

    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.exerciseImage);
            textView = itemView.findViewById(R.id.exerciseName);
        }
    }
}