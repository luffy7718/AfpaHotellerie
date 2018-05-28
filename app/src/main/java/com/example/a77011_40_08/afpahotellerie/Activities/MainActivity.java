package com.example.a77011_40_08.afpahotellerie.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Floors;
import com.example.a77011_40_08.afpahotellerie.Models.Jobs;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.Models.RoomsTypes;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.App;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.google.gson.Gson;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        loadColors();
        getData();
       goToLogin();
    }

    private void goToLogin() {


        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(getApplicationContext(), PermissionActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }


        if (intent != null) {
            startActivity(intent);
            finish();
        }

    }


    @SuppressLint("CheckResult")
    private void getData() {
        //first it creates an observable which emits retrofit service class
        //to leave current main thread, we need to use subscribeOn which subscribes the
        // observable on computation thread
        //flatMap is used to apply function on the item emitted by previous observable
        //function makes two rest service calls using the give retrofit object for defined api
        // interface
        //these two calls run parallel that is why subscribeOn is used on each of them
        //since these two api call return same object, they are joined using concatArray operator
        //finally consumer observes on android main thread
        Observable.just(swInterface).subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    Observable<Push> jobObservable
                            = s.jobs(Functions.getAuth()).subscribeOn(Schedulers.io());

                    Observable<Push> RoomsStatusObservable
                            = s.RoomsStatus(Functions.getAuth()).subscribeOn(Schedulers.io());
                    Observable<Push> FloorsObservable
                            = s.Floors(Functions.getAuth()).subscribeOn(Schedulers.io());
                    Observable<Push> RoomsTypesObservable
                            = s.RoomsTypes(Functions.getAuth()).subscribeOn(Schedulers.io());
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

            if (push.getType().equals("jobs")) {

                jobs=gson.fromJson(push.getData(),Jobs.class);
                app.setJobs(jobs);

            } else if (push.getType().equals("rooms_status")) {
                roomStatuts=gson.fromJson(push.getData(),RoomStatuts.class);
                app.setRoomStatuts(roomStatuts);
            } else if (push.getType().equals("floors")) {
                floors=gson.fromJson(push.getData(),Floors.class);
                app.setFloors(floors);
            }else if (push.getType().equals("rooms_types")) {
                roomsTypes = gson.fromJson(push.getData(), RoomsTypes.class);
                app.setRoomsTypes(roomsTypes);
            }

            //Log.e(Constants._TAG_LOG, "Jobs:"+String.valueOf(jobs!=null)+" | RoomStatus:"+String.valueOf(roomStatuts!=null)+" | Floors:"+String.valueOf(floors!=null)+" | RoomsTypes:"+String.valueOf(roomsTypes!=null));
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
