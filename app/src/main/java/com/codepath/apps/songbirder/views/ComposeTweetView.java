package com.codepath.apps.songbirder.views;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.songbirder.R;
import com.codepath.apps.songbirder.listeners.ComposeTweetButtonListener;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Show a your tweet is posting thing for detail view
public class ComposeTweetView extends RelativeLayout implements TextView.OnEditorActionListener
{
    private static final int MAX_TWEET_LENGTH = 140;
    private static final int WARNING_CHARACTER_COUNT = 10;

    @BindView(R.id.etEnterTweet) EditText etEnterTweet;
    @BindView(R.id.tvCounter) TextView tvCounter;
    @BindView(R.id.btnTweet) Button btnTweet;

    @BindColor( R.color.red ) int red;
    @BindColor( R.color.dark_orange) int darkOrange;
    @BindColor( android.R.color.black ) int black;

    private ComposeTweetButtonListener listener;

    public ComposeTweetView( Context context )
    {
        super( context );
        init();
    }

    public ComposeTweetView( Context context, AttributeSet attrs )
    {
        super( context, attrs );
        init();
    }

    public ComposeTweetView( Context context, AttributeSet attrs, int defStyleAttr )
    {
        super( context, attrs, defStyleAttr );
        init();
    }

    private void init()
    {
        inflate( getContext(), R.layout.view_compose_tweet, this );

        ButterKnife.bind( this );

        configureKeyboard();
    }

    public void setTweetButtonListener( ComposeTweetButtonListener listener )
    {
        this.listener = listener;
    }

    public void setReplyUsername( String tweetText )
    {
        etEnterTweet.setText( String.format( "%s ", tweetText ) );
        int position = etEnterTweet.length();
        Selection.setSelection( etEnterTweet.getText(), position);
    }

    public void clear()
    {
        etEnterTweet.setText( "" );
    }

    private void configureKeyboard()
    {
        etEnterTweet.addTextChangedListener( getTextChangedListener() );
        etEnterTweet.setOnEditorActionListener( this );
    }

    private TextWatcher getTextChangedListener()
    {
        return new TextWatcher()
        {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after )
            {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count )
            {
                updateCounter();
            }

            @Override
            public void afterTextChanged( Editable s )
            {

            }
        };
    }

    private void updateCounter()
    {
        int currentLength = etEnterTweet.getText().length();
        int valueToDisplay = MAX_TWEET_LENGTH - currentLength;

        tvCounter.setTextColor( getTextColor( valueToDisplay ) );
        tvCounter.setText( String.valueOf( valueToDisplay ) );
    }

    private int getTextColor( int valueToDisplay )
    {
        if( valueToDisplay < 0 )
        {
            return red;
        }
        else if( valueToDisplay <= WARNING_CHARACTER_COUNT )
        {
            return darkOrange;
        }
        else
        {
            return black;
        }
    }

    @Override
    public boolean onEditorAction( TextView view, int actionId, KeyEvent event )
    {
        if( EditorInfo.IME_ACTION_DONE == actionId )
        {
            listener.onEditorDone( etEnterTweet.getText().toString() );

            return true;
        }

        return false;
    }

    @OnClick(R.id.btnTweet)
    void submitTweet()
    {
        listener.onTweetButtonClick( etEnterTweet.getText().toString() );
    }
}
