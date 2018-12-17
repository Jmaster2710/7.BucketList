package com.joelKroon.bucketlist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {BucketItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase DB;
    public abstract BucketItemDao bucketItemDao();

    public static AppDatabase getInstance(Context context) {
        if( DB == null ) {
            DB = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "bucketlist_data")
                    .allowMainThreadQueries()
                    .build();
        }

        return DB;
    }

    public static void destroyInstance() {
        DB = null;
    }
}
