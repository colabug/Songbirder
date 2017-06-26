package com.codepath.apps.songbirder.api;

import android.content.Context;

import com.codepath.apps.songbirder.R;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/scribejava/scribejava/tree/master/scribejava-apis/src/main/java/com/github/scribejava/apis
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient
{
	// Basic authentication and URL requirements
    private static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
	private static final String REST_URL = "https://api.twitter.com/1.1";
	private static final String REST_CONSUMER_KEY = "R43hRq4NN8MOehGhg2sQSMiJJ";
	private static final String REST_CONSUMER_SECRET = "gyFIe3BLKpVBbinptzp6ttutWvj8b1b2N9cVEwg9InyXnso5vW";

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	private static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	private static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	// Timeline call and parameters
	private static final String TIMELINE_ENDPOINT = "statuses/home_timeline.json";
	private static final String PARAM_COUNT = "count";
	private static final int VALUE_NUMBER_OF_TWEETS = 25;
	private static final String PARAM_SINCE = "since";
	private static final int VALUE_SINCE = 1;

	// Tweet reply and creation call and parameters
	private static final String ENDPOINT_STATUS = "statuses/update.json";
	private static final String PARAM_STATUS = "status";
	private static final String PARAM_REPLY_ID = "in_reply_to_status_id";

    public TwitterClient( Context context )
    {
        super( context,
               REST_API_INSTANCE,
               REST_URL,
               REST_CONSUMER_KEY,
               REST_CONSUMER_SECRET,
               String.format( REST_CALLBACK_URL_TEMPLATE,
                              context.getString( R.string.intent_host ),
                              context.getString( R.string.intent_scheme ),
                              context.getPackageName(),
                              FALLBACK_URL ) );
    }

    public void getHomeTimeline( AsyncHttpResponseHandler handler )
    {
        String url = getApiUrl( TIMELINE_ENDPOINT );

        RequestParams params = new RequestParams();
        params.put( PARAM_COUNT, VALUE_NUMBER_OF_TWEETS );
        params.put( PARAM_SINCE, VALUE_SINCE );

        client.get( url, params, handler );
    }

    public void postTweet( String status, long replyId, JsonHttpResponseHandler handler )
    {
        String url = getApiUrl( ENDPOINT_STATUS );

        RequestParams params = new RequestParams();
        params.put( PARAM_STATUS, status );

        if( replyId > -1 )
        {
            params.put( PARAM_REPLY_ID, replyId );
        }

        client.post( url, params, handler );
    }
}
