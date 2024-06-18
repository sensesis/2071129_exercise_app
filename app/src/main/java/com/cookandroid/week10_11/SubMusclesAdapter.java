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

public class SubMusclesAdapter extends RecyclerView.Adapter<SubMusclesAdapter.ViewHolder> {

    private List<MuscleItem> muscleItemList;
    private Context context;

    public SubMusclesAdapter(Context context, List<MuscleItem> muscleItemList) {
        this.context = context;
        this.muscleItemList = muscleItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.submuscleitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MuscleItem muscleItem = muscleItemList.get(position);
        holder.textView.setText(muscleItem.getName());

        if (muscleItem.getImageUrl() == null || muscleItem.getImageUrl().isEmpty()) {
            holder.imageView.setImageResource(R.drawable.default_image);
        } else {
            Picasso.get()
                    .load(muscleItem.getImageUrl())
                    .placeholder(R.drawable.default_image)
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExercisesActivity.class);
            intent.putExtra("subMuscleGroup", muscleItem.getName()); // 확인
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return muscleItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}