package com.example.android.frep;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    private Button suggestBtn;
    private Button userBtn;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_home);

        //Initiate
        listViewResep = (ListView) findViewById(R.id.resepList);
        dbResepNusantara = FirebaseDatabase.getInstance().getReference("resepNusantara");
        progressBar = findViewById(R.id.progressBar);

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

        //To Send a Resep
        suggestBtn = (Button) findViewById(R.id.suggestBtn);
        suggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(fHome.this, fSendResep.class));
            }
        });

        //To go to Acc page
        userBtn = (Button) findViewById(R.id.btnUser);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(fHome.this, fAccPage.class));

            }
        });
    }



    //To view specific rating
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId() == R.id.resepList) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            resepNusantara selectedResep = listResepNusantara.get(info.position);
            menu.setHeaderTitle("Rating");
            String[] menuItems = getResources().getStringArray(R.array.rater);
            String[] newMenuItems = new String[menuItems.length];

            newMenuItems[0] = menuItems[0] + " sebanyak " + selectedResep.getRating();
            newMenuItems[1] = menuItems[1] + " sebanyak " + selectedResep.getRating2();
            newMenuItems[2] = menuItems[2] + " sebanyak " + selectedResep.getRating3();

            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, newMenuItems[i]);
            }
        }
    }

    //To give rating
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        resepNusantara selectedResep = listResepNusantara.get(index);

        Long total;

        switch(item.getItemId()) {

            case 0:
                total = selectedResep.getRating() + 1L;
                selectedResep.setRating(total);
                dbResepNusantara.child(selectedResep.getId()).child("rating").setValue(total);
                break;

            case 1:
                total = selectedResep.getRating2() + 1L;
                selectedResep.setRating2(total);
                dbResepNusantara.child(selectedResep.getId()).child("rating2").setValue(total);
                break;

            case 2:
                total = selectedResep.getRating3() + 1L;
                selectedResep.setRating3(total);
                dbResepNusantara.child(selectedResep.getId()).child("rating3").setValue(total);
                break;
        }

        return super.onContextItemSelected(item);
    }



    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(ProgressBar.VISIBLE);
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
                registerForContextMenu(listViewResep);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
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
