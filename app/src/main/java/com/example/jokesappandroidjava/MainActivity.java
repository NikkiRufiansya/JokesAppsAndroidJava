package com.example.jokesappandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.developer.kalert.KAlertDialog;
import com.example.jokesappandroidjava.adapter.AdapterJokes;
import com.example.jokesappandroidjava.api.JokesService;
import com.example.jokesappandroidjava.helper.UrlApi;
import com.example.jokesappandroidjava.model.JokesModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;

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
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (isConnected(MainActivity.this)){
            getDataJokes();
        }else{
            new AestheticDialog.Builder(MainActivity.this, DialogStyle.CONNECTIFY, DialogType.ERROR)
                    .setTitle("Opss...")
                    .setMessage("No Available Connection!")
                    .show();
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    private void init() {
        listJokeRV = findViewById(R.id.listJokeRV);
        shimmerFrameLayout = findViewById(R.id.shimmerLoading);
    }

    private void getDataJokes() {
        KAlertDialog pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
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
                        shimmerFrameLayout.startShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        listJokeRV.setVisibility(View.VISIBLE);
                        pDialog.dismiss();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                } else {
                    new AestheticDialog.Builder(MainActivity.this, DialogStyle.TOASTER, DialogType.ERROR)
                            .setTitle("Opss...")
                            .setMessage("Something wrong reload again !")
                            .show();
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmerAnimation();
        super.onResume();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }
}