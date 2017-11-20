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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fHome extends AppCompatActivity {

    List<resepNusantara> listResepNusantara = new ArrayList<resepNusantara>();
    ListView listViewResep;

    DatabaseReference dbResepNusantara;

    private Button signOutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_home);

        listViewResep = (ListView) findViewById(R.id.resepList);
        dbResepNusantara = FirebaseDatabase.getInstance().getReference("resepNusantara");

        //To give action in Selected data in ListView
        listViewResep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(fHome.this, fDescActivity.class);
                resepNusantara resep = listResepNusantara.get(i);
                intent.putExtra("RESEP", resep);

                startActivity(intent);

            }
        });

        //To Sign Out User
        signOutBtn = (Button) findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signOut();
            }
        });
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
                    resepNusantara resepN = postSnapshot.getValue(resepNusantara.class);


                    //adding resep to the list
                    listResepNusantara.add(resepN);
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

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
