package com.example.a77011_40_08.afpahotellerie.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Floors;
import com.example.a77011_40_08.afpahotellerie.models.Jobs;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.models.RoomsTypes;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.google.gson.Gson;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Context context;
    SWInterface swInterface;
    Jobs jobs;
    RoomStatuts roomStatuts;
    Floors floors;
    App app;
    RoomsTypes roomsTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        app = (App) getApplication();
        swInterface = RetrofitApi.getInterface();
        checkVersion();
        loadColors();
        //getData();

    }

    private void goToLogin() {


        Intent intent;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(getApplicationContext(), PermissionActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }

        startActivity(intent);
        finish();


    }

    private void checkVersion() {
        Call<Integer> call = swInterface.getDBVersion(Functions.getAuth());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful()) {
                    int currentVersion = response.body();
                    getData(currentVersion);
                }else{
                    Log.e(Constants._TAG_LOG,"checkVersion: "+response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Log.e(Constants._TAG_LOG,"Erreur: checkVersion |"+t.getMessage());
            }
        });
    }

    private void getData(int dbVersion) {
        String save = Functions.getPreferenceString(this, "localVersion");
        if (!save.equals("")) {
            int localVersion = Integer.parseInt(save);
            Log.e(Constants._TAG_LOG, "DBVersion: " + dbVersion + " | LocalVersion: " + localVersion);
            if (localVersion == dbVersion) {
                getDataFromSP();
            } else {
                Functions.addPreferenceString(this, "localVersion", "" + dbVersion);
                getDataFromDB();
            }
        } else {
            Functions.addPreferenceString(this, "localVersion", "" + dbVersion);
            getDataFromDB();
        }
    }

    private void getDataFromSP() {//DB:Shared Preferences
        Log.e(Constants._TAG_LOG, "getDataFromSP");
        String jsonJobs = Functions.getPreferenceString(this, "jobsTable");
        String jsonRoomsStatus = Functions.getPreferenceString(this, "roomsStatusTable");
        String jsonFloors = Functions.getPreferenceString(this, "floorsTable");
        String jsonRoomsTypes = Functions.getPreferenceString(this, "roomsTypesTable");
        if (jsonJobs.equals("") || jsonRoomsStatus.equals("") || jsonFloors.equals("") || jsonRoomsTypes.equals("")) {
            Log.e(Constants._TAG_LOG, "Missing data from Shared Preferences");
            getDataFromDB();
        } else {
            Gson gson = new Gson();
            jobs = gson.fromJson(jsonJobs, Jobs.class);
            App.setJobs(jobs);
            roomStatuts = gson.fromJson(jsonRoomsStatus, RoomStatuts.class);
            App.setRoomStatuts(roomStatuts);
            floors = gson.fromJson(jsonFloors, Floors.class);
            App.setFloors(floors);
            roomsTypes = gson.fromJson(jsonRoomsTypes, RoomsTypes.class);
            App.setRoomsTypes(roomsTypes);
            Log.e(Constants._TAG_LOG, "APP loaded from SP.");
            goToLogin();
        }

    }


    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    private void getDataFromDB() {//DB:Data Base
        Log.e(Constants._TAG_LOG, "getDataFromDB");
        Observable.just(swInterface).subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    Observable<Push> jobObservable
                            = s.getJobs(Functions.getAuth()).subscribeOn(Schedulers.io());

                    Observable<Push> RoomsStatusObservable
                            = s.getRoomsStatus(Functions.getAuth()).subscribeOn(Schedulers.io());
                    Observable<Push> FloorsObservable
                            = s.getFloors(Functions.getAuth()).subscribeOn(Schedulers.io());
                    Observable<Push> RoomsTypesObservable
                            = s.getRoomsTypes(Functions.getAuth()).subscribeOn(Schedulers.io());
                    return Observable.concatArray(jobObservable, RoomsStatusObservable,
                            FloorsObservable,RoomsTypesObservable);
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults,
                this::handleError);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void handleResults(Object object) {
        if (object instanceof Push) {
            Push push = (Push) object;
            Log.e(Constants._TAG_LOG, push.toString());
            Gson gson=new Gson();

            switch (push.getType()) {
                case "getJobs":
                    jobs = gson.fromJson(push.getData(), Jobs.class);
                    App.setJobs(jobs);
                    Functions.addPreferenceString(this, "jobsTable", push.getData());
                    break;
                case "rooms_status":
                    roomStatuts = gson.fromJson(push.getData(), RoomStatuts.class);
                    App.setRoomStatuts(roomStatuts);
                    Functions.addPreferenceString(this, "roomsStatusTable", push.getData());
                    break;
                case "floors":
                    floors = gson.fromJson(push.getData(), Floors.class);
                    App.setFloors(floors);
                    Functions.addPreferenceString(this, "floorsTable", push.getData());
                    break;
                case "rooms_types":
                    roomsTypes = gson.fromJson(push.getData(), RoomsTypes.class);
                    App.setRoomsTypes(roomsTypes);
                    Functions.addPreferenceString(this, "roomsTypesTable", push.getData());
                    break;
            }

            //Log.e(Constants._TAG_LOG, "Jobs:"+String.valueOf(getJobs!=null)+" | RoomStatus:"+String.valueOf(roomStatuts!=null)+" | getFloors:"+String.valueOf(floors!=null)+" | getRoomsTypes:"+String.valueOf(roomsTypes!=null));
            if(jobs != null && roomStatuts!=null && floors!=null && roomsTypes!=null)
            {
                Log.e(Constants._TAG_LOG,"APP loaded");
                goToLogin();
            }


        }


    }

    private void handleError(Throwable t) {
        Log.e("Observer", "" + t.toString());
        Toast.makeText(this, "ERROR IN GETTING DATA",
                Toast.LENGTH_LONG).show();
    }

    private void loadColors(){
        HashMap<String, Integer> colors = new HashMap<>();
        colors.put("LP",getResources().getColor(R.color.colorStatusLP));
        colors.put("LS",getResources().getColor(R.color.colorStatusLS));
        colors.put("OP",getResources().getColor(R.color.colorStatusOP));
        colors.put("OS",getResources().getColor(R.color.colorStatusOS));
        colors.put("DA",getResources().getColor(R.color.colorStatusDA));

        App.setColors(colors);
    }

}
