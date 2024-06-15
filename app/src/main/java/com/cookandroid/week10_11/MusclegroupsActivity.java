package com.cookandroid.week10_11;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private ImageView imageBack, imageChest, imageLegs, imageShoulders, imageArms, imageAbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musclegroups);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageBack = findViewById(R.id.image_back);
        imageChest = findViewById(R.id.image_chest);
        imageLegs = findViewById(R.id.image_legs);
        imageShoulders = findViewById(R.id.image_shoulders);
        imageArms = findViewById(R.id.image_arms);
        imageAbs = findViewById(R.id.image_abs);

        fetchExerciseData();
    }

    private void fetchExerciseData() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://example.com/getExerciseData.php") // 실제 API URL로 변경하세요
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