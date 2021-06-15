package com.example.jokesappandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailJokes extends AppCompatActivity {
    private int id;
    private String type, setup, punchline;
    private TextView typeTV, setupTV, punclineTV;
    private ImageView btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jokes);
        getData();
        init();
    }

    private void init() {
        typeTV = findViewById(R.id.type);
        setupTV = findViewById(R.id.setup);
        punclineTV = findViewById(R.id.punchline);
        btnClose = findViewById(R.id.btnClose);
        typeTV.setText(type);
        setupTV.setText(setup);
        punclineTV.setText(punchline);
        btnClose.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }


    private void getData() {
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getStringExtra("type");
        setup = getIntent().getStringExtra("setup");
        punchline = getIntent().getStringExtra("punchline");
    }

}