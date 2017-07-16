package com.codepath.apps.songbirder.timeline;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.codepath.apps.songbirder.db.AppDatabase;
import com.codepath.apps.songbirder.db.DatabaseInitializer;
import com.codepath.apps.songbirder.models.Tweet2;

import java.util.List;

public class TweetViewModel extends AndroidViewModel
{
    private AppDatabase database;

    public final LiveData<List<Tweet2>> tweets;

    public TweetViewModel(Application application)
    {
        super(application);

        createDb();

        tweets = database.tweetModel().findAllTweets();
    }

    private void createDb()
    {
        database = AppDatabase.getInMemoryDatabase(this.getApplication());

        DatabaseInitializer.populateAsync(database);
    }
}
