package com.deadlock.komnondb;

import android.annotation.SuppressLint;
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

    TextView textResultHW, textResultCW, textResultElectr, textResultWof,
            textResultAll, textPersonal;
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

        btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(this);

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
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

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
            double resultHW = (hw2 - hw1) * HW_KEF;
            BigDecimal hw = new BigDecimal(resultHW);
            hw = hw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultHW.setText("Горячая вода    -->    " + (hw2 - hw1) + "л     --> Сумма: " + hw + "руб");

            int cw1 = Integer.parseInt(cwPr.getText().toString());
            int cw2 = Integer.parseInt(cw.getText().toString());
            double resultCW = (cw2 - cw1) * CW_KEF;
            BigDecimal cw = new BigDecimal(resultCW);
            cw = cw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultCW.setText("Холодная вода -->    " + (cw2 - cw1) + "л     --> Сумма: " + cw + "руб");

            int resWof = (hw2 - hw1) + (cw2 - cw1);
            double resultWof = resWof * WOF_KEF;
            BigDecimal wof = new BigDecimal(resultWof);
            wof = wof.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultWof.setText("Водоотвод         -->    " + resWof + "л     --> Сумма: " + wof + "руб");

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
            textResultElectr.setText("Электричество --> " + resT1 + "|" + resT2 + "|" + resT3 + " --> Сумма: " + el + "руб");

            double resultAll = resultHW + resultCW + resultElectr + resultWof + PHONE_KEF;
            BigDecimal all = new BigDecimal(resultAll);
            all = all.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultAll.setText("Итого: " + all);

            double peronal = (resultAll + HOUSE_KEF) / 3;
            BigDecimal pers = new BigDecimal(peronal);
            pers = pers.setScale(2, BigDecimal.ROUND_HALF_UP);
            textPersonal.setText(pers + "руб");
        }

    }
}
