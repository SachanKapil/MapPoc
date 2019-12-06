package com.mappoc.data.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;


public class Person implements ClusterItem {

    private LatLng mPosition;
    private String name;
    private String profileUrl;

    public Person(double lat, double lng, String name, String profileUrl) {
        this.name = name;
        this.profileUrl = profileUrl;
        mPosition = new LatLng(lat, lng);
    }

    public LatLng getmPosition() {
        return mPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return profileUrl;
    }
}
