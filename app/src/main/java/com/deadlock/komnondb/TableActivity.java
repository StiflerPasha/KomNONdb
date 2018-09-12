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

    Button btnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table2);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid()));

        table = findViewById(R.id.table);

        /*btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResult();
            }
        });*/

        getResult();

    }

    private void getResult() {
        for (int i = 0; i <= 11; i++) {
            final int finalI = i;
            reference.child(monthNames[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Long hwFromDb = dataSnapshot.child("Показания").child("Горячая вода").getValue(Long.class);
                    Long hwLitrFromDb = dataSnapshot.child("Результаты").child("Горячая вода*").getValue(Long.class);
                    Float hwResFromDb = dataSnapshot.child("Результаты").child("Горячая вода").getValue(Float.class);

                    Long cwFromDb = dataSnapshot.child("Показания").child("Холодная вода").getValue(Long.class);
                    Long cwLitrFromDb = dataSnapshot.child("Результаты").child("Холодная вода*").getValue(Long.class);
                    Float cwResFromDb = dataSnapshot.child("Результаты").child("Холодная вода").getValue(Float.class);

                    Long wofLitrFromDb = dataSnapshot.child("Результаты").child("Водоотвод*").getValue(Long.class);
                    Float wofResFromDb = dataSnapshot.child("Результаты").child("Водоотвод").getValue(Float.class);

                    Float elResFromDb = dataSnapshot.child("Результаты").child("Электричество").getValue(Float.class);
                    Float allResFromDb = dataSnapshot.child("Результаты").child("Всего").getValue(Float.class);


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
                    textHW.setGravity(Gravity.CENTER_HORIZONTAL);
                    textHW.setText(hwFromDb != null ? hwFromDb.toString() : null);
                    textHW.setBackgroundResource(R.drawable.shape_rec_hw);
                    textHW.setTextColor(Color.parseColor("#ffffee"));
                    textHW.setTextSize(16);

                    TextView textHWlitr = new TextView(getApplicationContext());
                    textHWlitr.setGravity(Gravity.CENTER_HORIZONTAL);
                    textHWlitr.setText(hwLitrFromDb != null ? hwLitrFromDb.toString() : null);
                    textHWlitr.setBackgroundResource(R.drawable.shape_rec_hw);
                    textHWlitr.setTextColor(Color.parseColor("#ffffee"));
                    textHWlitr.setTextSize(16);

                    TextView textHWres = new TextView(getApplicationContext());
                    textHWres.setGravity(Gravity.CENTER_HORIZONTAL);
                    textHWres.setText(hwResFromDb != null ? hwResFromDb.toString() : null);
                    textHWres.setBackgroundResource(R.drawable.shape_rec_hw);
                    textHWres.setTextColor(Color.parseColor("#ffffee"));
                    textHWres.setTextSize(16);

                    TextView textCW = new TextView(getApplicationContext());
                    textCW.setGravity(Gravity.CENTER_HORIZONTAL);
                    textCW.setText(cwFromDb != null ? cwFromDb.toString() : null);
                    textCW.setBackgroundResource(R.drawable.shape_rec_cw);
                    textCW.setTextColor(Color.parseColor("#ffffee"));
                    textCW.setTextSize(16);

                    TextView textCWlitr = new TextView(getApplicationContext());
                    textCWlitr.setGravity(Gravity.CENTER_HORIZONTAL);
                    textCWlitr.setText(cwLitrFromDb != null ? cwLitrFromDb.toString() : null);
                    textCWlitr.setBackgroundResource(R.drawable.shape_rec_cw);
                    textCWlitr.setTextColor(Color.parseColor("#ffffee"));
                    textCWlitr.setTextSize(16);

                    TextView textCWres = new TextView(getApplicationContext());
                    textCWres.setGravity(Gravity.CENTER_HORIZONTAL);
                    textCWres.setText(cwResFromDb != null ? cwResFromDb.toString() : null);
                    textCWres.setBackgroundResource(R.drawable.shape_rec_cw);
                    textCWres.setTextColor(Color.parseColor("#ffffee"));
                    textCWres.setTextSize(16);

                    TextView textWoflitr = new TextView(getApplicationContext());
                    textWoflitr.setGravity(Gravity.CENTER_HORIZONTAL);
                    textWoflitr.setText(wofLitrFromDb != null ? wofLitrFromDb.toString() : null);
                    textWoflitr.setBackgroundResource(R.drawable.shape_rec_wof);
                    textWoflitr.setTextColor(Color.parseColor("#ffffee"));
                    textWoflitr.setTextSize(16);

                    TextView textWofres = new TextView(getApplicationContext());
                    textWofres.setGravity(Gravity.CENTER_HORIZONTAL);
                    textWofres.setText(wofResFromDb != null ? wofResFromDb.toString() : null);
                    textWofres.setBackgroundResource(R.drawable.shape_rec_wof);
                    textWofres.setTextColor(Color.parseColor("#ffffee"));
                    textWofres.setTextSize(16);

                    TextView textEl = new TextView(getApplicationContext());
                    textEl.setGravity(Gravity.CENTER_HORIZONTAL);
                    textEl.setText(elResFromDb != null ? elResFromDb.toString() : null);
                    textEl.setBackgroundResource(R.drawable.shape_rec_elec);
                    textEl.setTextColor(Color.parseColor("#ffffee"));
                    textEl.setTextSize(16);

                    TextView textAll = new TextView(getApplicationContext());
                    textAll.setGravity(Gravity.CENTER_HORIZONTAL);
                    textAll.setText(allResFromDb != null ? allResFromDb.toString() : null);
                    textAll.setBackgroundResource(R.drawable.shape_rec_all);
                    textAll.setTextColor(Color.parseColor("#6495ED"));
                    textAll.setTextSize(16);
                    textAll.setTypeface(null, Typeface.BOLD);

                    TableRow row = new TableRow(getApplicationContext());
                    row.addView(textMonth);
                    row.addView(textHW);
                    row.addView(textHWlitr);
                    row.addView(textHWres);
                    row.addView(textCW);
                    row.addView(textCWlitr);
                    row.addView(textCWres);
                    row.addView(textWoflitr);
                    row.addView(textWofres);
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
