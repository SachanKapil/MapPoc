package com.mappoc.data;

import com.google.firebase.database.DatabaseReference;
import com.mappoc.data.firebase.FirebaseDatabaseQueries;

public class DataManager {
    private static DataManager instance;
    private FirebaseDatabaseQueries mFirebaseQueries;


    private DataManager() {
        mFirebaseQueries = FirebaseDatabaseQueries.getInstance();
    }

    /**
     * Returns the single instance of {@link DataManager} if
     *
     * @return instance
     */
    public static DataManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before getInstance()");
        }
        return instance;
    }

    /**
     * Method used to create an instance of {@link DataManager}
     *
     * @return instance if it is null
     */
    public synchronized static void init() {
        if (instance == null) {
            instance = new DataManager();
        }
    }

    public DatabaseReference getLiveCoordinates(String userId) {
        return mFirebaseQueries.getLiveCoordinates(userId);
    }
}
