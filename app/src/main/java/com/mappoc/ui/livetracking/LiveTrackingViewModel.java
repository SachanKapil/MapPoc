package com.mappoc.ui.livetracking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;

public class LiveTrackingViewModel extends ViewModel {
    private LiveTrackingRepo repo = new LiveTrackingRepo();

    private MutableLiveData<String> mLiveTrackingData = new MutableLiveData<>();

    private LiveData<DataSnapshot> liveTrackingLiveData
            = Transformations.switchMap(mLiveTrackingData, request -> repo.getLiveCoordinates(request));

    public LiveData<DataSnapshot> getAllLatLngsLiveData() {
        return liveTrackingLiveData;
    }

    public void getCurrentPosition(String userId) {
        mLiveTrackingData.setValue(userId);
    }
}
