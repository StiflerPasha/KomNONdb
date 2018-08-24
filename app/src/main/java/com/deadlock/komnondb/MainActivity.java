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
    final static double PHONE_KEF = 205.00;

    TextView textResultHW, textResultCW, textResultElectr, textResultWof, textResultAll;
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
            double hw1 = Double.parseDouble(hwPr.getText().toString());
            double hw2 = Double.parseDouble(hw.getText().toString());
            double resultHW = (hw2 - hw1) * HW_KEF;
            BigDecimal hw = new BigDecimal(resultHW);
            hw = hw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultHW.setText(hw + " / " + (hw2 - hw1));

            double cw1 = Double.parseDouble(cwPr.getText().toString());
            double cw2 = Double.parseDouble(cw.getText().toString());
            double resultCW = (cw2 - cw1) * CW_KEF;
            BigDecimal cw = new BigDecimal(resultCW);
            cw = cw.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultCW.setText(cw + " / " + (cw2 - cw1));

            double resWof = (hw2 - hw1) + (cw2 - cw1);
            double resultWof = resWof * WOF_KEF;
            BigDecimal wof = new BigDecimal(resultWof);
            wof = wof.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultWof.setText(wof + " / " + resWof);

            double elPr1 = Double.parseDouble(t1Pr.getText().toString());
            double el1 = Double.parseDouble(t1.getText().toString());
            double resT1 = el1 - elPr1;
            double resultT1 = resT1 * T1_KEF;

            double elPr2 = Double.parseDouble(t2Pr.getText().toString());
            double el2 = Double.parseDouble(t2.getText().toString());
            double resT2 = el2 - elPr2;
            double resultT2 = resT2 * T2_KEF;

            double elPr3 = Double.parseDouble(t3Pr.getText().toString());
            double el3 = Double.parseDouble(t3.getText().toString());
            double resT3 = el3 - elPr3;
            double resultT3 = resT3 * T3_KEF;

            double resultElectr = resultT1 + resultT2 + resultT3;
            textResultElectr.setText(resultElectr +
                    " /* " + resT1 + "/" + resT2 + "/" + resT3 + " */");

            double resultAll = resultHW + resultCW + resultElectr + resultWof + PHONE_KEF;
            BigDecimal all = new BigDecimal(resultAll);
            all = all.setScale(2, BigDecimal.ROUND_HALF_UP);
            textResultAll.setText("" + all);
        }

    }
}
