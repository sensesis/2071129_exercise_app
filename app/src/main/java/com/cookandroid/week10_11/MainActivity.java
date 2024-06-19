package com.cookandroid.week10_11;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etNote;
    private EditText[] editTexts = new EditText[5];
    private TextView[] TextNumbers = new TextView[5];
    private LinearLayout[] linearLayouts = new LinearLayout[5];
    private Button btnNextButton, btnSave;
    private RatingBar ratingBar;
    private boolean timecheck = false; // 버튼 체크 활성화
    private int[] linearLayoutIds = {
            R.id.page1, R.id.page2, R.id.page3,
            R.id.page4, R.id.page5
    };
    private int[] editTextIds = {
            R.id.editText1, R.id.editText2, R.id.editText3,
            R.id.editText4, R.id.editText5
    };
    private int[] textNumberIds = {
            R.id.TextNumber1, R.id.TextNumber2, R.id.TextNumber3,
            R.id.TextNumber4, R.id.TextNumber5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < linearLayoutIds.length; i++) {
            linearLayouts[i] = findViewById(linearLayoutIds[i]);
        }
        for (int i = 0; i < editTextIds.length; i++) {
            editTexts[i] = findViewById(editTextIds[i]);
        }
        for (int i = 0; i < textNumberIds.length; i++) {
            TextNumbers[i] = findViewById(textNumberIds[i]);
        }

        etNote = findViewById(R.id.etNote);
        btnNextButton = findViewById(R.id.btnNextButton);
        btnSave = findViewById(R.id.btnSave);
        ratingBar = findViewById(R.id.ratingBar);

        btnNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재는 인덱스 관련된 코드가 없으므로 단순하게 처리
                Toast.makeText(getApplicationContext(), "Next button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToServer();
            }
        });
    }

    private void saveDataToServer() {
        OkHttpClient client = new OkHttpClient();

        String exerciseName = "바벨슈러그";
        String note = "Test Note";
        String date = "24-06-01";
        float rating = 5.0f;

        int weight1 = 10;
        int weight2 = 20;
        int weight3 = 30;
        int weight4 = 40;
        int weight5 = 50;

        int number1 = 5;
        int number2 = 10;
        int number3 = 15;
        int number4 = 20;
        int number5 = 25;

        String time1 = "00:30";
        String time2 = "01:00";
        String time3 = "01:30";
        String time4 = "02:00";
        String time5 = "02:30";

        float ftime1 = 30.0f;
        float ftime2 = 60.0f;
        float ftime3 = 90.0f;
        float ftime4 = 120.0f;
        float ftime5 = 150.0f;

        String avg_time = "01:30";
        float avg_weight = 30.0f;
        float avg_Num = 15.0f;

        Log.d("saveDataToServer", "Preparing request body...");
        RequestBody formBody = new FormBody.Builder()
                .add("exerciseName", exerciseName)
                .add("note", note)
                .add("date", date)
                .add("rating", String.valueOf(rating))
                .add("weight1", String.valueOf(weight1))
                .add("weight2", String.valueOf(weight2))
                .add("weight3", String.valueOf(weight3))
                .add("weight4", String.valueOf(weight4))
                .add("weight5", String.valueOf(weight5))
                .add("number1", String.valueOf(number1))
                .add("number2", String.valueOf(number2))
                .add("number3", String.valueOf(number3))
                .add("number4", String.valueOf(number4))
                .add("number5", String.valueOf(number5))
                .add("time1", time1)
                .add("time2", time2)
                .add("time3", time3)
                .add("time4", time4)
                .add("time5", time5)
                .add("avg_weight", String.valueOf(avg_weight))
                .add("avg_num", String.valueOf(avg_Num))
                .add("avg_time", avg_time)
                .build();

        Request request = new Request.Builder()
                .url("https://sensesis.mycafe24.com/getSaveExerciseData.php")
                .post(formBody)
                .build();

        Log.d("saveDataToServer", "Sending request to server...");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("saveDataToServer", "Request failed: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Server request failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    Log.d("saveDataToServer", "Response from server: " + responseData);
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Data saved successfully! Response: " + responseData, Toast.LENGTH_LONG).show();
                    });
                } else {
                    Log.e("saveDataToServer", "Server returned an error: " + response.message());
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Server error: " + response.message(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }
}