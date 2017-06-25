package com.codepath.apps.songbirder.timeline;


import com.codepath.apps.songbirder.BuildConfig;
import com.codepath.apps.songbirder.models.Tweet;
import com.codepath.apps.songbirder.support.DataHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static com.codepath.apps.songbirder.support.Assert.assertViewIsVisible;
import static com.codepath.apps.songbirder.support.ContextHelper.genericViewGroup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TweetAdapterTest
{
    private static final int FIRST_INDEX = 0;

    private TweetAdapter adapter;
    private TweetAdapter.ViewHolder holder;
    private ArrayList<Tweet> list;

    @Before
    public void setUp() throws Exception
    {
        list = DataHelper.populateList();

        adapter = new TweetAdapter( list );

        holder = adapter.onCreateViewHolder( genericViewGroup(), FIRST_INDEX );
        adapter.onBindViewHolder( holder, FIRST_INDEX );
    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( adapter );
    }

    @Test
    public void onCreateViewHolder_shouldNotBeNull() throws Exception
    {
        assertNotNull( holder );
    }

    @Test
    public void shouldHaveProfileView() throws Exception
    {
        assertViewIsVisible( holder.ivProfileImage );
    }

    @Test
    public void shouldHaveName() throws Exception
    {
        assertViewIsVisible( holder.tvName );
        assertThat( holder.tvName.getText().toString(),
                    equalTo( list.get( FIRST_INDEX ).getName() ) );
    }

    @Test
    public void shouldHaveUserName() throws Exception
    {
        String username = list.get( FIRST_INDEX ).getDisplayUsername();
        assertViewIsVisible( holder.tvUserName );
        assertTrue(username.startsWith( "@" ));
        assertThat( holder.tvUserName.getText().toString(),
                    equalTo( username ) );
    }

    @Test
    public void shouldHaveTimestamp() throws Exception
    {
        assertViewIsVisible( holder.tvRelativeTimestamp );
    }

    @Test
    public void shouldHaveTweetText() throws Exception
    {
        assertViewIsVisible( holder.tvTweetText );
        assertThat( holder.tvTweetText.getText().toString(),
                    equalTo( list.get( FIRST_INDEX ).getTweetText() ) );

    }

    @Test
    public void getItemCount_shouldReturnCount() throws Exception
    {
        assertThat( list.size(), equalTo( adapter.getItemCount() ) );
    }
}