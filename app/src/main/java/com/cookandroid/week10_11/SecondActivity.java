package com.cookandroid.week10_11;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SecondActivity extends AppCompatActivity {

    private EditText etNote;
    private TextView tvTimer;
    private Button btnStartTimer, btnStopTimer, btnNextButton, btnSave, btnLoad; /*backButton*/
    private TimePicker timePicker;
    private RatingBar ratingBar;
    private long startTimeInMillis;
    private CountDownTimer countDownTimer;

    private Toolbar toolbar2;
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
        if (itemId == R.id.logout) {
            // 로그아웃 아이템을 선택한 경우
            // 로그아웃 처리를 수행하거나 로그아웃 액티비티로 이동하는 코드를 추가합니다.
            return true;
        } else if (itemId == R.id.account) {
            // 계정 정보 아이템을 선택한 경우
            Intent intent = new Intent(SecondActivity.this, MemoActivity.class);
            startActivity(intent); // MemoActivity로 이동
            return true;
        } else if (itemId == android.R.id.home) {
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
        setContentView(R.layout.second);

        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
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



        /*backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/

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

        /*int[] editTextIds = {
            R.id.editText1, R.id.editText2, R.id.editText3,
            R.id.editText4, R.id.editText5
    };

    EditText[] editTexts = new EditText[5];*/

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isFirstClick)
                    finish();

                else {
                    Toast.makeText(getApplicationContext(), "해당 부위의 자극정도를 매기고 저장버튼을 한번 더 눌러주세요", Toast.LENGTH_SHORT).show();
                    ratingBar.setVisibility(View.VISIBLE);
                    isFirstClick = false;
                }
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNote();
            }
        });

        /*backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
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

}