package com.ekc.hangoutsprototype;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * The main Messages Fragment window. Creates view that is populated with RecyclerView MessageSets.
 * Each MessageSet also hosts a RecyclerView of MessageSnippets.
 *
 * A set is grouped by date, determined by the DATES array in Message - the dummy grouping is just
 * "Today, Yesterday, This month."
 *
 * The set could have been hardcoded but Inbox "sets" are dynamic - it goes much further beyond
 * "This month" to every month in the past, so this implementation allows extending dummy Message
 * data to that capacity just by adding more months in the DATES array in the Messages class.
 */
public class MessageSetFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton mComposeButton;
    private FragmentManager mFragmentManager;
    private Toolbar mToolbar;
    private ActionBarActivity mContext;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    public static MessageSetFragment newInstance(int sectionNumber) {
        MessageSetFragment fragment = new MessageSetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageSetFragment() {
        // TODO: remove shadow under action bar for API 21 (and add elevation parameter instead?)

        // TODO: compose button may need to be TransitionDrawable. see Inbox UI
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mFragmentManager = getFragmentManager();

        View rootView = inflater.inflate(R.layout.fragment_message_set, container, false);
        mDrawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        // Setup toolbar
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        setupToolbar();
        setupDrawerToggle();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.message_set_container);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MessageSetAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // TODO: compose has no functionality beyond layout, would be nice to open
        // a list of contacts to scroll through
        mComposeButton = (ImageButton) rootView.findViewById(R.id.compose_button);
        mComposeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "Compose!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getActivity(), str, duration);
                toast.show();

                mFragmentManager.beginTransaction()
                        .add(R.id.container, new ComposeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    public void setupToolbar() {
        mContext = (ActionBarActivity) getActivity();
        mContext.setSupportActionBar(mToolbar);
        ActionBar actionBar = mContext.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setCustomView(R.layout.action_bar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        mToolbar.inflateMenu(R.menu.main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.search:
                // TODO: add search, ideally one that looks for html links too
                CharSequence text = "Executing phase shift...";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(mContext, text, duration);
                toast.show();
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }


    public void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(mContext,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

}
