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

    private EditText etNote, editText1, editText2, editText3, editText4, editText5;
    private EditText TextNumber1, TextNumber2, TextNumber3, TextNumber4, TextNumber5;
    private TextView TextView1, TextView2, TextView3, TextView4, TextView5;
    private RatingBar ratingBar;
    private TextView tvTimer;
    private Button btnStartTimer, btnStopTimer, btnNextButton, btnSave, btnLoad; /*backButton*/
    private TimePicker timePicker;
    private long startTimeInMillis;
    private CountDownTimer countDownTimer;

    private Toolbar toolbar;
    private ActionBar actionBar;

    boolean isFirstClick = true;

    MediaPlayer mp;
    private int pagenum = 1, count = 1; // 페이지 번호, 카운트 번호
    private int checknum=0;
    String editTextValue, editTextValue2; // 설정한 무게 값 // 버튼 next의 값 변수


    int[] linearLayoutIds = {
            R.id.page1, R.id.page2, R.id.page3,
            R.id.page4, R.id.page5
    };

    int[] checkBoxIds = {
            R.id.checkBox1, R.id.checkBox2, R.id.checkBox3,
            R.id.checkBox4, R.id.checkBox5
    };

    int[] editTextIds = {
            R.id.editText1, R.id.editText2, R.id.editText3,
            R.id.editText4, R.id.editText5
    };

    int[] TextViewIds = {
            R.id.TextView1, R.id.TextView2, R.id.TextView3,
            R.id.TextView4, R.id.TextView5
    };

    LinearLayout[] linearLayouts = new LinearLayout[5];
    CheckBox[] checkBoxes = new CheckBox[5];
    EditText[] editTexts = new EditText[5];
    TextView[] TextViews = new TextView[5];


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            // 뒤로 가기 버튼을 선택한 경우
            // 현재 액티비티를 종료하는 코드를 추가합니다.
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNote = findViewById(R.id.etNote);
        ratingBar = findViewById(R.id.ratingBar);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);

        TextNumber1 = findViewById(R.id.TextNumber1);
        TextNumber2 = findViewById(R.id.TextNumber2);
        TextNumber3 = findViewById(R.id.TextNumber3);
        TextNumber4 = findViewById(R.id.TextNumber4);
        TextNumber5 = findViewById(R.id.TextNumber5);

        TextView1 = findViewById(R.id.TextView1);
        TextView2 = findViewById(R.id.TextView2);
        TextView3 = findViewById(R.id.TextView3);
        TextView4 = findViewById(R.id.TextView4);
        TextView5 = findViewById(R.id.TextView5);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);

        etNote = findViewById(R.id.etNote);
        tvTimer = findViewById(R.id.tvTimer);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        btnStopTimer = findViewById(R.id.btnStopTimer);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        ratingBar = findViewById(R.id.ratingBar);
        /*backButton = findViewById(R.id.backButton);*/
        timePicker = findViewById(R.id.timePicker);
        btnNextButton = findViewById(R.id.btnNextButton);
        timePicker.setIs24HourView(true);
        mp = MediaPlayer.create(this, R.raw.alarm);
        Date currentDate = new Date(); // 현재 날짜와 시간을 가져옵니다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // SimpleDateFormat을 사용하여 날짜 및 시간 형식을 지정합니다
        // TimePicker 초기값 설정
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            timePicker.setHour(1); // 타임피커 시간을 1시로 설정 (API 23 이상)
            timePicker.setMinute(0); // 타임피커 분을 0분으로 설정 (API 23 이상)
        } else {
            timePicker.setCurrentHour(1); // 타임피커 시간을 1시로 설정 (API 23 미만)
            timePicker.setCurrentMinute(0); // 타임피커 분을 0분으로 설정 (API 23 미만)
        }

        for (int i = 0; i < linearLayoutIds.length; i++) {
            linearLayouts[i] = findViewById(linearLayoutIds[i]);
        }

        for (int i = 0; i < checkBoxIds.length; i++) {
            checkBoxes[i] = findViewById(checkBoxIds[i]);
        }

        for (int i = 0; i < editTextIds.length; i++) {
            editTexts[i] = findViewById(editTextIds[i]);
        }

        for (int i = 0; i < TextViewIds.length; i++) {
            TextViews[i] = findViewById(TextViewIds[i]);
        }

        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePicker.getVisibility() == View.GONE) {
                    timePicker.setVisibility(View.VISIBLE);

                } else {
                    timePicker.setVisibility(View.GONE);
                }
            }

        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // 선택된 시간을 형식화하여 문자열로 변환
                String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                // tvTimer의 텍스트를 선택된 시간으로 업데이트
                tvTimer.setText(selectedTime);
            }
        });

        btnStartTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editTextValue = editTexts[count-1].getText().toString(); // EditText 값을 String으로 가져옴

                if (count != pagenum)
                    Toast.makeText(getApplicationContext(), "운동 박스를 생성해주세요", Toast.LENGTH_SHORT).show();

                else if (editTextValue.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "운동 무게를 설정하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    editTexts[count-1].setEnabled(false);
                    int hour;
                    int minute;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        hour = timePicker.getHour(); // 타임피커에서 시간을 가져옴 (API 23 이상)
                        minute = timePicker.getMinute(); // 타임피커에서 분을 가져옴 (API 23 이상)
                    } else {
                        hour = timePicker.getCurrentHour(); // 타임피커에서 시간을 가져옴 (API 23 미만)
                        minute = timePicker.getCurrentMinute(); // 타임피커에서 분을 가져옴 (API 23 미만)
                    }

                    int a = hour * 60; // 시간을 분 단위로 변환
                    int b = minute; // 분 단위로 변환된 시간

                    long startTimeInMillis = (a + b) * 1000; // 분:초를 밀리초로 변환하여 계산

                    startTimer(startTimeInMillis); // 타이머 시작

                    count++;
                }

            }
        });

        btnStopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                count--;
            }
        });

        btnNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextValue2 = editTexts[pagenum-1].getText().toString();

                if (editTextValue2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "운동 무게를 설정하세요.", Toast.LENGTH_SHORT).show();
                }

                else if (pagenum > linearLayouts.length) {

                    Toast.makeText(getApplicationContext(), "운동 일지를 추가해주세요.", Toast.LENGTH_SHORT).show();
                }

                else if (pagenum < linearLayouts.length) {
                    linearLayouts[pagenum].setVisibility(View.VISIBLE);
                    editTexts[pagenum].setText(editTextValue);
                    pagenum++; // 다음 페이지로 이동
                }

                else {
                    Toast.makeText(getApplicationContext(), "최대 개수는 5개 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isFirstClick) {
                    saveDataToServer();
                    finish();
                }

                else {
                    Toast.makeText(getApplicationContext(), "해당 부위의 자극정도를 매기고 저장버튼을 한번 더 눌러주세요", Toast.LENGTH_SHORT).show();
                    ratingBar.setVisibility(View.VISIBLE);
                    isFirstClick = false;
                }
            }
        });
    }



    private void startTimer(final long startTime) {
        final long startTimeInMillis = startTime; // 시작 시간을 밀리초로 유지하기 위한 변수

        countDownTimer = new CountDownTimer(startTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                updateTimerText(millisUntilFinished);
            }

            @Override
            public void onFinish() {

                String timeString = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(startTimeInMillis),
                        TimeUnit.MILLISECONDS.toSeconds(startTimeInMillis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTimeInMillis)));

                tvTimer.setText(timeString);

                if (checknum < checkBoxes.length) {
                    checkBoxes[checknum].setChecked(true);
                    TextViews[checknum].setText(timeString); // timeString 값을 사용
                    checknum++; // 다음 페이지로 이동
                } else {
                    Toast.makeText(getApplicationContext(), "더 이상 체크할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(), "자자, 어서 운동합시다!!", Toast.LENGTH_SHORT).show();
                mp.start();
            }
        }.start();
    }


    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // 타이머 취소
            countDownTimer = null;
        }
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // 타이머 일시정지
        }
    }

    private void resumeTimer() {
        startTimer(startTimeInMillis); // 타이머 다시 시작
    }

    private void updateTimerText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void saveNote() {
        // 메모 저장 코드 작성
    }

    private void loadNote() {
        // 메모 불러오기 코드 작성
    }

    private void saveDataToServer() {
        String exerciseName = getIntent().getStringExtra("exerciseName");
        OkHttpClient client = new OkHttpClient();

        String note = etNote.getText().toString();
        float rating = ratingBar.getRating();

        int weight1 = Integer.parseInt(editText1.getText().toString());
        int weight2 = Integer.parseInt(editText2.getText().toString());
        int weight3 = Integer.parseInt(editText3.getText().toString());
        int weight4 = Integer.parseInt(editText4.getText().toString());
        int weight5 = Integer.parseInt(editText5.getText().toString());

        int number1 = Integer.parseInt(TextNumber1.getText().toString());
        int number2 = Integer.parseInt(TextNumber2.getText().toString());
        int number3 = Integer.parseInt(TextNumber3.getText().toString());
        int number4 = Integer.parseInt(TextNumber4.getText().toString());
        int number5 = Integer.parseInt(TextNumber5.getText().toString());

        String time1 = TextView1.getText().toString();
        String time2 = TextView2.getText().toString();
        String time3 = TextView3.getText().toString();
        String time4 = TextView4.getText().toString();
        String time5 = TextView5.getText().toString();

        float ftime1 = convertTimeStringToFloat(time1);
        float ftime2 = convertTimeStringToFloat(time2);
        float ftime3 = convertTimeStringToFloat(time3);
        float ftime4 = convertTimeStringToFloat(time4);
        float ftime5 = convertTimeStringToFloat(time5);

        String avg_time = avgTime(ftime1, ftime2, ftime3, ftime4, ftime5);
        float avg_weight = avgNum(weight1, weight2, weight3, weight4, weight5);
        float avg_Num = avgNum(number1, number2, number3, number4, number5);

        Log.d("saveDataToServer", "Preparing request body...");
        RequestBody formBody = new FormBody.Builder()
                .add("exerciseName", exerciseName)
                .add("note", note)
                .add("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
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
                .add("time1", String.valueOf(ftime1))
                .add("time2", String.valueOf(ftime2))
                .add("time3", String.valueOf(ftime3))
                .add("time4", String.valueOf(ftime4))
                .add("time5", String.valueOf(ftime5))
                .add("avg_weight", String.valueOf(avg_weight))
                .add("avg_num", String.valueOf(avg_Num))
                .add("avg_time", String.valueOf(convertTimeStringToFloat(avg_time)))
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Server request failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    Log.d("saveDataToServer", "Response from server: " + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Data saved successfully! Response: " + responseData, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Log.e("saveDataToServer", "Server returned an error: " + response.message());
                    final String responseData = response.body().string();
                    Log.e("saveDataToServer", "Error response: " + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Server error: " + response.message() + " - " + responseData, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }


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

    // 평균 시간을 계산하는 메서드
    private String avgTime(float... times) {
        if (times == null || times.length == 0) return "0:0";
        float sum = 0;
        for (float time : times) {
            sum += time;
        }
        float avg = sum / times.length;
        int minutes = (int) (avg / 60);
        int seconds = (int) (avg % 60);
        return String.format("%d:%02d", minutes, seconds);
    }

    // 평균 값을 계산하는 메서드 (가중치 및 횟수)
    private float avgNum(int... nums) {
        if (nums == null || nums.length == 0) return 0;
        float sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum / nums.length;
    }

}