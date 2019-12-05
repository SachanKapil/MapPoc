package com.mappoc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        ClusterManager.OnClusterItemClickListener<Person>,
        ClusterManager.OnClusterClickListener<Person> {

    private ClusterManager<Person> mClusterManager;
    private GoogleMap mMap;
    private Double baseLat = 28.7041;
    private Double baseLong = 77.1025;
    private int start = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap();
    }

    private void initMap() {
        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "We are unable to load map", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupMap();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.726600000000037, 77.11850000000011), 13));

        mClusterManager = new ClusterManager<>(this, googleMap);
        MyClusterRender mClusterRender = new MyClusterRender(this, mMap, mClusterManager);
        mClusterManager.setRenderer(mClusterRender);
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
        googleMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
    }

    protected void setupMap() {
        if (mMap != null) {
            return;
        }

        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        // permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onClusterClick(Cluster<Person> cluster) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(),
                (float) Math.floor(mMap.getCameraPosition().zoom + 2)),
                300,
                null);
        return true;
    }

    @Override
    public boolean onClusterItemClick(Person person) {
        return false;
    }
//    https://homepages.cae.wisc.edu/~ece533/images/airplane.png
    public void addPerson(View view) {
        for (int i = start; i <= start + 10; i++) {
            baseLat += 0.00045;
            baseLong += 0.00032;
            mClusterManager.addItem(new Person(baseLat, baseLong, "Person " + i, "https://i.pravatar.cc/300"));
        }
        mClusterManager.cluster();
    }
}
