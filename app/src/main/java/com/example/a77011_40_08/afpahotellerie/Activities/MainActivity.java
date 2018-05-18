package com.example.a77011_40_08.afpahotellerie.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Floor;
import com.example.a77011_40_08.afpahotellerie.Models.Floors;
import com.example.a77011_40_08.afpahotellerie.Models.Job;
import com.example.a77011_40_08.afpahotellerie.Models.Jobs;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.Models.Users;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.App;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    Context context;
    SWInterface swInterface;
    Jobs jobs;
    RoomStatuts roomStatuts;
    Floors floors;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        app = (App) getApplication();
        swInterface = RetrofitApi.getInterface();
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
                    Observable<Push> getFloorsObservable
                            = s.getFloors(Functions.getAuth()).subscribeOn(Schedulers.io());

                    return Observable.concatArray(jobObservable, RoomsStatusObservable,
                            getFloorsObservable);
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
                Log.e(Constants._TAG_LOG,"STATUS 0:"+roomStatuts.get(0).getAbbreviation());
                app.setRoomStatuts(roomStatuts);
            } else if (push.getType().equals("floors")) {
                floors=gson.fromJson(push.getData(),Floors.class);
                app.setFloors(floors);
            }

            if(jobs != null && roomStatuts!=null && floors!=null)
            {
                goToLogin();
            }


        }


    }

    private void handleError(Throwable t) {
        Log.e("Observer", "" + t.toString());
        Toast.makeText(this, "ERROR IN GETTING DATA",
                Toast.LENGTH_LONG).show();
    }

}
