package com.mappoc.ui.cluster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.mappoc.R;
import com.mappoc.data.model.Person;
import com.mappoc.ui.livetracking.LiveTrackingActivity;
import com.mappoc.utils.MyClusterRender;

public class ClusteringActivity extends AppCompatActivity implements OnMapReadyCallback,
        ClusterManager.OnClusterItemClickListener<Person>,
        ClusterManager.OnClusterClickListener<Person> {

    private ClusterManager<Person> mClusterManager;
    private GoogleMap mMap;
    private ClusteringActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clustering);
        initViewModel();
        initMap();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ClusteringActivityViewModel.class);
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


    public void addPerson(View view) {
        mClusterManager.addItems(viewModel.getPersonList());
        mClusterManager.cluster();
    }

    public void openLiveTrackActivity(View view) {
        startActivity(new Intent(this, LiveTrackingActivity.class));
    }
}
