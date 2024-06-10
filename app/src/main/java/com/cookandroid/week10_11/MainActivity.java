package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;



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
            Intent intent = new Intent(MainActivity.this, MemoActivity.class);
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
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(false);
        String[] items = getResources().getStringArray(R.array.main_array);



        // 이미지 뷰들을 배열에 할당합니다
        ImageView[] imageViews = new ImageView[6];
        int[] imageViewIds = {
                R.id.iv1, R.id.iv2, R.id.iv3,
                R.id.iv4, R.id.iv5, R.id.iv6,
        };

        for (int i = 0; i < imageViews.length; i++) {

            imageViews[i] = findViewById(imageViewIds[i]);
            int finalI = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        Class<?> selectedClass = Class.forName("com.cookandroid.week10_11." + items[finalI]);
                        // 해당 클래스로 Intent를 생성합니다.
                        Intent intent = new Intent(MainActivity.this, selectedClass);
                        // 액티비티를 시작합니다.
                        startActivity(intent);
                    }catch (ClassNotFoundException e) {
                        e.printStackTrace(); // 클래스 못 찾으면 예외 처리
                    }
                }
            });
        }
    }
}