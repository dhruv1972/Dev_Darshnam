package com.example.devdarshanam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DashboardTemple extends AppCompatActivity {

    Button getLocation, nearbyHotels, liveDarshan, aboutUs, weather, More;
    String selectedState, selectedCity, selectedTemple, uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_temple);

        Intent i = getIntent();

        getLocation = findViewById(R.id.getLocation);
        nearbyHotels = findViewById(R.id.nearbyHotel);
        liveDarshan = findViewById(R.id.liveDarshan);
        aboutUs = findViewById(R.id.about_us);
        weather = findViewById(R.id.weather);
        More = findViewById(R.id.More);

        selectedState = i.getStringExtra("selectedState");
        selectedCity = i.getStringExtra("selectedCity");
        selectedTemple = i.getStringExtra("selectedTemple");

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DashboardTemple.this, LocateTemple.class);

                i.putExtra("selectedState", selectedState);
                i.putExtra("selectedCity", selectedCity);
                i.putExtra("selectedTemple", selectedTemple);

                startActivity(i);

            }
        });

        nearbyHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "https://www.google.com/maps/search/hotels+near+@" + selectedTemple + ", " + selectedCity;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);
            }
        });

        liveDarshan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uri = "https://www.youtube.com/watch?v=77_R4qlg3zg&t=1s";

                DocumentReference temple = FirebaseFirestore.getInstance().document("/States/" + selectedState + "/Cities/" + selectedCity + "/Temples/" + selectedTemple);
                temple.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot temple, @Nullable FirebaseFirestoreException error) {
                        uri = temple.get("Live_Darshan").toString();
                    }
                });

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);

            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference temple = FirebaseFirestore.getInstance().document("/States/" + selectedState + "/Cities/" + selectedCity + "/Temples/" + selectedTemple);
                temple.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot temple, @Nullable FirebaseFirestoreException error) {
                        uri = temple.get("About_Temple").toString();
                    }
                });

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text;
                text = "weather"+" "+selectedTemple;

                String uri = "http://www.google.com/search?q="+text;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);
            }
        });

        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String text = "";
//                text="https://templeinfo.azurewebsites.net/webform1";
//                String uri = "http://www.google.com/search?q="+text;

                uri = "https://templeinfo.azurewebsites.net/webform1";
                // String uri = "http://www.google.com/search?q="+text;

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);
            }
        });

    }


}