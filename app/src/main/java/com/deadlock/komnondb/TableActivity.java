package com.deadlock.komnondb;

import android.graphics.Color;
import android.graphics.Typeface;
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

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid())).child("Результаты");

        table = findViewById(R.id.table);

        getResult();

    }

    private void getResult() {
        for (int i = 0; i <= 11; i++) {
            final int finalI = i;
            reference.child(monthNames[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Float hwResFromDb = dataSnapshot.child("Горячая вода").getValue(Float.class);
                    Float cwResFromDb = dataSnapshot.child("Холодная вода").getValue(Float.class);
                    Float wofResFromDb = dataSnapshot.child("Водоотвод").getValue(Float.class);
                    Float elResFromDb = dataSnapshot.child("Электричество").getValue(Float.class);
                    Float allResFromDb = dataSnapshot.child("Всего").getValue(Float.class);

                    TextView textMonth = new TextView(getApplicationContext());
                    if (month.equals(monthNames[finalI])) {
                        textMonth.setTextColor(Color.parseColor("#6495ED"));
                        textMonth.setGravity(Gravity.CENTER_HORIZONTAL);
                        textMonth.setText(monthNames[finalI]);
                        textMonth.setBackgroundResource(R.drawable.shape_rec);
                        textMonth.setTextSize(16);
                    }else {
                        textMonth.setGravity(Gravity.START);
                        textMonth.setText(monthNames[finalI]);
                        textMonth.setTextColor(Color.parseColor("#FFF58302"));
                        textMonth.setBackgroundResource(R.drawable.shape_rec);
                        textMonth.setTextSize(16);
                    }

                    TextView textHW = new TextView(getApplicationContext());
                    textHW.setGravity(Gravity.END);
                    textHW.setText(hwResFromDb != null ? hwResFromDb.toString() : null);
                    textHW.setBackgroundResource(R.drawable.shape_rec_4dp);
                    textHW.setTextColor(Color.parseColor("#007BA7"));
                    textHW.setTextSize(16);

                    TextView textCW = new TextView(getApplicationContext());
                    textCW.setGravity(Gravity.END);
                    textCW.setText(cwResFromDb != null ? cwResFromDb.toString() : null);
                    textCW.setBackgroundResource(R.drawable.shape_rec_4dp);
                    textCW.setTextColor(Color.parseColor("#007BA7"));
                    textCW.setTextSize(16);

                    TextView textWof = new TextView(getApplicationContext());
                    textWof.setGravity(Gravity.END);
                    textWof.setText(wofResFromDb != null ? wofResFromDb.toString() : null);
                    textWof.setBackgroundResource(R.drawable.shape_rec_4dp);
                    textWof.setTextColor(Color.parseColor("#007BA7"));
                    textWof.setTextSize(16);

                    TextView textEl = new TextView(getApplicationContext());
                    textEl.setGravity(Gravity.END);
                    textEl.setText(elResFromDb != null ? elResFromDb.toString() : null);
                    textEl.setBackgroundResource(R.drawable.shape_rec_4dp);
                    textEl.setTextColor(Color.parseColor("#007BA7"));
                    textEl.setTextSize(16);

                    TextView textAll = new TextView(getApplicationContext());
                    textAll.setGravity(Gravity.CENTER_HORIZONTAL);
                    textAll.setText(allResFromDb != null ? allResFromDb.toString() : null);
                    textAll.setBackgroundResource(R.drawable.shape_rec_4dp);
                    textAll.setTextColor(Color.parseColor("#6495ED"));
                    textAll.setTextSize(16);
                    textAll.setTypeface(null, Typeface.BOLD);

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
}
