package com.deadlock.komnondb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KoefActivity extends MainActivity implements View.OnClickListener {

    String SAVED_HW_KEF2 = "saved_hw_kef2";
    String SAVED_CW_KEF2 = "saved_cw_kef2";
    String SAVED_T1_KEF2 = "saved_t1_kef2";
    String SAVED_T2_KEF2 = "saved_t2_kef2";
    String SAVED_T3_KEF2 = "saved_t3_kef2";
    String SAVED_WOF_KEF2 = "saved_wof_kef2";
    String SAVED_PHONE_KEF2 = "saved_phone_kef2";
    String SAVED_HOUSE_KEF2 = "saved_house_kef2";
    String SAVED_PERSONS_KEF2 = "saved_persons_kef2";

    EditText etHwKef, etCwKef, etT1Kef, etT2Kef, etT3Kef,
            etWofKef, etPhoneKef, etHouseKef, etPersonsKef;

    Button btnSaveKef;

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

        btnSaveKef = findViewById(R.id.saveKef);
        btnSaveKef.setOnClickListener(this);

        loadKef();
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("hwKef", Float.valueOf(etHwKef.getText().toString()));
        intent.putExtra("cwKef", Float.valueOf(etCwKef.getText().toString()));
        intent.putExtra("T1Kef", Float.valueOf(etT1Kef.getText().toString()));
        intent.putExtra("T2Kef", Float.valueOf(etT2Kef.getText().toString()));
        intent.putExtra("T3Kef", Float.valueOf(etT3Kef.getText().toString()));
        intent.putExtra("wofKef", Float.valueOf(etWofKef.getText().toString()));
        intent.putExtra("phoneKef", Float.valueOf(etPhoneKef.getText().toString()));
        intent.putExtra("houseKef", Float.valueOf(etHouseKef.getText().toString()));
        intent.putExtra("personsKef", Integer.valueOf(etPersonsKef.getText().toString()));
        saveKef();
        startActivity(intent);
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveKef();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKef();
    }


    public void saveKef() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putFloat(SAVED_HW_KEF2, Float.parseFloat(etHwKef.getText().toString()));
        ed.putFloat(SAVED_CW_KEF2, Float.parseFloat(etCwKef.getText().toString()));
        ed.putFloat(SAVED_T1_KEF2, Float.parseFloat(etT1Kef.getText().toString()));
        ed.putFloat(SAVED_T2_KEF2, Float.parseFloat(etT2Kef.getText().toString()));
        ed.putFloat(SAVED_T3_KEF2, Float.parseFloat(etT3Kef.getText().toString()));
        ed.putFloat(SAVED_WOF_KEF2, Float.parseFloat(etWofKef.getText().toString()));
        ed.putFloat(SAVED_PHONE_KEF2, Float.parseFloat(etPhoneKef.getText().toString()));
        ed.putFloat(SAVED_HOUSE_KEF2, Float.parseFloat(etHouseKef.getText().toString()));
        ed.putInt(SAVED_PERSONS_KEF2, Integer.parseInt(etPersonsKef.getText().toString()));
        ed.commit();
    }

    public void loadKef() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedHW_Kef = String.valueOf(sPref.getFloat(SAVED_HW_KEF2, 0));
        String savedCW_Kef = String.valueOf(sPref.getFloat(SAVED_CW_KEF2, 0));
        String savedT1_Kef = String.valueOf(sPref.getFloat(SAVED_T1_KEF2, 0));
        String savedT2_Kef = String.valueOf(sPref.getFloat(SAVED_T2_KEF2, 0));
        String savedT3_Kef = String.valueOf(sPref.getFloat(SAVED_T3_KEF2, 0));
        String savedWof_Kef = String.valueOf(sPref.getFloat(SAVED_WOF_KEF2, 0));
        String savedPhone_Kef = String.valueOf(sPref.getFloat(SAVED_PHONE_KEF2, 0));
        String savedHouse_Kef = String.valueOf(sPref.getFloat(SAVED_HOUSE_KEF2, 0));
        String savedPersons_Kef = String.valueOf(sPref.getInt(SAVED_PERSONS_KEF2, 0));
        etHwKef.setText(savedHW_Kef);
        etCwKef.setText(savedCW_Kef);
        etT1Kef.setText(savedT1_Kef);
        etT2Kef.setText(savedT2_Kef);
        etT3Kef.setText(savedT3_Kef);
        etWofKef.setText(savedWof_Kef);
        etPhoneKef.setText(savedPhone_Kef);
        etHouseKef.setText(savedHouse_Kef);
        etPersonsKef.setText(savedPersons_Kef);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveKef();
    }
}
