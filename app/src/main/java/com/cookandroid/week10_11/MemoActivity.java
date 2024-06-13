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

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo);

        noticeListView = findViewById(R.id.noticeListView);
        noticeList = new ArrayList<>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);

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
        fetchNotices();
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

    private void fetchNotices() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sensesis.mycafe24.com/NoticeList.php")
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
                                JSONObject jsonObject = new JSONObject(responseData);
                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                int count = 0;
                                String noticeContent, noticeName, noticeDate;

                                while (count < jsonArray.length()) {
                                    JSONObject object = jsonArray.getJSONObject(count);
                                    noticeContent = object.getString("noticeContent");
                                    noticeName = object.getString("noticeName");
                                    noticeDate = object.getString("noticeDate");
                                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                                    noticeList.add(notice);
                                    count++;
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