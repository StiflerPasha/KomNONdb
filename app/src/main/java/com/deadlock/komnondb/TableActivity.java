package com.deadlock.komnondb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;

public class TableActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    Button btnRefresh;
    TableLayout table;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
            "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
    Calendar calendar = Calendar.getInstance();
    String month = monthNames[calendar.get(Calendar.MONTH)];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table2);

        Calendar calendar = Calendar.getInstance();
        String month = monthNames[calendar.get(Calendar.MONTH)];

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid())).child("Результаты");

        btnRefresh = findViewById(R.id.btnRefresh);
        table = findViewById(R.id.table);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getResult();
            }
        });
    }

    private void getResult() {
        reference.child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long hwResFromDb = dataSnapshot.child("Горячая вода").getValue(Long.class);
                Long cwResFromDb = dataSnapshot.child("Холодная вода").getValue(Long.class);
                Long wofResFromDb = dataSnapshot.child("Водоотвод").getValue(Long.class);
                Long elResFromDb = dataSnapshot.child("Электричество").getValue(Long.class);
                Long allResFromDb = dataSnapshot.child("Всего").getValue(Long.class);

                TextView textMonth = new TextView(getApplicationContext());
                textMonth.setGravity(Gravity.CENTER_HORIZONTAL);
                textMonth.setText(month);

                TextView textHW = new TextView(getApplicationContext());
                textHW.setGravity(Gravity.CENTER_HORIZONTAL);
                textHW.setText(hwResFromDb != null ? hwResFromDb.toString() : null);

                TextView textCW = new TextView(getApplicationContext());
                textCW.setGravity(Gravity.CENTER_HORIZONTAL);
                textCW.setText(cwResFromDb != null ? cwResFromDb.toString() : null);

                TextView textWof = new TextView(getApplicationContext());
                textWof.setGravity(Gravity.CENTER_HORIZONTAL);
                textWof.setText(wofResFromDb != null ? wofResFromDb.toString() : null);

                TextView textEl = new TextView(getApplicationContext());
                textEl.setGravity(Gravity.CENTER_HORIZONTAL);
                textEl.setText(elResFromDb != null ? elResFromDb.toString() : null);

                TextView textAll = new TextView(getApplicationContext());
                textAll.setGravity(Gravity.CENTER_HORIZONTAL);
                textAll.setText(allResFromDb != null ? allResFromDb.toString() : null);

                TableRow row = new TableRow(getApplicationContext());
                row.addView(textMonth);
                row.addView(textHW);
                row.addView(textCW);
                row.addView(textWof);
                row.addView(textEl);
                row.addView(textAll);
                table.addView(row);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
