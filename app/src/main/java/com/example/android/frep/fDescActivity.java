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
    List<favouritedResep> key = new ArrayList<>();

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

                for(int i=0; i<key.size(); i++) {

                    if (key.get(i).getEmail().equals(user.getEmail().substring(0,user.getEmail().indexOf('@'))) && key.get(i).getKey().equals(resep.getId())) {

                        dbFav.child(key.get(i).getEmail()).removeValue();
                        view.setBackgroundColor(Color.parseColor("#000000"));

                        Snackbar.make(view, "Unfavorited !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {

                        favResep = new favouritedResep(user.getEmail(),resep.getId());
                        dbFav.child(user.getEmail()).setValue(favResep);
                        view.setBackgroundColor(Color.parseColor("#FFC0CB"));

                        Snackbar.make(view, "Favorited !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
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

        dbFav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clear the list
                key.clear();


                //iterating all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //getting the favourited resep
                    favouritedResep fr = postSnapshot.getValue(favouritedResep.class);

                    //adding resep to the list
                    key.add(fr);
                }

                //Set the color button for the first time
                FloatingActionButton fav = (FloatingActionButton) findViewById(R.id.favBtn);
                for(int i=0; i<key.size(); i++) {
                    if (key.get(i).getEmail().equals(user.getEmail()) && key.get(i).getKey().equals(resep.getId())) {

                        fav.setBackgroundColor(Color.parseColor("#000000"));

                    } else {

                        fav.setBackgroundColor(Color.parseColor("#FFC0CB"));

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
