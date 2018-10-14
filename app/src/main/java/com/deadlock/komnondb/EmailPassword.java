package com.deadlock.komnondb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPassword extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText ETemail, ETpassword;
    private Button BTNregistration, BTNsignIn;
    private FirebaseAuth.AuthStateListener authStateListener;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        mAuth = FirebaseAuth.getInstance();

        ETemail = findViewById(R.id.et_mail);
        ETpassword = findViewById(R.id.et_pass);

        BTNregistration = findViewById(R.id.btn_registration);
        BTNsignIn = findViewById(R.id.btn_sign_in);
        BTNregistration.setOnClickListener(this);
        BTNsignIn.setOnClickListener(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(EmailPassword.this, UpperActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_registration):
                if (loginEmpty()) {
                    toast = Toast.makeText(EmailPassword.this, "Заполните все поля...",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 40);
                    toast.show();
                } else {
                    registration(ETemail.getText().toString(), ETpassword.getText().toString());
                }
                break;
            case (R.id.btn_sign_in):
                if (loginEmpty()) {
                    toast = Toast.makeText(EmailPassword.this, "Заполните все поля...",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 40);
                    toast.show();
                } else {
                    signing(ETemail.getText().toString(), ETpassword.getText().toString());
                }
                break;
        }
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(EmailPassword.this, UpperActivity.class);
                            startActivity(intent);
                            toast = Toast.makeText(EmailPassword.this, "Регистрация успешна!",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 40);
                            toast.show();
                        } else {
                            Toast.makeText(EmailPassword.this, "Регистрация не выполнена...",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 40);
                            toast.show();
                        }
                    }
                });
    }

    public void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(EmailPassword.this, UpperActivity.class);
                            startActivity(intent);
                            toast = Toast.makeText(EmailPassword.this, "Вход выполнен!",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 40);
                            toast.show();

                        } else {
                            toast = Toast.makeText(EmailPassword.this, "Вход не выполнен...",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 40);
                            toast.show();
                        }
                    }
                });
    }

    private boolean loginEmpty() {
        return TextUtils.isEmpty(ETemail.getText().toString()) ||
                TextUtils.isEmpty(ETpassword.getText().toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}
