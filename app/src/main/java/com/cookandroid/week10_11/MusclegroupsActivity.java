package com.cookandroid.week10_11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MusclegroupsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private ImageView imageBack, imageChest, imageLegs, imageShoulders, imageArms, imageAbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musclegroups);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(false);


        imageBack = findViewById(R.id.image_back);
        imageChest = findViewById(R.id.image_chest);
        imageLegs = findViewById(R.id.image_legs);
        imageShoulders = findViewById(R.id.image_shoulders);
        imageArms = findViewById(R.id.image_arms);
        imageAbs = findViewById(R.id.image_abs);


        setClickListener(imageBack, "Back");
        setClickListener(imageChest, "Chest");
        setClickListener(imageLegs, "Legs");
        setClickListener(imageShoulders, "Shoulders");
        setClickListener(imageArms, "Arms");
        setClickListener(imageAbs, "Abs");
        fetchExerciseData();
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

    private void setClickListener(ImageView imageView, final String muscleGroup) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusclegroupsActivity.this, SubMusclesActivity.class);
                intent.putExtra("muscleGroup", muscleGroup); // 선택한 근육 그룹을 인텐트에 추가합니다.
                startActivity(intent);
            }
        });
    }

    private void fetchExerciseData() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://example.com/getMuscleGroup.php") // 실제 API URL로 변경하세요
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
                                    String muscleGroup = jsonObject.getString("muscleGroup");
                                    String imageUrl = jsonObject.getString("imageUrl");

                                    switch (muscleGroup) {
                                        case "Back":
                                            loadImage(imageBack, imageUrl);
                                            break;
                                        case "Chest":
                                            loadImage(imageChest, imageUrl);
                                            break;
                                        case "Legs":
                                            loadImage(imageLegs, imageUrl);
                                            break;
                                        case "Shoulders":
                                            loadImage(imageShoulders, imageUrl);
                                            break;
                                        case "Arms":
                                            loadImage(imageArms, imageUrl);
                                            break;
                                        case "Abs":
                                            loadImage(imageAbs, imageUrl);
                                            break;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private void loadImage(ImageView imageView, String url) {
        Picasso.get().load(url).into(imageView);
    }
}