package com.deadlock.komnondb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UpperActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnPokaz, btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upper);

        btnPokaz = findViewById(R.id.btnPokaz);
        btnPokaz.setOnClickListener(this);
        btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnPokaz:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSet:
                intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                break;
        }
    }
}
