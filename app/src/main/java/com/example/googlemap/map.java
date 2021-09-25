package com.example.googlemap;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemap.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

public class map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Location currentLocation;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //copied code
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(map.this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                fusedLocationProviderClient.getLastLocation().
                        addOnSuccessListener(map.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location1) {
                                currentLocation=location1;

                                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                        .findFragmentById(R.id.map);
                                mapFragment.getMapAsync(map.this);
                            }
                        });
            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng currentPlace=new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        // Add a marker in current place and move the camera

        MarkerOptions marker=new MarkerOptions()
                .position(currentPlace)
                .title("My Current Location")
                .snippet(currentLocation.getLatitude()+" "+ currentLocation.getLongitude())  //snippet=subtitle
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_home));
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.home));


        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPlace));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPlace,15.0f));

    }
}