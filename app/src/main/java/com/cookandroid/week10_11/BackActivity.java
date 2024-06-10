package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.back);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);

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
            Intent intent = new Intent(BackActivity.this, MemoActivity.class);
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
}