package com.codepath.apps.songbirder.util;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{
    private static final String TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    public static final int SIXTY_SECONDS = 60;
    public static final int TWENTY_FOUR_HOURS = 24;
    public static final int THIRTY_DAYS = 30;

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

    /**
     * Given a date String of the format given by the Twitter API, returns a display-formatted
     * String representing the relative time difference, e.g. "2m", "6d", "23 May", "1 Jan 14"
     * depending on how great the time difference between now and the given date is.
     * This, as of 2016-06-29, matches the behavior of the official Twitter app.
     */
    public static String getTimeDifference( String rawJsonDate )
    {
        String time = "";
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat( twitterFormat, Locale.ENGLISH );
        format.setLenient( true );
        try
        {
            long diff = ( System.currentTimeMillis() - format.parse( rawJsonDate )
                                                             .getTime() ) / 1000;
            if( diff < 1 )
            {
                time = "now";
            }
            else if( diff < SIXTY_SECONDS )
            {
                time = String.format( Locale.ENGLISH, "%ds", diff );
            }
            else if( diff < SIXTY_SECONDS * SIXTY_SECONDS )
            {
                time = String.format( Locale.ENGLISH, "%dm", diff / SIXTY_SECONDS );
            }
            else if( diff < SIXTY_SECONDS * SIXTY_SECONDS * TWENTY_FOUR_HOURS )
            {
                time = String.format( Locale.ENGLISH, "%dh", diff / ( SIXTY_SECONDS * SIXTY_SECONDS ) );
            }
            else if( diff < SIXTY_SECONDS * SIXTY_SECONDS * TWENTY_FOUR_HOURS * THIRTY_DAYS )
            {
                time = String.format( Locale.ENGLISH, "%dd", diff / ( SIXTY_SECONDS * SIXTY_SECONDS * TWENTY_FOUR_HOURS ) );
            }
            else
            {
                Calendar now = Calendar.getInstance();
                Calendar then = Calendar.getInstance();
                then.setTime( format.parse( rawJsonDate ) );
                if( now.get( Calendar.YEAR ) == then.get( Calendar.YEAR ) )
                {
                    time = String.valueOf( then.get( Calendar.DAY_OF_MONTH ) ) + " "
                            + then.getDisplayName( Calendar.MONTH, Calendar.SHORT, Locale.US );
                }
                else
                {
                    time = String.valueOf( then.get( Calendar.DAY_OF_MONTH ) ) + " "
                            + then.getDisplayName( Calendar.MONTH, Calendar.SHORT, Locale.US )
                            + " " + String.valueOf( then.get( Calendar.YEAR ) - 2000 );
                }
            }
        }
        catch( ParseException e )
        {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * Given a date String of the format given by the Twitter API, returns a display-formatted
     * String of the absolute date of the form "30 Jun 16".
     * This, as of 2016-06-30, matches the behavior of the official Twitter app.
     */
    public static String getTimeStamp( String rawJsonDate )
    {
        String time = "";
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat( twitterFormat, Locale.ENGLISH );
        format.setLenient( true );
        try
        {
            Calendar then = Calendar.getInstance();
            then.setTime( format.parse( rawJsonDate ) );
            Date date = then.getTime();

            SimpleDateFormat format1 = new SimpleDateFormat( "h:mm a \u00b7 dd MMM yy" );

            time = format1.format( date );

        }
        catch( ParseException e )
        {
            e.printStackTrace();
        }
        return time;
    }
}
