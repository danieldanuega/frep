package com.example.android.frep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fHome extends AppCompatActivity {

    EditText editKet = (EditText) findViewById(R.id.editKet);
    EditText editDaerah = (EditText) findViewById(R.id.editDaerah);
    EditText editBahan = (EditText) findViewById(R.id.editBahan);
    EditText editCara = (EditText) findViewById(R.id.editCara);
    EditText editRating = (EditText) findViewById(R.id.editRating);
    Button btnSimpan = (Button) findViewById(R.id.btnSimpan);

    List<resepNusantara> resepNusantaras;

    DatabaseReference dbResepNusantara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_home);

        dbResepNusantara = FirebaseDatabase.getInstance().getReference("resepNusantara");

        resepNusantaras = new ArrayList<>();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addResep();
            }
        });

    }



    private void addResep() {

        String keteranganResep = editKet.getText().toString();
        String asalDaerah = editDaerah.getText().toString();
        String bahan = editBahan.getText().toString();
        String cara = editBahan.getText().toString();
        String rating = editRating.getText().toString();

        if(!TextUtils.isEmpty(keteranganResep)) {

            String id = dbResepNusantara.push().getKey();

            resepNusantara rn = new resepNusantara(id,keteranganResep,asalDaerah,bahan,cara,rating);
            dbResepNusantara.child(id).setValue(rn);

            Toast.makeText(this, "resep added", Toast.LENGTH_LONG).show();
        }
    }
}
