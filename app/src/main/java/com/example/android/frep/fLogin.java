package com.example.android.frep;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailTxt;
    private EditText passTxt;
    private Button loginBtn;
    private Button daftarBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_login);

        mAuth = FirebaseAuth.getInstance();

        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passTxt = (EditText) findViewById(R.id.passTxt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        daftarBtn = (Button) findViewById(R.id.btnReg);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideSoftKeyboard();
                signIn();

            }
        });

        daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideSoftKeyboard();

                startActivity(new Intent(fLogin.this, fRegister.class));

            }
        });
    }



    private void signIn() {

        String email = emailTxt.getText().toString();
        String pass = passTxt.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

            Toast.makeText(fLogin.this, "Email atau Password tidak terisi", Toast.LENGTH_LONG).show();

        } else {

            progressBar.setVisibility(ProgressBar.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(fLogin.this, "Email atau Password anda salah", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(fLogin.this, "Selamat Datang " + user.getEmail(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(fLogin.this, fHome.class));
                    }

                }
            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(ProgressBar.INVISIBLE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent intent = new Intent(fLogin.this, fHome.class);
            startActivity(intent);
        }

    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
