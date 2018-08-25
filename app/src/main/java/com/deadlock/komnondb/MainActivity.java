package com.deadlock.komnondb;

import android.annotation.SuppressLint;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static double HW_KEF = 180.57;
    final static double CW_KEF = 35.40;
    final static double T1_KEF = 6.46;
    final static double T2_KEF = 1.92;
    final static double T3_KEF = 5.38;
    final static double WOF_KEF = 25.12;
    final static int PHONE_KEF = 205;
    final static int HOUSE_KEF = 35000;

    final String SAVED_HW = "saved_hw";
    final String SAVED_CW = "saved_cw";
    final String SAVED_T1 = "saved_t1";
    final String SAVED_T2 = "saved_t2";
    final String SAVED_T3 = "saved_t3";

    final String result = "Результат";
    final String exit = "Сохранить и выйти";

    SharedPreferences sPref;

    TextView textResultHW, textResultCW, textResultElectr, textResultWof,
            textResultAll, textPersonal, litrHW, litrCW, litrWof, litrEl,
            sumHW, sumCW, sumWof, sumEl;

    Button btnResult;

    EditText hwPr, cwPr, t1Pr, t2Pr, t3Pr, hw, cw, t1, t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(this);
        btnResult.setText(result);

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

        load();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        if (btnResult.getText().equals(exit)) {
            save();
            finish();
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
            double resultHW = resHW * HW_KEF;
            BigDecimal hw = new BigDecimal(resultHW);
            hw = hw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultHW.setText("Горячая вода:");
            litrHW.setText(resHW + "л");
            sumHW.setText(hw + " руб");

            int cw1 = Integer.parseInt(cwPr.getText().toString());
            int cw2 = Integer.parseInt(cw.getText().toString());
            int resCW = cw2 - cw1;
            double resultCW = resCW * CW_KEF;
            BigDecimal cw = new BigDecimal(resultCW);
            cw = cw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultCW.setText("Холодная вода:");
            litrCW.setText(resCW + "л");
            sumCW.setText(cw + " руб");

            int resWof = (hw2 - hw1) + (cw2 - cw1);
            double resultWof = resWof * WOF_KEF;
            BigDecimal wof = new BigDecimal(resultWof);
            wof = wof.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultWof.setText("Водоотвод:");
            litrWof.setText(resWof + "л");
            sumWof.setText(wof + " руб");

            int elPr1 = Integer.parseInt(t1Pr.getText().toString());
            int el1 = Integer.parseInt(t1.getText().toString());
            int resT1 = el1 - elPr1;
            double resultT1 = resT1 * T1_KEF;

            int elPr2 = Integer.parseInt(t2Pr.getText().toString());
            int el2 = Integer.parseInt(t2.getText().toString());
            int resT2 = el2 - elPr2;
            double resultT2 = resT2 * T2_KEF;

            int elPr3 = Integer.parseInt(t3Pr.getText().toString());
            int el3 = Integer.parseInt(t3.getText().toString());
            int resT3 = el3 - elPr3;
            double resultT3 = resT3 * T3_KEF;

            double resultElectr = resultT1 + resultT2 + resultT3;
            BigDecimal el = new BigDecimal(resultElectr);
            el = el.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultElectr.setText("Электричество:");
            litrEl.setText(resT1 + "|" + resT2 + "|" + resT3);
            sumEl.setText(el + " руб");

            double resultAll = resultHW + resultCW + resultElectr + resultWof + PHONE_KEF;
            BigDecimal all = new BigDecimal(resultAll);
            all = all.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultAll.setText(all + " руб");

            double peronal = (resultAll + HOUSE_KEF) / 3;
            BigDecimal pers = new BigDecimal(peronal);
            pers = pers.setScale(2, BigDecimal.ROUND_HALF_UP);
            textPersonal.setText(pers + " руб");

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
}
