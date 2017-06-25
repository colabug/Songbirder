package com.codepath.apps.songbirder.util;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

// TODO: Why does it always show a future time?
public class DateUtil
{
    private static final String TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    @NonNull
    public static String getRelativeTimestamp( String dateToParse )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat( TWITTER_DATE_FORMAT, Locale.ENGLISH );
        dateFormat.setLenient( true );

        String relativeDate = "";
        try
        {
            long dateMillis = dateFormat.parse( dateToParse ).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString( dateMillis,
                                                                System.currentTimeMillis(),
                                                                DateUtils.SECOND_IN_MILLIS )
                                    .toString();
        }
        catch( ParseException e )
        {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
