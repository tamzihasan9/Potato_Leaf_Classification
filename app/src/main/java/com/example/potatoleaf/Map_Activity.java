package com.example.potatoleaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Activity extends FragmentActivity implements OnMapReadyCallback {

  GoogleMap gMap;
  FrameLayout Map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

       Map =findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.gMap=googleMap;
        LatLng mapBan= new LatLng(23.6850,90.3563);
        this.gMap.addMarker(new MarkerOptions().position(mapBan).title("Marker In Bangladesh"));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapBan));
    }
}