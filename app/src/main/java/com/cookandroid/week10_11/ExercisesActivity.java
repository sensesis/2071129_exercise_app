package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExercisesActivity extends AppCompatActivity {

    private static final String TAG = "ExercisesActivity";
    private RecyclerView recyclerView;
    private ExercisesAdapter exercisesAdapter;
    private List<ExercisesItem> exerciseItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        exerciseItemList = new ArrayList<>();
        exercisesAdapter = new ExercisesAdapter(this, exerciseItemList);
        recyclerView.setAdapter(exercisesAdapter);

        Intent intent = getIntent();
        String subMuscleGroup = intent.getStringExtra("subMuscleGroup");

        Log.d(TAG, "Received subMuscleGroup: " + subMuscleGroup);

        if (subMuscleGroup != null) {
            fetchExercisesData(subMuscleGroup);
        } else {
            Log.e(TAG, "No subMuscleGroup received");
        }
    }

    private void fetchExercisesData(String subMuscleGroup) {
        OkHttpClient client = new OkHttpClient();

        String encodedSubMuscleGroup;
        try {
            encodedSubMuscleGroup = URLEncoder.encode(subMuscleGroup, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String url = "https://sensesis.mycafe24.com/getExerciseData.php?subMuscleGroup=" + encodedSubMuscleGroup;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ExercisesActivity.this, "Network Error", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    Log.d(TAG, "Response received: " + responseData); // 로그 출력
                    runOnUiThread(() -> processJsonData(responseData));
                } else {
                    Log.e(TAG, "Request not successful: " + response.message());
                    runOnUiThread(() -> Toast.makeText(ExercisesActivity.this, "Server Error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void processJsonData(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            exerciseItemList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String exerciseName = jsonObject.getString("name");
                String imageUrl = jsonObject.getString("imageUrl");

                ExercisesItem exerciseItem = new ExercisesItem(exerciseName, imageUrl);
                exerciseItemList.add(exerciseItem);
            }
            exercisesAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ExercisesActivity.this, "Data Parsing Error", Toast.LENGTH_SHORT).show();
        }
    }
}