package com.mappoc.ui.livetracking;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mappoc.data.DataManager;

public class LiveTrackingRepo {

    public MutableLiveData<DataSnapshot> getLiveCoordinates(String userId) {
        final MutableLiveData<DataSnapshot> updateCoordinatesLiveData = new MutableLiveData<>();
        DataManager.getInstance().getLiveCoordinates(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateCoordinatesLiveData.setValue(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return updateCoordinatesLiveData;
    }
}
