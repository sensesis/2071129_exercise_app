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
        ExercisesItem exercisesItem = exercisesList.get(position);
        holder.textView.setText(exercisesItem.getExerciseName());

        if (exercisesItem.getImageUrl() == null || exercisesItem.getImageUrl().isEmpty()) {
            holder.imageView.setImageResource(R.drawable.default_image);
        } else {
            Picasso.get()
                    .load(exercisesItem.getImageUrl())
                    .placeholder(R.drawable.default_image)
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExerciseListActivity.class);
            intent.putExtra("exerciseName", exercisesItem.getExerciseName());
            context.startActivity(intent);
        });
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