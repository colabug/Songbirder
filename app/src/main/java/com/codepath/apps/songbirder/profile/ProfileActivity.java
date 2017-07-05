package com.codepath.apps.songbirder.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.SongbirderApplication;
import com.codepath.apps.songbirder.api.TwitterClient;
import com.codepath.apps.songbirder.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity
{
    @BindView(R.id.ivHeaderImage) ImageView ivHeaderImage;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.tvBio) TextView tvBio;
    @BindView(R.id.tvLocation) TextView tvLocation;
    @BindView(R.id.tvWebsite) TextView tvWebsite;
    @BindView(R.id.tvFollowers) TextView tvFollowers;

    private User user;

    protected TwitterClient client;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );
        ButterKnife.bind( this );

        client = SongbirderApplication.getTwitterClient();
        client.getProfile( getProfileHandler() );
    }

    @NonNull
    private JsonHttpResponseHandler getProfileHandler()
    {
        return new JsonHttpResponseHandler(){

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONObject response )
            {
                super.onSuccess( statusCode, headers, response );
                try
                {
                    user = User.fromJson( response );
                    updateViews();
                }
                catch( JSONException e )
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, JSONArray response )
            {
                super.onSuccess( statusCode, headers, response );
            }

            @Override
            public void onSuccess( int statusCode, Header[] headers, String responseString )
            {
                super.onSuccess( statusCode, headers, responseString );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONObject errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   Throwable throwable,
                                   JSONArray errorResponse )
            {
                super.onFailure( statusCode, headers, throwable, errorResponse );
            }

            @Override
            public void onFailure( int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable )
            {
                super.onFailure( statusCode, headers, responseString, throwable );
            }
        };
    }

    private void updateViews()
    {
        tvName.setText( user.getName() );
        tvUserName.setText( user.getDisplayUserName() );
        tvBio.setText( user.getBio() );

        if( user.getHeaderImageUrl().isEmpty() )
        {
            ivHeaderImage.setImageDrawable( getDrawable( R.drawable.nav_bar_gradient ) );
        }
        else
        {
            Glide.with( this )
                 .load( user.getHeaderImageUrl() )
                 .into( ivHeaderImage );
        }

        Glide.with( this )
             .load( user.getProfileImageUrl() )
             .into( ivProfileImage );

        if( user.getLocation().isEmpty() )
        {
            tvLocation.setVisibility( View.GONE );
        }
        else
        {
            tvLocation.setText( user.getLocation() );
        }

        if( user.getUrl().isEmpty() )
        {
            tvWebsite.setVisibility( View.GONE );
        }
        else
        {
            tvWebsite.setText( user.getUrl() );
        }

        tvFollowers.setText( getFormattedFollowingText() );
    }

    private Spanned getFormattedFollowingText()
    {
        return Html.fromHtml( "<b><font color=#000000>" + user.getFollowerCount() + "</font></b>" +
                              "<font color=#8494a3>&nbsp;Following</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                              "<b><font color=#000000>" + user.getFollowingCount() + "</font></b>" +
                              "<font color=#8494a3>&nbsp;Followers</font>" );
    }

    public static Intent newIntent( Context context )
    {
        return new Intent( context, ProfileActivity.class );
    }
}
