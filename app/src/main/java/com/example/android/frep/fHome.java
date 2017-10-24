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

    List<resepNusantara> listResepNusantara;
    ListView listViewResep;

    DatabaseReference dbResepNusantara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_home);

        listViewResep = (ListView) findViewById(R.id.resepList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbResepNusantara.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clear the list
                listResepNusantara.clear();

                //iterating all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting resep
                    resepNusantara resepNusantara = postSnapshot.getValue(resepNusantara.class);
                    //adding resep to the list
                    listResepNusantara.add(resepNusantara);
                }

                //creating the adapter for the list
                ResepList resepListAdapter = new ResepList(fHome.this, listResepNusantara);
                //attaching adapter to the listView
                listViewResep.setAdapter(resepListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
