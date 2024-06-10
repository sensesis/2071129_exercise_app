package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MemoActivity extends AppCompatActivity {

    private Toolbar toolbar3;
    private ActionBar actionBar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo);

        toolbar3 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);
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

    // 추가 코드 부분
    private void saveMainLayout(View mainLayout) {
        String mainLayoutXml = ViewToXmlConverter.convertViewToXml(mainLayout);
        // 여기서 mainLayoutXml을 저장하는 로직을 구현하세요.
    }

    private void loadMainLayout(String mainLayoutXml, ViewGroup memoLayout) {
        View mainLayout = XmlToViewConverter.convertXmlToView(mainLayoutXml, memoLayout);
        if (mainLayout != null) {
            memoLayout.removeAllViews(); // 기존에 추가된 뷰를 모두 제거하고
            memoLayout.addView(mainLayout);

            memoLayout.invalidate();
        }
    }
}