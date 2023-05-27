package com.example.devdarshanam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCas;
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

public class CityMenu extends AppCompatActivity {

    ListView uiCityList;
    ArrayList<String> cityList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_menu);
        getSupportActionBar().hide();

        Intent i = getIntent();
        String selectedState = i.getStringExtra("selectedState");

        uiCityList = (ListView) findViewById(R.id.uiCityList);
        FirebaseFirestore.getInstance().collection("States").document(selectedState).collection("Cities").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot allcity, @Nullable FirebaseFirestoreException error) {
                cityList.clear();
                for(DocumentSnapshot city : allcity){
                    cityList.add(city.get("City Name").toString());

                }

                ArrayAdapter adapter = new ArrayAdapter<String>(CityMenu.this, R.layout.row, cityList);
                adapter.notifyDataSetChanged();
                uiCityList.setAdapter(adapter);
            }
        });

        uiCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String selectedCity = cityList.get(index);
                Intent i = new Intent(CityMenu.this, TemplesMenu.class);
                i.putExtra("selectedCity", selectedCity);
                i.putExtra("selectedState", selectedState);
                startActivity(i);
            }
        });

    }
}