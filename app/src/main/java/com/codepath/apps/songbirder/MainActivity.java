package com.codepath.apps.songbirder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.vpTwitterPages) ViewPager vpTwitterPages;

    List<WeakReference<Fragment>> fragments = new ArrayList<>();

    public static Intent newIntent( Context context )
    {
        return new Intent( context, MainActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_timeline );
        ButterKnife.bind( this );

        setSupportActionBar( toolbar );
        configureTabs();

        vpTwitterPages.setAdapter( new PagerAdapter( getSupportFragmentManager() ) );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );

        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close );
        drawer.setDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    private void configureTabs()
    {
        TabLayout tabLayout = (TabLayout) findViewById( R.id.discovery_tabs );
        vpTwitterPages.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        tabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener( vpTwitterPages ) );
    }

    // TODO: Get the user's profile image URL, send in here
    @OnClick(R.id.fab)
    void showComposeDialog()
    {
        Fragment currentFragment = getCurrentFragment();
        if( currentFragment != null && currentFragment instanceof BaseFragment )
        {
            ( (BaseFragment) currentFragment ).showDialog();
        }
    }

    @Override
    public void onAttachFragment( Fragment fragment )
    {
        fragments.add( new WeakReference( fragment ) );
    }

    public List<Fragment> getActiveFragments()
    {
        ArrayList<Fragment> visibleFragments = new ArrayList<>();
        for( WeakReference<Fragment> fragment : fragments )
        {
            Fragment currentFragment = fragment.get();
            if( currentFragment != null )
            {
                if( currentFragment.isVisible() )
                {
                    visibleFragments.add( currentFragment );
                }
            }
        }
        return visibleFragments;
    }

    private Fragment getCurrentFragment()
    {
        int position = vpTwitterPages.getCurrentItem();

        List<Fragment> fragments = getActiveFragments();
        for( Fragment current : fragments )
        {
            if( ( (BaseFragment) current ).getPosition() == position )
            {
                return current;
            }
        }

        return null;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if( drawer.isDrawerOpen( GravityCompat.START ) )
        {
            drawer.closeDrawer( GravityCompat.START );
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item )
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if( id == R.id.nav_profile )
        {

        }
        else if( id == R.id.nav_lists )
        {

        }
        else if( id == R.id.nav_moments )
        {

        }
        else if( id == R.id.nav_highlights )
        {

        }
        else if( id == R.id.nav_settings )
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );

        return true;
    }
}
