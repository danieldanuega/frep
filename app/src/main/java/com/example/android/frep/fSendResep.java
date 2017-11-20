package com.example.android.frep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fSendResep extends AppCompatActivity {

    private Button tambahBtn;
    private EditText namaReseptxt;
    private EditText ketTxt;
    private EditText caraTxt;
    private EditText bahanTxt;
    private EditText asalTxt;
    private DatabaseReference dbresep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_send_resep);

        namaReseptxt = (EditText) findViewById(R.id.namaResepTxt);
        ketTxt = (EditText) findViewById(R.id.ketResepTxt);
        caraTxt = (EditText) findViewById(R.id.caraTxt);
        bahanTxt = (EditText) findViewById(R.id.bahanTxt);
        asalTxt = (EditText) findViewById(R.id.asalTxt);
        tambahBtn = (Button) findViewById(R.id.tambahBtn);

        dbresep = FirebaseDatabase.getInstance().getReference("resepNusantara");

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tambahResep();

            }
        });

    }

    private void tambahResep() {

        String nama,ket,cara,bahan,asal;
        Long rating,rating2,rating3;
        String id;

        nama = namaReseptxt.getText().toString();
        ket = ketTxt.getText().toString();
        cara = caraTxt.getText().toString();
        bahan = bahanTxt.getText().toString();
        asal = asalTxt.getText().toString();
        id = dbresep.push().getKey();
        rating = 0L;
        rating2 = 0L;
        rating3 = 0L;

        if(TextUtils.isEmpty(nama) || TextUtils.isEmpty(ket) || TextUtils.isEmpty(cara) || TextUtils.isEmpty(bahan) || TextUtils.isEmpty(asal)) {

            Toast.makeText(fSendResep.this,"Satu atau lebih isian tidak terisi !", Toast.LENGTH_LONG).show();

        } else {

            //Creating the object
            resepNusantara resep = new resepNusantara(asal,bahan,cara,id,ket,nama,rating,rating2,rating3);

            //Insert the data into FirebaseDatabase
            dbresep.child(id).setValue(resep);

            //clear the text box
            namaReseptxt.setText("");
            ketTxt.setText("");
            caraTxt.setText("");
            bahanTxt.setText("");
            asalTxt.setText("");
            tambahBtn.setText("");

            Toast.makeText(fSendResep.this,"Penambahan Resep berhasil !",Toast.LENGTH_LONG).show();

            finish();

        }

    }
}
