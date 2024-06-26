package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.ListView;
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
    private List<ExerciseUi> exerciseList;

    private Toolbar toolbar;
    private ActionBar actionBar;

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

        // 서버에서 데이터 가져오기
        fetchExercises();
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

    private void fetchExercises() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sensesis.cafe24.com/getExerciseData.php") // 여기에 실제 API URL을 넣어야 합니다.
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
                                    String edit = jsonObject.getString("edit");
                                    List<Integer> weights = new ArrayList<>();
                                    weights.add(jsonObject.getInt("weight1"));
                                    weights.add(jsonObject.getInt("weight2"));
                                    weights.add(jsonObject.getInt("weight3"));
                                    weights.add(jsonObject.getInt("weight4"));
                                    weights.add(jsonObject.getInt("weight5"));
                                    List<Integer> numbers = new ArrayList<>();
                                    numbers.add(jsonObject.getInt("number1"));
                                    numbers.add(jsonObject.getInt("number2"));
                                    numbers.add(jsonObject.getInt("number3"));
                                    numbers.add(jsonObject.getInt("number4"));
                                    numbers.add(jsonObject.getInt("number5"));
                                    List<Float> times = new ArrayList<>();
                                    times.add((float)jsonObject.getDouble("time1"));
                                    times.add((float)jsonObject.getDouble("time2"));
                                    times.add((float)jsonObject.getDouble("time3"));
                                    times.add((float)jsonObject.getDouble("time4"));
                                    times.add((float)jsonObject.getDouble("time5"));
                                    double avgTime = jsonObject.getDouble("avg_time");
                                    double avgWeight = jsonObject.getDouble("avg_weight");
                                    double avgNumber = jsonObject.getDouble("avg_number");
                                    int rating = jsonObject.getInt("rating");

                                    ExerciseUi exercise = new ExerciseUi(exerciseName, exerciseDate, edit, weights, numbers, times, avgTime, avgWeight, avgNumber, rating);
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