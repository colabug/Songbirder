package com.codepath.apps.restclienttemplate.timeline;


import com.codepath.apps.restclienttemplate.BuildConfig;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.support.DataHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static com.codepath.apps.restclienttemplate.support.Assert.assertViewIsVisible;
import static com.codepath.apps.restclienttemplate.support.ContextHelper.genericViewGroup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

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
    public void shouldHaveUserName() throws Exception
    {
        assertViewIsVisible( holder.tvUserName );
        assertThat( holder.tvUserName.getText().toString(),
                    equalTo( list.get( FIRST_INDEX ).getName() ) );
    }

    @Test
    public void shouldHaveTweetText() throws Exception
    {
        assertViewIsVisible( holder.tvTweet );
        assertThat( holder.tvTweet.getText().toString(),
                    equalTo( list.get( FIRST_INDEX ).getTweetText() ) );

    }

    @Test
    public void getItemCount_shouldReturnCount() throws Exception
    {
        assertThat( list.size(), equalTo( adapter.getItemCount() ) );
    }
}