package com.example.devdarshanam;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.devdarshanam.databinding.ActivityLocateTempleBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;



public class LocateTemple extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String latitude, longitude;
    String selectedState, selectedCity, selectedTemple;
    private ActivityLocateTempleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        selectedTemple = intent.getStringExtra("selectedTemple");
        selectedCity = intent.getStringExtra("selectedCity");
        selectedState = intent.getStringExtra("selectedState");

        DocumentReference temple = FirebaseFirestore.getInstance().document("/States/"+selectedState+"/Cities/"+selectedCity+"/Temples/"+selectedTemple);
        temple.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                       @Override
                                       public void onEvent(@Nullable DocumentSnapshot temple, @Nullable FirebaseFirestoreException error) {
                                           latitude = temple.get("Latitude").toString();
                                           longitude = temple.get("Longitude").toString();
                                       }
                                   });

        binding = ActivityLocateTempleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng  templeLocation = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
        mMap.addMarker(new MarkerOptions().position(templeLocation).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(templeLocation));

    }
}