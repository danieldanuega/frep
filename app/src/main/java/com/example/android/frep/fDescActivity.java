package com.example.android.frep;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class fDescActivity extends AppCompatActivity {

    //get the user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FloatingActionButton fav;

    //toggle for filtering the button execution & email is for authenticate the user
    private int toggle = 0;
    private int index = user.getEmail().indexOf('@');
    private String email = user.getEmail().substring(0,index);

    resepNusantara resep;
    //A vessel for favouritedResep database
    List<favouritedResep> favouriteList = new ArrayList<>();

    DatabaseReference dbFav;
    ImageView imgResep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_desc);
        //Initiate
        dbFav = FirebaseDatabase.getInstance().getReference("favouritedResep");
        fav = findViewById(R.id.favBtn);
        imgResep = findViewById(R.id.imgResep);

        //get the object
        resep = (resepNusantara) getIntent().getSerializableExtra("RESEP");

        //modifying app bar or toolbar in the top
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(resep.getNama());
        //PICASSO HERE
        Picasso.with(this).load(resep.getImgUrl()).into(imgResep);

        //Modifying the favourite button
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0; i<favouriteList.size(); i++) {

                    //Check if the user has favourited or not
                    if(favouriteList.get(i).getEmail().equals(email) && favouriteList.get(i).getResepId().equals(resep.getId())) {

                        //removing the data from Database
                        final Query deleteResep = dbFav.orderByChild("resepId").equalTo(resep.getId());
                        deleteResep.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot deletesnapshot : dataSnapshot.getChildren()) {
                                    deletesnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        ///coloring the button
                        fav.setRippleColor(ContextCompat.getColor(getApplicationContext(), R.color.button_default));
                        fav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),R.color.button_default)));

                        Snackbar.make(view, "Unfavorited !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        toggle = 1;
                        return;
                    }
                }

                if(toggle == 0) {
                    //insert the data
                    String favId = dbFav.push().getKey();
                    favouritedResep thisResep = new favouritedResep(email, favId, resep.getId());
                    dbFav.child(favId).setValue(thisResep);

                    //coloring the button
                    fav.setRippleColor(ContextCompat.getColor(getApplicationContext(), R.color.button_focused));
                    fav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),R.color.button_pressed)));

                    Snackbar.make(view, "Favorited !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                toggle = 0;
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

        //get the favourite
        dbFav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                favouriteList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    favouritedResep favR = postSnapshot.getValue(favouritedResep.class);
                    favouriteList.add(favR);
                }


                //coloring the button for the first time
                for(int i=0; i<favouriteList.size(); i++) {

                    if (favouriteList.get(i).getEmail().equals(email) && favouriteList.get(i).getResepId().equals(resep.getId())) {
                        fav.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),R.color.button_pressed)));
                        return;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
