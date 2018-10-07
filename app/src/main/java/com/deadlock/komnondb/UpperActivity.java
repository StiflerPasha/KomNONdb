package com.deadlock.komnondb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UpperActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnTable, btnSet;
    Button btnPokaz, btnExit;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upper);

        btnPokaz = findViewById(R.id.btnPokaz);
        btnPokaz.setOnClickListener(this);
        btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(this);
        btnTable = findViewById(R.id.btnTable);
        btnTable.setOnClickListener(this);
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid()));
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
            case R.id.btnTable:
                intent = new Intent(this, TableActivity.class);
                startActivity(intent);
                break;
            case R.id.btnExit:
                mAuth.signOut();
                intent = new Intent(this, EmailPassword.class);
                startActivity(intent);
                break;
        }
    }
}
