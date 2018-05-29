package com.example.a77011_40_08.afpahotellerie.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.fragments.AssignRoomFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.RoomsToCleanFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.AssignedStaffFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.HomeFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.StateRoomsFragment;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Session;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    Fragment currentFragment;
    FragmentManager fragmentManager;
    TextView txtHeaderName;
    ImageView imgProfilePics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        Map<Boolean, String> myMap = new HashMap<Boolean, String>();
        myMap.put(true, "1");
        myMap.put(false, "2");
        myMap.put(false, "3");
        myMap.put(false, "4");
        myMap.put(false, "5");
        myMap = new HashMap<Boolean, String>();
        myMap.put(false, "1");
        myMap.put(true, "2");
        myMap.put(false, "3");
        myMap.put(false, "4");
        myMap.put(false, "5");
        myMap = new HashMap<Boolean, String>();
        myMap.put(false, "1");
        myMap.put(false, "2");
        myMap.put(true, "3");
        myMap.put(false, "4");
        myMap.put(false, "5");
        myMap = new HashMap<Boolean, String>();
        myMap.put(false, "1");
        myMap.put(false, "2");
        myMap.put(false, "3");
        myMap.put(true, "4");
        myMap.put(false, "5");
        myMap = new HashMap<Boolean, String>();
        myMap.put(false, "1");
        myMap.put(false, "2");
        myMap.put(false, "3");
        myMap.put(false, "4");
        myMap.put(true, "5");

        fragmentManager = getFragmentManager();
        changeFragment(Constants._FRAG_HOME, null);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtHeaderName = navigationView.getHeaderView(0).findViewById(R.id.txtHeader_name);
        imgProfilePics = navigationView.getHeaderView(0).findViewById(R.id.imgProfilePic);
        userHasChange(Session.getMyUser());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_login) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            clearFragments();
            changeFragment(Constants._FRAG_HOME, null);
        } else if (id == R.id.nav_chambre) {
            changeFragment(Constants.FRAG_ROOMS_CLEAN, null);
        } else if (id == R.id.nav_affectation) {
            changeFragment(Constants.FRAG_ASSIGNED_STAFF, null);
        } else if (id == R.id.nav_stateRooms) {
            changeFragment(Constants.FRAG_SATEROOMS, null);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void clearFragments() {
        int end = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i <= end; i++) {
            //Log.e(Constants._TAG_LOG,i+"/"+end);
            fragmentManager.popBackStackImmediate();
        }
        //Log.e(Constants._TAG_LOG,"Finish "+fragmentManager.getBackStackEntryCount());
    }

    public void changeFragment(int code, Bundle params) {
        Fragment frag = null;
        switch (code) {
            case Constants._FRAG_HOME:
                frag = new HomeFragment();
                break;
            case Constants.FRAG_ROOMS_CLEAN:
                frag = new RoomsToCleanFragment();
                break;
            case Constants.FRAG_ASSIGNED_STAFF:
                frag = new AssignedStaffFragment();
                break;
            case Constants.FRAG_SATEROOMS:
                frag = new StateRoomsFragment();
                break;
            case Constants.FRAG_ASSIGNED_ROOM:
                frag = AssignRoomFragment.newInstance(params);
                break;

            default:
                Log.e("[ERROR]", "changeFragment: code invalide " + code);
                break;
        }

        if (frag != null) {
            loadFragment(frag);
        }

    }

    private void loadFragment(Fragment fragment) {
        currentFragment = fragment;
        int backStackCount = fragmentManager.getBackStackEntryCount();
        String tag = "Frag" + backStackCount;

        fragmentManager.beginTransaction()
                .replace(R.id.frtHome, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void userHasChange(User user) {
        txtHeaderName.setText(user.getFullName());
    }

    public Fragment getLastFragment() {
        return currentFragment;
    }


}