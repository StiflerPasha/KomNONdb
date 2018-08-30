package com.deadlock.komnondb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KoefActivity extends MainActivity implements View.OnClickListener {

    EditText etHwKef, etCwKef, etT1Kef, etT2Kef, etT3Kef,
            etWofKef, etPhoneKef, etHouseKef, etPersonsKef;

    Button saveKef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koef);

        etHwKef = findViewById(R.id.etHwKef);
        etCwKef = findViewById(R.id.etCwKef);
        etT1Kef = findViewById(R.id.etT1Kef);
        etT2Kef = findViewById(R.id.etT2Kef);
        etT3Kef = findViewById(R.id.etT3Kef);
        etWofKef = findViewById(R.id.etWofKef);
        etPhoneKef = findViewById(R.id.etPhoneKef);
        etHouseKef = findViewById(R.id.etHouseKef);
        etPersonsKef = findViewById(R.id.etPersonsKef);

        saveKef = findViewById(R.id.saveKef);
        saveKef.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (TextUtils.isEmpty(etHwKef.getText().toString()) ||
                TextUtils.isEmpty(etCwKef.getText().toString()) ||
                TextUtils.isEmpty(etT1Kef.getText().toString()) ||
                TextUtils.isEmpty(etT2Kef.getText().toString()) ||
                TextUtils.isEmpty(etT3Kef.getText().toString()) ||
                TextUtils.isEmpty(etWofKef.getText().toString()) ||
                TextUtils.isEmpty(etPhoneKef.getText().toString()) ||
                TextUtils.isEmpty(etHouseKef.getText().toString()) ||
                TextUtils.isEmpty(etPersonsKef.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_LONG).show();
        } else {
            double editHw = Double.parseDouble(etHwKef.getText().toString());
            double editCw = Double.parseDouble(etHwKef.getText().toString());
            double editT1 = Double.parseDouble(etHwKef.getText().toString());
            double editT2 = Double.parseDouble(etHwKef.getText().toString());
            double editT3 = Double.parseDouble(etHwKef.getText().toString());
            double editWof = Double.parseDouble(etHwKef.getText().toString());
            double editPhone = Double.parseDouble(etHwKef.getText().toString());
            double editHouse = Double.parseDouble(etHwKef.getText().toString());
            int editPersons = Integer.parseInt(etHwKef.getText().toString());
        }
    }
}
