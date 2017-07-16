package com.codepath.apps.songbirder.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.codepath.apps.songbirder.models.User;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface  UserDao
{
    @Insert(onConflict = IGNORE)
    void insertUser(User user);
}
