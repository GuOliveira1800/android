package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Location l = lastKnow();

        if(l != null){
            Toast.makeText(this, l.getLatitude() + " / "+ l.getLongitude(), Toast.LENGTH_SHORT).show();
        }

    }

    private Location lastKnow() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location lastKnow = null;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        lastKnow = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lastKnow = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        return lastKnow;
    }

}