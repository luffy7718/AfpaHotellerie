package com.example.a77011_40_08.afpahotellerie.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.fragments.AssignRoomFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.ChatFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.ChatPrivateMessagesFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.RoomsToCleanFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.AssignStaffFragment;
import com.example.a77011_40_08.afpahotellerie.R;

import com.example.a77011_40_08.afpahotellerie.fragments.FilterDialogFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.HomeFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.RoomDetailDialogFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.StateRoomsFragment;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Job;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int defaultFragment = -1;

    Context context;
    Fragment currentFragment;
    FragmentManager fragmentManager;
    TextView txtHeaderName;
    TextView txtHeaderJob;
    ImageView imgProfilePics;
    SWInterface swInterface;

    //MyFirebaseMessagingService myFirebaseMessagingService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;
        swInterface = RetrofitApi.getInterface();


        fragmentManager = getFragmentManager();
        changeFragment(Constants._FRAG_HOME,null);

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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        adapteDrawer(navigationView);
        txtHeaderName = navigationView.getHeaderView(0).findViewById(R.id.txtHeader_name);
        txtHeaderJob = navigationView.getHeaderView(0).findViewById(R.id.txtHeader_job);
        imgProfilePics = navigationView.getHeaderView(0).findViewById(R.id.imgProfilePic);
        userHasChange(Session.getMyUser());

    }

    /*@Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(context).registerReceiver((mMessageReceiver),
                new IntentFilter("MessageReceive")
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            int backStackCount = fragmentManager.getBackStackEntryCount();
            if (backStackCount == 0) {
                showDisconnectAppliDialog();
            }
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
            showDisconnectDialog();
        }  else if (id == R.id.action_message) {
       changeFragment(Constants.FRAG_CHAT,null);
    }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home){
            clearFragments();
            changeFragment(Constants._FRAG_HOME, null);
        } else if (id == R.id.nav_room_to_clean) {
            changeFragment(Constants.FRAG_ROOMS_CLEAN, null);
        } else if (id == R.id.nav_affectation) {
            changeFragment(Constants.FRAG_ASSIGNED_STAFF, null);
        } else if (id == R.id.nav_stateRooms) {
            changeFragment(Constants.FRAG_SATEROOMS, null);
        } else if (id == R.id.nav_unaffectation) {
            changeFragment(Constants.FRAG_ASSIGNED_ROOM, null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDisconnectAppliDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Quitter l'application ?");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "non", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                changeFragment(Constants._FRAG_HOME, null);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
                System.exit(0);

            }
        });
        alertDialog.show();
    }

    private void showDisconnectDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Déconnexion de votre compte ?");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "non", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                logout();
            }
        });
        alertDialog.show();
    }

    private void clearFragments() {
        int end = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i<=end;i++){
            //Log.e(Constants._TAG_LOG,i+"/"+end);
            fragmentManager.popBackStackImmediate();
        }
        //Log.e(Constants._TAG_LOG,"Finish "+fragmentManager.getBackStackEntryCount());
    }
    public void changeFragment(int code, Bundle params){
        Boolean needToSubscribe = false;
        Fragment frag = null;
        switch (code){
            case Constants._FRAG_HOME:
                //frag = new HomeFragment();
                if(Session.getMyUser().getIdJob() != 5){
                    frag = new StateRoomsFragment();
                    needToSubscribe = true;
                }else{
                    frag = new RoomsToCleanFragment();
                }
                break;
            case Constants.FRAG_ROOMS_CLEAN:
                frag = new RoomsToCleanFragment();
                break;
            case Constants.FRAG_ASSIGNED_STAFF:
                frag = new AssignStaffFragment();
                break;
            case Constants.FRAG_SATEROOMS:
                frag = new StateRoomsFragment();
                needToSubscribe = true;
                break;
            case Constants.FRAG_ASSIGNED_ROOM:
                frag = AssignRoomFragment.newInstance(params);
                needToSubscribe = true;
                break;
            case Constants.FRAG_CHAT:
                frag =  new ChatFragment();
                break;
            case Constants.FRAG_CHAT_PRIVATE:
                frag =  new ChatPrivateMessagesFragment();
                break;
            default:
                Log.e("[ERROR]","changeFragment: code invalide "+code);
                break;
        }

        if (needToSubscribe) {
            FirebaseMessaging.getInstance().subscribeToTopic("roomState");
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("roomState");
        }

        if(frag !=null){
            loadFragment(frag);
        }

    }

    private void loadFragment(Fragment fragment){
        currentFragment = fragment;
        int backStackCount = fragmentManager.getBackStackEntryCount();
        String tag = "Frag" + backStackCount;

        fragmentManager.beginTransaction()
                .replace(R.id.frtHome,fragment,tag)
                .addToBackStack(tag)
                .commit();
    }
    public void userHasChange(User user){
        txtHeaderName.setText(user.getFullName());

        String strJob = "";
        for(Job job : App.getJobs()) {
            if(job.getIdJob() == user.getIdJob()) {
                strJob = job.getTitle();
            }
        }
        txtHeaderJob.setText(strJob);
    }

    public Fragment getLastFragment(){
        return currentFragment;
    }

    private void logout() {
        Call<Push> call = swInterface.logout(Functions.getAuth(), Session.getMyUser().getIdStaff());
        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, push.getData(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(Constants._TAG_LOG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }

    private void adapteDrawer(NavigationView navigationView) {
        HashMap<Integer, Boolean> access = Session.getMyAccess();
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_home).setVisible(access.get(Constants._FRAG_HOME));
        menu.findItem(R.id.nav_room_to_clean).setVisible(access.get(Constants.FRAG_ROOMS_CLEAN));
        menu.findItem(R.id.nav_affectation).setVisible(access.get(Constants.FRAG_ASSIGNED_STAFF));
        menu.findItem(R.id.nav_stateRooms).setVisible(access.get(Constants.FRAG_SATEROOMS));
        menu.findItem(R.id.nav_unaffectation).setVisible(access.get(Constants.FRAG_ASSIGNED_ROOM));

    }

    /*private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int idFrom = intent.getIntExtra("idFragment",-1);
            if(idFrom != -1){
                Log.e(Constants._TAG_LOG, "HomeActivity: " + idFrom);
            }else{
                Log.e(Constants._TAG_LOG, "HomeActivity: pas d'idFrom");
            }
        }
    };*/

    public void showRoomDetails(Room room,  RoomStatut roomStatut, User staff){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        RoomDetailDialogFragment detailDialog = RoomDetailDialogFragment.newInstance();
        detailDialog.setDetailRoom(room, roomStatut, staff);
        detailDialog.show(ft, "TAG detail");
    }

    public void showFilterDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FilterDialogFragment filterDialog = FilterDialogFragment.newInstance();
        //detailDialog.setDetailRoom(room, roomStatut, staff);
        filterDialog.show(ft, "TAG detail");
    }
}
