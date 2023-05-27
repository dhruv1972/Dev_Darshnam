package com.example.devdarshanam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TemplesMenu extends AppCompatActivity {

    ListView uiTemplesList;
    ArrayList<String> templesList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temples_menu);
        getSupportActionBar().hide();

        Intent i = getIntent();
        String selectedState = i.getStringExtra("selectedState");
        String selectedCity = i.getStringExtra("selectedCity");

        uiTemplesList= (ListView) findViewById(R.id.uiTemplesList);

        FirebaseFirestore.getInstance().collection("States").document(selectedState).collection("Cities").document(selectedCity).collection("Temples").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot allTemples, @Nullable FirebaseFirestoreException error) {
                templesList.clear();
                for(DocumentSnapshot temple : allTemples){
                     templesList.add(temple.get("Name").toString());
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(TemplesMenu.this, R.layout.row, templesList);
                adapter.notifyDataSetChanged();
                uiTemplesList.setAdapter(adapter);
            }
        });

        uiTemplesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String selectedTemple = templesList.get(index);
                Intent i = new Intent(TemplesMenu.this, DashboardTemple.class);

                i.putExtra("selectedState", selectedState);
                i.putExtra("selectedCity", selectedCity);
                i.putExtra("selectedTemple", selectedTemple);

                startActivity(i);
            }
        });



    }
}