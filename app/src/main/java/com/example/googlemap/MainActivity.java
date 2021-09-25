package com.example.googlemap;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
    Double longitude, latitude;

//    LocationRequest mLocationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=(Button)findViewById(R.id.button);
        tv=(TextView)findViewById(R.id.location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        fusedLocationProviderClient.getLastLocation().
                                addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location1) {
                                        if (location1 != null) {
                                            latitude = location1.getLatitude();
                                            longitude = location1.getLongitude();
                                            tv.setText(latitude + "\n" + longitude);
                                            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
                                        } else {
                                            tv.setText("null");
                                        }
                                    }
                                });
                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }
                }
            }
        });

    }
}
