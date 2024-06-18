package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SubMusclesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubMusclesAdapter subMusclesAdapter;
    private List<MuscleItem> muscleItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submuscles);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        muscleItemList = new ArrayList<>();
        subMusclesAdapter = new SubMusclesAdapter(this, muscleItemList);
        recyclerView.setAdapter(subMusclesAdapter);

        String muscleGroup = getIntent().getStringExtra("muscleGroup");
        Log.d("SubMusclesActivity", "Received muscleGroup: " + muscleGroup);
        if (muscleGroup != null) {
            fetchExerciseData(muscleGroup);
        } else {
            Log.e("SubMusclesActivity", "No muscleGroup received");
        }
    }

    private void fetchExerciseData(String muscleGroup) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://sensesis.mycafe24.com/getSubMuscles.php?muscleGroup=" + muscleGroup)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    Log.d("SubMusclesActivity", "Response received: " + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processJsonData(responseData);
                        }
                    });
                } else {
                    Log.e("SubMusclesActivity", "Request not successful");
                }
            }
        });
    }

    private void processJsonData(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            muscleItemList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String exerciseName = jsonObject.getString("name");
                String imageUrl = jsonObject.getString("imageUrl");

                MuscleItem muscleItem = new MuscleItem(exerciseName, imageUrl);
                muscleItemList.add(muscleItem);
            }
            subMusclesAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}