package com.mappoc.data.firebase;


import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by appinventiv-pc on 7/3/18.
 */

public class FirebaseEventListeners implements ChildEventListener {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
    }


    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
}
