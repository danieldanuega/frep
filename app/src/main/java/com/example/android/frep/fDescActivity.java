package com.example.android.frep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fDescActivity extends AppCompatActivity {

    resepNusantara resep;
    DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_desc);

        //get the object
        resep = (resepNusantara) getIntent().getSerializableExtra("RESEP");


        //modifying app bar or bar in the top
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(resep.getNama());


        //Modifying the favourite button //ERROR
        FloatingActionButton fav = (FloatingActionButton) findViewById(R.id.favBtn);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                resep.setFavourite(!resep.getFavourite());

                data = FirebaseDatabase.getInstance().getReference("resepNusantara");
                //data.child("resepNusantara").child(resep.getId().toString()).;


                if(resep.getFavourite()) {

                    Snackbar.make(view, "Favorited !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    Snackbar.make(view, "Unfavorited !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });


        //Modifying the Description
        TextView desc = (TextView) findViewById(R.id.desc);
        desc.setText(resep.getKeteranganResep());
    }
}
