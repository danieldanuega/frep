package com.example.android.frep;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fDescActivity extends AppCompatActivity {

    private int toggle = 0;

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
        //Initiate
        dbFav = FirebaseDatabase.getInstance().getReference("favouritedResep");

        //get the object
        resep = (resepNusantara) getIntent().getSerializableExtra("RESEP");


        //modifying app bar or bar in the top
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(resep.getNama());


        //Modifying the favourite button
        FloatingActionButton fav = (FloatingActionButton) findViewById(R.id.favBtn);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            int index = user.getEmail().indexOf('@');
            String email = user.getEmail().substring(0,index);


            for(int i=0; i<favouriteList.size(); i++) {

                if(favouriteList.get(i).getEmail().equals(email) && favouriteList.get(i).getResepId().equals(resep.getId())) {

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

                    view.setBackgroundColor(Color.parseColor("#000000"));

                    Snackbar.make(view, "Unfavorited !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    toggle = 1;
                    return;
                }
            }

            if(toggle == 0) {
                String favId = dbFav.push().getKey();
                favouritedResep thisResep = new favouritedResep(email, favId, resep.getId());
                dbFav.child(favId).setValue(thisResep);

                view.setBackgroundColor(Color.parseColor("#FFC0CB"));

                Snackbar.make(view, "Favorited !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                toggle = 0;
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

        //get the favourite
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
