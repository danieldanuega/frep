package com.danielgo.android.frep;

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
    private Button btnBuat;
    private EditText email;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_register);

        mAuth = FirebaseAuth.getInstance();

        btnBuat = (Button) findViewById(R.id.btnBuat);
        email = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();
            }
        });

    }

    private void register() {

        String emailTxt = email.getText().toString();
        String passTxt = pass.getText().toString();

        if(TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passTxt)) {

            Toast.makeText(fRegister.this, "Email atau Password tidak terisi", Toast.LENGTH_LONG).show();

        } else {

            if(passTxt.length() < 8) {

                Toast.makeText(fRegister.this, "Password terlalu pendek, minimal 8 karakter", Toast.LENGTH_LONG).show();

            } else {

                mAuth.createUserWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(fRegister.this, "Maaf terjadi kendala internal", Toast.LENGTH_LONG).show();
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(fRegister.this, "Selamat Datang " + user.getEmail(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(fRegister.this, fHome.class));
                        }
                    }

                });
            }

        }
    }


}
