package com.ekc.hangoutsprototype;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

/**
 * Hangouts meets Inbox
 * Erick Chang - 02/25/15
 * Updated material view for Hangouts with minimal implementation. Intended to carry over some
 * UI layout/design from Inbox into Hangouts to demonstrate "what Hangouts should look like going
 * forward".
 */
public class MainActivity extends ActionBarActivity implements ComposeFragment.OnFragmentInteractionListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new MessageSetFragment(),"messageSetFragment")
                .commit();

        // TODO: implement a drawer
//        mNavigationDrawerFragment = (NavigationDrawerFragment)
//                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
//        mTitle = getTitle();
//
//        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));
    }



    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO: implement callback
    }
}
