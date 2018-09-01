package com.deadlock.komnondb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpperActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPokaz, btnSet;

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
                intent = new Intent(this, KoefActivity.class);
                startActivity(intent);
                break;
        }
    }
}
