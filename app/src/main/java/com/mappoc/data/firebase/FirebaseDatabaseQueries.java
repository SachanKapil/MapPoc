package com.mappoc.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseQueries {

    private static FirebaseDatabaseQueries instance;
    private static DatabaseReference firebaseDatabaseRef;
    private static FirebaseAuth mAuth;

    private FirebaseDatabaseQueries() {
        if (firebaseDatabaseRef == null) {
            firebaseDatabaseRef = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
        }
    }

    public static FirebaseDatabaseQueries getInstance() {
        if (instance == null) {
            synchronized (FirebaseDatabaseQueries.class) {
                if (instance == null)
                    instance = new FirebaseDatabaseQueries();
            }
        }
        return instance;
    }

    public DatabaseReference getLiveCoordinates(String userId) {
        return firebaseDatabaseRef.child(FirebaseConstants.LOCATION_NODE).child(userId);
    }

}
