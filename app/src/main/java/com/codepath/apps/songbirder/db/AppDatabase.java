package com.codepath.apps.songbirder.db;

import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.codepath.apps.songbirder.models.Tweet2;
import com.codepath.apps.songbirder.models.User;

@Database(entities = {Tweet2.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase INSTANCE;

    public abstract Tweet2Dao tweetModel();

    public abstract UserDao userModel();

    public static AppDatabase getInMemoryDatabase(Context context)
    {
        if (INSTANCE == null)
        {
            // TODO: Don't do on main thread. See PersistenceBasicSample for an example.
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                           .allowMainThreadQueries()
                           .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }
}