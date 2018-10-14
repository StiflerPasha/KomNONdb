package com.deadlock.komnondb;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    float HW_KEF, CW_KEF, T1_KEF, T2_KEF, T3_KEF, WOF_KEF, PHONE_KEF, HOUSE_KEF;
    float hwRes, cwRes, wofRes, elRes, allRes;
    int PERSONS_KEF;

    int hwToDb, cwToDb, wofToDb;


    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
            "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

    final String result = "Результат";

    SharedPreferences sp;

    TextView textResultHW, textResultCW, textResultElectr, textResultWof,
            textResultAll, textPersonal, litrHW, litrCW, litrWof, litrEl,
            sumHW, sumCW, sumWof, sumEl, prevMonth, presMonth;

    Button btnResult;
    ImageButton btnPrev, btnNext;

    EditText hwPr, cwPr, t1Pr, t2Pr, t3Pr, hw, cw, t1, t2, t3;

    FirebaseDatabase database;
    DatabaseReference reference;

    Calendar calendar = Calendar.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    int month = calendar.get(Calendar.MONTH);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        textResultHW = findViewById(R.id.textResultHW);
        textResultCW = findViewById(R.id.textResultCW);
        textResultWof = findViewById(R.id.textResultWof);
        textResultElectr = findViewById(R.id.textResultElectr);
        textResultAll = findViewById(R.id.textResultAll);
        textPersonal = findViewById(R.id.textPersonal);
        litrHW = findViewById(R.id.litrHW);
        litrCW = findViewById(R.id.litrCW);
        litrWof = findViewById(R.id.litrWof);
        litrEl = findViewById(R.id.litrEl);
        sumHW = findViewById(R.id.sumHW);
        sumCW = findViewById(R.id.sumCW);
        sumWof = findViewById(R.id.sumWof);
        sumEl = findViewById(R.id.sumEl);

        prevMonth = findViewById(R.id.prevMonth);
        presMonth = findViewById(R.id.presMonth);

        btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(this);
        btnResult.setText(result);

        btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        hwPr = findViewById(R.id.etHWpr);
        cwPr = findViewById(R.id.etCWpr);
        t1Pr = findViewById(R.id.etT1pr);
        t2Pr = findViewById(R.id.etT2pr);
        t3Pr = findViewById(R.id.etT3pr);
        hw = findViewById(R.id.etHW);
        cw = findViewById(R.id.etCW);
        t1 = findViewById(R.id.etT1);
        t2 = findViewById(R.id.etT2);
        t3 = findViewById(R.id.etT3);

        HW_KEF = Float.parseFloat(sp.getString("hwSet", "188.53"));
        CW_KEF = Float.parseFloat(sp.getString("cwSet", "38.06"));
        T1_KEF = Float.parseFloat(sp.getString("t1Set", "6.46"));
        T2_KEF = Float.parseFloat(sp.getString("t2Set", "1.92"));
        T3_KEF = Float.parseFloat(sp.getString("t3Set", "5.38"));
        WOF_KEF = Float.parseFloat(sp.getString("wofSet", "27.01"));
        PHONE_KEF = Float.parseFloat(sp.getString("phoneSet", "205.0"));
        HOUSE_KEF = Float.parseFloat(sp.getString("houseSet", "35000.0"));
        PERSONS_KEF = Integer.parseInt(sp.getString("personsSet", "3"));

        presMonth.setText(monthNames[month]);
        prevMonth.setText(monthNames[month - 1]);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid()));

        setCounters();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnResult):
                if (etEmpty()) {
                    Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_LONG).show();

                } else if (month == calendar.get(Calendar.MONTH)) {
                    btnPrev.setVisibility(View.INVISIBLE);
                    btnNext.setVisibility(View.INVISIBLE);
                    calculate();
                    savePresCounter();
                    savePrevCounter();
                    saveResults();
                } else {
                    calculate();
                }
                break;

            case (R.id.btnPrev):
                clearText();
                month--;
                prevMonth.setText(monthNames[month - 1]);
                presMonth.setText(monthNames[month]);
                setCounters();
                break;
                
            case (R.id.btnNext):
                clearText();
                month++;
                prevMonth.setText(monthNames[month - 1]);
                presMonth.setText(monthNames[month]);
                setCounters();
                break;
        }
    }

    private void clearText() {
        textResultHW.setText("");
        textResultCW.setText("");
        textResultWof.setText("");
        textResultElectr.setText("");
        textResultAll.setText("");
        textPersonal.setText("");
        litrHW.setText("");
        litrCW.setText("");
        litrWof.setText("");
        litrEl.setText("");
        sumHW.setText("");
        sumCW.setText("");
        sumWof.setText("");
        sumEl.setText("");
    }

    private boolean etEmpty() {
        return TextUtils.isEmpty(hwPr.getText().toString()) ||
                TextUtils.isEmpty(hw.getText().toString()) ||
                TextUtils.isEmpty(cwPr.getText().toString()) ||
                TextUtils.isEmpty(cw.getText().toString()) ||
                TextUtils.isEmpty(t1Pr.getText().toString()) ||
                TextUtils.isEmpty(t1.getText().toString()) ||
                TextUtils.isEmpty(t2Pr.getText().toString()) ||
                TextUtils.isEmpty(t2.getText().toString()) ||
                TextUtils.isEmpty(t3Pr.getText().toString()) ||
                TextUtils.isEmpty(t3.getText().toString());
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        int hw1 = Integer.parseInt(hwPr.getText().toString());
        int hw2 = Integer.parseInt(hw.getText().toString());
        int resHW = hw2 - hw1;
        float resultHW = resHW * HW_KEF;
        BigDecimal hw = new BigDecimal(resultHW);
        hw = hw.setScale(2, BigDecimal.ROUND_HALF_UP);
        textResultHW.setText("Горячая вода:");
        litrHW.setText(resHW + "л");
        sumHW.setText(hw + " руб");
        hwRes = (float) Math.round(resultHW * 100) / 100;
        hwToDb = resHW;

        int cw1 = Integer.parseInt(cwPr.getText().toString());
        int cw2 = Integer.parseInt(cw.getText().toString());
        int resCW = cw2 - cw1;
        float resultCW = resCW * CW_KEF;
        BigDecimal cw = new BigDecimal(resultCW);
        cw = cw.setScale(2, BigDecimal.ROUND_HALF_UP);
        textResultCW.setText("Холодная вода:");
        litrCW.setText(resCW + "л");
        sumCW.setText(cw + " руб");
        cwRes = (float) Math.round(resultCW * 100) / 100;
        cwToDb = resCW;

        int resWof = (hw2 - hw1) + (cw2 - cw1);
        float resultWof = resWof * WOF_KEF;
        BigDecimal wof = new BigDecimal(resultWof);
        wof = wof.setScale(2, BigDecimal.ROUND_HALF_UP);
        textResultWof.setText("Водоотвод:");
        litrWof.setText(resWof + "л");
        sumWof.setText(wof + " руб");
        wofRes = (float) Math.round(resultWof * 100) / 100;
        wofToDb = resWof;

        int elPr1 = Integer.parseInt(t1Pr.getText().toString());
        int el1 = Integer.parseInt(t1.getText().toString());
        int resT1 = el1 - elPr1;
        float resultT1 = resT1 * T1_KEF;

        int elPr2 = Integer.parseInt(t2Pr.getText().toString());
        int el2 = Integer.parseInt(t2.getText().toString());
        int resT2 = el2 - elPr2;
        float resultT2 = resT2 * T2_KEF;

        int elPr3 = Integer.parseInt(t3Pr.getText().toString());
        int el3 = Integer.parseInt(t3.getText().toString());
        int resT3 = el3 - elPr3;
        float resultT3 = resT3 * T3_KEF;

        float resultElectr = resultT1 + resultT2 + resultT3;
        BigDecimal el = new BigDecimal(resultElectr);
        el = el.setScale(2, BigDecimal.ROUND_HALF_UP);
        textResultElectr.setText("Электричество:");
        litrEl.setText(resT1 + "|" + resT2 + "|" + resT3);
        sumEl.setText(el + " руб");
        elRes = (float) Math.round(resultElectr * 100) / 100;

        float resultAll = resultHW + resultCW + resultElectr + resultWof + PHONE_KEF;
        BigDecimal all = new BigDecimal(resultAll);
        all = all.setScale(2, BigDecimal.ROUND_HALF_UP);
        textResultAll.setText(all + " руб");
        allRes = (float) Math.round(resultAll * 100) / 100;

        float peronal = (resultAll + HOUSE_KEF) / PERSONS_KEF;
        BigDecimal pers = new BigDecimal(peronal);
        pers = pers.setScale(2, BigDecimal.ROUND_HALF_UP);
        textPersonal.setText(pers + " руб");
    }

    private void savePresCounter() {
        String date = presMonth.getText().toString();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid())).
                child(date);
        String id = "Показания";

        Counters newCounter = new Counters(
                Integer.parseInt(hw.getText().toString()),
                Integer.parseInt(cw.getText().toString()),
                Integer.parseInt(t1.getText().toString()),
                Integer.parseInt(t2.getText().toString()),
                Integer.parseInt(t3.getText().toString()));

        Map<String, Object> counterValues = newCounter.counterToMap();

        Map<String, Object> counter = new HashMap<>();
        counter.put(id, counterValues);

        reference.updateChildren(counter);
    }

    private void savePrevCounter() {
        String date = prevMonth.getText().toString();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid())).
                child(date);
        String id = "Показания";

        Counters newCounter = new Counters(
                Integer.parseInt(hwPr.getText().toString()),
                Integer.parseInt(cwPr.getText().toString()),
                Integer.parseInt(t1Pr.getText().toString()),
                Integer.parseInt(t2Pr.getText().toString()),
                Integer.parseInt(t3Pr.getText().toString()));

        Map<String, Object> counterValues = newCounter.counterToMap();

        Map<String, Object> counter = new HashMap<>();
        counter.put(id, counterValues);

        reference.updateChildren(counter);
    }

    private void saveResults() {
        String date = presMonth.getText().toString();
        reference = database.getReference("users").child(Objects.requireNonNull(mAuth.getUid())).
                child(date);
        String id = "Результаты";

        Results newResult = new Results(hwRes, hwToDb, cwRes, cwToDb, wofRes, wofToDb, elRes, allRes);

        Map<String, Object> resultValues = newResult.ResultsToMap();

        Map<String, Object> result = new HashMap<>();
        result.put(id, resultValues);

        reference.updateChildren(result);
    }

    private void setCounters() {
        String prevDate = prevMonth.getText().toString();
        reference.child(prevDate).child("Показания").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer hwPrFromDb = dataSnapshot.child("Горячая вода").getValue(Integer.class);
                Integer cwPrFromDb = dataSnapshot.child("Холодная вода").getValue(Integer.class);
                Integer t1PrFromDb = dataSnapshot.child("T1").getValue(Integer.class);
                Integer t2PrFromDb = dataSnapshot.child("T2").getValue(Integer.class);
                Integer t3PrFromDb = dataSnapshot.child("T3").getValue(Integer.class);
                hwPr.setText(hwPrFromDb != null ? hwPrFromDb.toString() : null);
                cwPr.setText(cwPrFromDb != null ? cwPrFromDb.toString() : null);
                t1Pr.setText(t1PrFromDb != null ? t1PrFromDb.toString() : null);
                t2Pr.setText(t2PrFromDb != null ? t2PrFromDb.toString() : null);
                t3Pr.setText(t3PrFromDb != null ? t3PrFromDb.toString() : null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String presDate = presMonth.getText().toString();
        reference.child(presDate).child("Показания").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer hwFromDb = dataSnapshot.child("Горячая вода").getValue(Integer.class);
                Integer cwFromDb = dataSnapshot.child("Холодная вода").getValue(Integer.class);
                Integer t1FromDb = dataSnapshot.child("T1").getValue(Integer.class);
                Integer t2FromDb = dataSnapshot.child("T2").getValue(Integer.class);
                Integer t3FromDb = dataSnapshot.child("T3").getValue(Integer.class);
                hw.setText(hwFromDb != null ? hwFromDb.toString() : null);
                cw.setText(cwFromDb != null ? cwFromDb.toString() : null);
                t1.setText(t1FromDb != null ? t1FromDb.toString() : null);
                t2.setText(t2FromDb != null ? t2FromDb.toString() : null);
                t3.setText(t3FromDb != null ? t3FromDb.toString() : null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
