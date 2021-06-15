package com.example.jokesappandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.jokesappandroidjava.adapter.AdapterJokes;
import com.example.jokesappandroidjava.api.JokesService;
import com.example.jokesappandroidjava.helper.UrlApi;
import com.example.jokesappandroidjava.model.JokesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listJokeRV;
    private List<JokesModel> jokesModels = new ArrayList<>();
    private AdapterJokes adapterJokes;
    private JokesService jokesService = UrlApi.getJokesService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getDataJokes();
    }

    private void init() {
        listJokeRV = findViewById(R.id.listJokeRV);
    }

    private void getDataJokes() {
        jokesService.getJokes().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String respon = response.body().string();
                        JSONArray jsonArray = new JSONArray(respon);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jokesObject = jsonArray.getJSONObject(i);
                            jokesModels.add(new JokesModel(
                                    jokesObject.getInt("id"),
                                    jokesObject.getString("type"),
                                    jokesObject.getString("setup"),
                                    jokesObject.getString("punchline")));
                        }
                        adapterJokes = new AdapterJokes(MainActivity.this, jokesModels);
                        listJokeRV.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        listJokeRV.setAdapter(adapterJokes);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Load Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}