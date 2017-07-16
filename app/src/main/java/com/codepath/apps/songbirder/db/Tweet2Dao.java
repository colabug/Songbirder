package com.codepath.apps.songbirder.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.codepath.apps.songbirder.models.Tweet2;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface Tweet2Dao
{
    @Query("SELECT * FROM Tweet2")
    LiveData<List<Tweet2>> findAllTweets();

    @Insert(onConflict = IGNORE)
    void insertTweet(Tweet2 tweet);

    @Query("DELETE FROM Tweet2")
    void deleteAll();
}
