package com.mappoc.data.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseValueEventLiveData extends LiveData<DataSnapshot> {
    private static final String TAG = "FirebaseEventLiveData";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();
    private boolean mIsSingleValueEventListener;

    public FirebaseValueEventLiveData(Query query, boolean isSingleValueEventListener) {
        this.query = query;
    }

    public FirebaseValueEventLiveData(DatabaseReference ref, boolean isSingleValueEventListener) {
        this.query = ref;
        this.mIsSingleValueEventListener = isSingleValueEventListener;
    }

    @Override
    protected void onActive() {
        if (mIsSingleValueEventListener)
            query.addListenerForSingleValueEvent(listener);
        else
            query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}