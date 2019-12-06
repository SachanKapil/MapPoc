package com.mappoc.ui.cluster;

import androidx.lifecycle.ViewModel;

import com.mappoc.data.model.Person;

import java.util.ArrayList;

public class ClusteringActivityViewModel extends ViewModel {

    private Double baseLat = 28.7041;
    private Double baseLong = 77.1025;

    ArrayList<Person> getPersonList() {
        ArrayList<Person> peopleList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            baseLat += 0.00045;
            baseLong += 0.00032;
            peopleList.add(new Person(baseLat, baseLong, "Person " + i, "https://i.pravatar.cc/300"));
        }
        return peopleList;
    }
}
