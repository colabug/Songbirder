package com.codepath.apps.songbirder.db;

import android.os.AsyncTask;

import com.codepath.apps.songbirder.models.Tweet2;
import com.codepath.apps.songbirder.models.User;

public class DatabaseInitializer
{
    public static void populateAsync(final AppDatabase db)
    {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addTweet(final AppDatabase db, final long id, final String text, long userId)
    {
        Tweet2 tweet = new Tweet2();
        tweet.uid = id;
        tweet.tweetText = text;
        tweet.userId = userId;
        db.tweetModel().insertTweet( tweet );
    }

    private static User addUser(final AppDatabase db, final long id, final String name, final String screenName)
    {
        User user = new User();
        user.uid = id;
        user.name = name;
        user.screenName = screenName;
        db.userModel().insertUser( user );
        return user;
    }

    private static void populateWithTestData(AppDatabase db)
    {
        db.tweetModel().deleteAll();

        User user1 = addUser( db, 1, "Corey", "corey_latislaw" );
        User user2 = addUser( db, 2, "Leigh", "colabug" );

        addTweet( db, 1, "Hi there!", user1.uid );
        addTweet(db, 2, "Whoa, this actually works!", user2.uid);
        addTweet(db, 3, "How are you?", user1.uid);
        addTweet(db, 4, "I'm fine thanks!", user2.uid);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {
        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db)
        {
            database = db;
        }

        @Override
        protected Void doInBackground (final Void... params)
        {
            populateWithTestData( database );
            return null;
        }
    }
}
