package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class MemoActivity extends AppCompatActivity {

    private ListView exerciseListView;
    private ExerciseListAdapter adapter;
    private List<Exercise> exerciseList;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo);

        exerciseListView = findViewById(R.id.exerciseListView);
        exerciseList = new ArrayList<>();
        adapter = new ExerciseListAdapter(getApplicationContext(), exerciseList);
        exerciseListView.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        spinner = findViewById(R.id.spinner);

        String[] items = getResources().getStringArray(R.array.my_array);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(myAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 새로운 네트워크 요청 메서드 호출
        fetchExercises();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            return true;
        } else if (itemId == R.id.account) {
            Intent intent = new Intent(MemoActivity.this, MemoActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchExercises() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://example.com/ExerciseList.php") // 여기에 실제 API URL을 넣어야 합니다.
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

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONArray jsonArray = new JSONArray(responseData);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String exerciseName = jsonObject.getString("exerciseName");
                                    String exerciseDate = jsonObject.getString("exerciseDate");
                                    List<Integer> weights = new ArrayList<>();
                                    weights.add(jsonObject.getInt("weight1"));
                                    weights.add(jsonObject.getInt("weight2"));
                                    weights.add(jsonObject.getInt("weight3"));
                                    weights.add(jsonObject.getInt("weight4"));
                                    weights.add(jsonObject.getInt("weight5"));
                                    double weightAvg = jsonObject.getDouble("weightAvg");
                                    double timeAvg = jsonObject.getDouble("timeAvg");

                                    Exercise exercise = new Exercise(exerciseName, exerciseDate, weights, weightAvg, timeAvg);
                                    exerciseList.add(exercise);
                                }
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}