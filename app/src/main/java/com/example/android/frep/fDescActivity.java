package com.example.android.frep;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fDescActivity extends AppCompatActivity {

    resepNusantara resep;
    favouritedResep favResep;
    //A vessel for favouritedResep database
    List<favouritedResep> favouriteList = new ArrayList<>();

    DatabaseReference dbFav;
    //get the user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_desc);
        dbFav = FirebaseDatabase.getInstance().getReference("favouritedResep");

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

                /*
                view.setBackgroundColor(Color.parseColor("#000000"));

                Snackbar.make(view, "Unfavorited !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                view.setBackgroundColor(Color.parseColor("#FFC0CB"));

                Snackbar.make(view, "Favorited !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */

                for(int i=0; i<favouriteList.size(); i++) {

                    if(favouriteList.get(i).getEmail().equals(user.getEmail()) && favouriteList.get(i).getKey().equals(resep.getId())) {

                        favouriteList.remove(i);

                    } else {

                        favouritedResep thisResep = new favouritedResep(user.getEmail(),resep.getId());
                        dbFav.child("FavouritedResep").setValue(thisResep); //Check the child name

                    }

                }

            }
        });



        //Modifying the Description
        TextView desc = (TextView) findViewById(R.id.desc);
        desc.setText("Asal Daerah : "+ resep.getAsalDaerah() +"\n\nBahan : \n"+ resep.getBahan()+ "\n\nCara : \n"+resep.getCara() );
        desc.setTextSize(18);desc.setTextColor(getColor(android.R.color.black));
    }


    @Override
    protected void onStart() {
        super.onStart();

            //get if the favourite
            dbFav.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    favouriteList.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        favouritedResep favR = postSnapshot.getValue(favouritedResep.class);

                        favouriteList.add(favR);

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }


}
