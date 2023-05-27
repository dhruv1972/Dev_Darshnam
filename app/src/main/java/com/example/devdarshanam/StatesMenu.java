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
import java.util.List;

public class StatesMenu extends AppCompatActivity {

    ListView uiStateList;
    private List<String> statesList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_states_menu);
        getSupportActionBar().hide();

        uiStateList = findViewById(R.id.uiStateList);
        FirebaseFirestore.getInstance().collection("States").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot allStates, @Nullable FirebaseFirestoreException error) {
                statesList.clear();

                for(DocumentSnapshot state : allStates){
                    statesList.add(state.get("State Name").toString());
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(StatesMenu.this, R.layout.row, statesList);
                adapter.notifyDataSetChanged();
                uiStateList.setAdapter(adapter);
            }
        });

        uiStateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String selectedState = statesList.get(index);
                Intent i = new Intent(StatesMenu.this, CityMenu.class);
                i.putExtra("selectedState", selectedState);
                startActivity(i);
            }
        });

    }
}