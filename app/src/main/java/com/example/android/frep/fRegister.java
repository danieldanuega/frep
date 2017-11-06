package com.example.android.frep;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fRegister extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailTxt;
    private EditText passTxt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_register);

        mAuth = FirebaseAuth.getInstance();

        emailTxt = (EditText) findViewById(R.id.emailTxt);
        passTxt = (EditText) findViewById(R.id.passTxt);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });
    }



    private void signIn() {

        String email = emailTxt.getText().toString();
        String pass = passTxt.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

            Toast.makeText(fRegister.this, "Email atau Password tidak terisi", Toast.LENGTH_LONG).show();

        } else {

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(fRegister.this, "Email atau Password anda salah", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(fRegister.this, "Selamat Datang " + user.getEmail(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(fRegister.this, fHome.class));
                    }

                }
            });

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent intent = new Intent(fRegister.this, fHome.class);
        startActivity(intent);
    }
}
