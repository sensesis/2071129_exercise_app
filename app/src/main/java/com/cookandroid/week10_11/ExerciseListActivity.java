package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExerciseListActivity extends AppCompatActivity {

    private ListView exerciseListView;
    private ExerciseListAdapter adapter;
    private List<ExerciseUi> exerciseList;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exerciselist);

        exerciseListView = findViewById(R.id.exerciseListView);
        exerciseList = new ArrayList<>();
        adapter = new ExerciseListAdapter(getApplicationContext(), exerciseList);
        exerciseListView.setAdapter(adapter);

        Button createbtn = findViewById(R.id.createbtn);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Intent에서 exerciseName 값 가져오기
        String exerciseName = getIntent().getStringExtra("exerciseName");

        if (exerciseName != null) {
            // 서버에서 데이터 가져오기
            fetchExercisesFromServer(exerciseName);
        }


        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseListActivity.this, MainActivity.class);
                intent.putExtra("exerciseName", exerciseName);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchExercisesFromServer(String exerciseName) {
        OkHttpClient client = new OkHttpClient();

        // URL에 쿼리 파라미터로 exerciseName을 추가합니다.
        try {
            String encodedExerciseName = URLEncoder.encode(exerciseName, "UTF-8");
            String url = "https://sensesis.mycafe24.com/getExerciseList.php?exerciseName=" + encodedExerciseName;
            Log.d("ExerciseListActivity", "Request URL: " + url); // URL 로그 추가

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.e("ExerciseListActivity", "Server request failed: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String responseData = response.body().string();
                        Log.d("ExerciseListActivity", "Response Data: " + responseData);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(responseData);
                                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                                    Log.d("ExerciseListActivity", "JSON Array Length: " + jsonArray.length());

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject exerciseObject = jsonArray.getJSONObject(i);
                                        String exerciseName = exerciseObject.getString("exerciseName");
                                        String exerciseDate = exerciseObject.getString("date");
                                        String edit = exerciseObject.getString("edit");

                                        List<Integer> weights = new ArrayList<>();
                                        weights.add(exerciseObject.getInt("weight1"));
                                        weights.add(exerciseObject.getInt("weight2"));
                                        weights.add(exerciseObject.getInt("weight3"));
                                        weights.add(exerciseObject.getInt("weight4"));
                                        weights.add(exerciseObject.getInt("weight5"));

                                        List<Integer> numbers = new ArrayList<>();
                                        numbers.add(exerciseObject.getInt("number1"));
                                        numbers.add(exerciseObject.getInt("number2"));
                                        numbers.add(exerciseObject.getInt("number3"));
                                        numbers.add(exerciseObject.getInt("number4"));
                                        numbers.add(exerciseObject.getInt("number5"));

                                        List<Float> times = new ArrayList<>();
                                        times.add(convertTimeStringToFloat(exerciseObject.getString("time1")));
                                        times.add(convertTimeStringToFloat(exerciseObject.getString("time2")));
                                        times.add(convertTimeStringToFloat(exerciseObject.getString("time3")));
                                        times.add(convertTimeStringToFloat(exerciseObject.getString("time4")));
                                        times.add(convertTimeStringToFloat(exerciseObject.getString("time5")));

                                        double avgTime = convertTimeStringToFloat(exerciseObject.getString("avg_time"));
                                        double avgWeight = exerciseObject.getDouble("avg_weight");
                                        double avgNumber = exerciseObject.getDouble("avg_number");
                                        int rating = exerciseObject.getInt("rating");

                                        ExerciseUi exercise = new ExerciseUi(exerciseName, exerciseDate, edit, weights, numbers, times, avgTime, avgWeight, avgNumber, rating);
                                        exerciseList.add(exercise); // 리스트에 추가
                                        Log.d("ExerciseListActivity", "Added exercise: " + exerciseName);
                                    }
                                    Log.d("ExerciseListActivity", "Exercise List Size: " + exerciseList.size());
                                    adapter.notifyDataSetChanged(); // 어댑터 갱신
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("ExerciseListActivity", "JSON parsing error: " + e.getMessage());
                                }
                            }
                        });
                    } else {
                        Log.e("ExerciseListActivity", "Server responded with status code: " + response.code());
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("ExerciseListActivity", "URL encoding error: " + e.getMessage());
        }
    }

    // 문자열 시간 형식을 float으로 변환하는 메소드
    private float convertTimeStringToFloat(String timeString) {
        try {
            if (timeString.contains(":")) {
                String[] parts = timeString.split(":");
                int minutes = Integer.parseInt(parts[0]);
                int seconds = Integer.parseInt(parts[1]);
                return minutes * 60 + seconds;
            } else {
                return Float.parseFloat(timeString);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
