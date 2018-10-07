package com.deadlock.komnondb;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    public void onClick(View view) {
        if (view.getId() == R.id.btn_registration) {
            registration(ETemail.getText().toString(), ETpassword.getText().toString());
        } else if (view.getId() == R.id.btn_sign_in) {
            signing(ETemail.getText().toString(), ETpassword.getText().toString());
        }
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(EmailPassword.this, UpperActivity.class);
                            startActivity(intent);
                            /*Toast.makeText(EmailPassword.this, "Регистрация успешна!",
                                    Toast.LENGTH_SHORT).show();*/
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(EmailPassword.this, "Регистрация не выполнена...",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(EmailPassword.this, UpperActivity.class);
                            startActivity(intent);
                            /*Toast.makeText(EmailPassword.this, "Вход выполнен!",
                                    Toast.LENGTH_SHORT).show();*/
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(EmailPassword.this, "Вход не выполнен...",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
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
