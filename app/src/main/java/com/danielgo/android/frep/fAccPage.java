package com.danielgo.android.frep;

/**
 * Created by Albertus F C on 25/11/2017.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class fAccPage extends AppCompatActivity {

    //get the user
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference dbFav;
    private DatabaseReference dbResepNusantara;

    List<resepNusantara> listResepNusantara = new ArrayList<resepNusantara>();
    List<favouritedResep> listFavouritedResep = new ArrayList<favouritedResep>();
    ListView favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_acc);

        //Initiate
        favList = (ListView) findViewById(R.id.favList);
        dbResepNusantara = FirebaseDatabase.getInstance().getReference("resepNusantara");
        dbFav = FirebaseDatabase.getInstance().getReference("favouritedResep");

        TextView profile = (TextView)findViewById(R.id.textAcc);
        profile.setText("Username : "+ user.getEmail());
        profile.setTextSize(18);profile.setTextColor(getColor(android.R.color.black));


        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(fAccPage.this, fDescActivity.class);
                resepNusantara resep = listResepNusantara.get(i);
                intent.putExtra("RESEP", resep);

                startActivity(intent);

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        dbFav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listFavouritedResep.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    favouritedResep favR = postSnapshot.getValue(favouritedResep.class);

                    listFavouritedResep.add(favR);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbResepNusantara.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int index = user.getEmail().indexOf('@');
                String email = user.getEmail().substring(0,index);

                //clear the list
                listResepNusantara.clear();


                //iterating all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //getting resep
                    resepNusantara resepN = postSnapshot.getValue(resepNusantara.class);

                    //filtering the items, only the favourited one
                    for(favouritedResep f : listFavouritedResep) {
                        if(f.getResepId().equals(resepN.getId()) && f.getEmail().equals(email)) {
                            //adding resep to the list
                            listResepNusantara.add(resepN);
                        }
                    }

                }


                //creating the adapter for the list
                ResepList resepListAdapter = new ResepList(fAccPage.this, listResepNusantara);
                //attaching adapter to the listView
                favList.setAdapter(resepListAdapter);
                registerForContextMenu(favList);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
