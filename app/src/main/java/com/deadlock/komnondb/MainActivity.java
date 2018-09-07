package com.deadlock.komnondb;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    float HW_KEF,
            CW_KEF,
            T1_KEF,
            T2_KEF,
            T3_KEF,
            WOF_KEF,
            PHONE_KEF,
            HOUSE_KEF;
    int PERSONS_KEF;

    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
            "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

    final String SAVED_HW = "saved_hw";
    final String SAVED_CW = "saved_cw";
    final String SAVED_T1 = "saved_t1";
    final String SAVED_T2 = "saved_t2";
    final String SAVED_T3 = "saved_t3";

    final String result = "Результат";
    final String exit = "Сохранить и выйти";

    SharedPreferences sPref, sp;

    TextView textResultHW, textResultCW, textResultElectr, textResultWof,
            textResultAll, textPersonal, litrHW, litrCW, litrWof, litrEl,
            sumHW, sumCW, sumWof, sumEl, prevMonth, presMonth;

    Button btnResult;
    Button btnAdd;

    EditText hwPr, cwPr, t1Pr, t2Pr, t3Pr, hw, cw, t1, t2, t3;

    FirebaseDatabase database;
    DatabaseReference reference;

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

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Показания");

        /*btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = presMonth.getText().toString();
                database = FirebaseDatabase.getInstance();
                reference = database.getReference();
                reference.child("Monthes").child(date).setValue(date);
                addCounter();
            }
        });*/

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

        Calendar calendar = Calendar.getInstance();
        presMonth.setText(monthNames[calendar.get(Calendar.MONTH)]);
        prevMonth.setText(monthNames[calendar.get(Calendar.MONTH) - 1]);

        load();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        if (btnResult.getText().equals(exit)) {
            save();
            finish();
            //finishAffinity();
        }

        if (TextUtils.isEmpty(hwPr.getText().toString()) ||
                TextUtils.isEmpty(hw.getText().toString()) ||
                TextUtils.isEmpty(cwPr.getText().toString()) ||
                TextUtils.isEmpty(cw.getText().toString()) ||
                TextUtils.isEmpty(t1Pr.getText().toString()) ||
                TextUtils.isEmpty(t1.getText().toString()) ||
                TextUtils.isEmpty(t2Pr.getText().toString()) ||
                TextUtils.isEmpty(t2.getText().toString()) ||
                TextUtils.isEmpty(t3Pr.getText().toString()) ||
                TextUtils.isEmpty(t3.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_LONG).show();

        } else {

            int hw1 = Integer.parseInt(hwPr.getText().toString());
            int hw2 = Integer.parseInt(hw.getText().toString());
            int resHW = hw2 - hw1;
            float resultHW = resHW * HW_KEF;
            BigDecimal hw = new BigDecimal(resultHW);
            hw = hw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultHW.setText("Горячая вода:");
            litrHW.setText(resHW + "л");
            sumHW.setText(hw + " руб");

            int cw1 = Integer.parseInt(cwPr.getText().toString());
            int cw2 = Integer.parseInt(cw.getText().toString());
            int resCW = cw2 - cw1;
            float resultCW = resCW * CW_KEF;
            BigDecimal cw = new BigDecimal(resultCW);
            cw = cw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultCW.setText("Холодная вода:");
            litrCW.setText(resCW + "л");
            sumCW.setText(cw + " руб");

            int resWof = (hw2 - hw1) + (cw2 - cw1);
            float resultWof = resWof * WOF_KEF;
            BigDecimal wof = new BigDecimal(resultWof);
            wof = wof.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultWof.setText("Водоотвод:");
            litrWof.setText(resWof + "л");
            sumWof.setText(wof + " руб");

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

            float resultAll = resultHW + resultCW + resultElectr + resultWof + PHONE_KEF;
            BigDecimal all = new BigDecimal(resultAll);
            all = all.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultAll.setText(all + " руб");

            float peronal = (resultAll + HOUSE_KEF) / PERSONS_KEF;
            BigDecimal pers = new BigDecimal(peronal);
            pers = pers.setScale(2, BigDecimal.ROUND_HALF_UP);
            textPersonal.setText(pers + " руб");

            addCounter();

            btnResult.setText(exit);
        }
    }

    void save() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_HW, hw.getText().toString());
        ed.putString(SAVED_CW, cw.getText().toString());
        ed.putString(SAVED_T1, t1.getText().toString());
        ed.putString(SAVED_T2, t2.getText().toString());
        ed.putString(SAVED_T3, t3.getText().toString());
        ed.commit();
    }

    void savePrev() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_HW, hwPr.getText().toString());
        ed.putString(SAVED_CW, cwPr.getText().toString());
        ed.putString(SAVED_T1, t1Pr.getText().toString());
        ed.putString(SAVED_T2, t2Pr.getText().toString());
        ed.putString(SAVED_T3, t3Pr.getText().toString());
        ed.commit();
    }

    void load() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedHW = sPref.getString(SAVED_HW, "");
        String savedCW = sPref.getString(SAVED_CW, "");
        String savedT1 = sPref.getString(SAVED_T1, "");
        String savedT2 = sPref.getString(SAVED_T2, "");
        String savedT3 = sPref.getString(SAVED_T3, "");
        hwPr.setText(savedHW);
        cwPr.setText(savedCW);
        t1Pr.setText(savedT1);
        t2Pr.setText(savedT2);
        t3Pr.setText(savedT3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TextUtils.isEmpty(hw.getText().toString()) ||
                TextUtils.isEmpty(cw.getText().toString()) ||
                TextUtils.isEmpty(t1.getText().toString()) ||
                TextUtils.isEmpty(t2.getText().toString()) ||
                TextUtils.isEmpty(t3.getText().toString())) {
            savePrev();
        } else {
            save();
        }
    }

    private void addCounter() {
        String date = presMonth.getText().toString();
        String id = reference.child(date).getKey();

        Counters newCounter = new Counters(
                Integer.parseInt(hw.getText().toString()),
                Integer.parseInt(cw.getText().toString()),
                Integer.parseInt(t1.getText().toString()),
                Integer.parseInt(t2.getText().toString()),
                Integer.parseInt(t3.getText().toString()));

        Map<String, Object> counterValues = newCounter.toMap();

        Map<String, Object> counter = new HashMap<>();
        counter.put(id, counterValues);

        reference.updateChildren(counter);
    }
}
