package com.example.a77011_40_08.afpahotellerie.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(Constants._TAG_LOG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Functions.addPreferenceString(getApplicationContext(),"token",refreshedToken);

        SWInterface swInterface = RetrofitApi.getInterface();
        String idDevice = Functions.getPreferenceString(getApplicationContext(),"idDevice");

        if(idDevice.equals("")){
            Call<Push> call = swInterface.addDevice(Functions.getAuth(),refreshedToken);
            call.enqueue(new Callback<Push>() {
                @Override
                public void onResponse(Call<Push> call, Response<Push> response) {
                    if(response.isSuccessful()){
                        Push push = response.body();
                        if(push.getStatus() == 1){
                            String newId = push.getData();
                            Functions.addPreferenceString(getApplicationContext(),"idDevice",newId);
                            Log.d(Constants._TAG_LOG,"New idDevice: "+newId);
                        }else{
                            Log.e(Constants._TAG_LOG,"Error: addDevice.ph | "+push.getData());
                        }
                    }else{
                        Log.e(Constants._TAG_LOG,"Error: "+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Push> call, Throwable t) {
                    Log.e(Constants._TAG_LOG,"Error: "+t.getMessage());
                }
            });
        }else{
            Call<Push> call = swInterface.setDevice(Functions.getAuth(), Integer.parseInt(idDevice),refreshedToken);
            call.enqueue(new Callback<Push>() {
                @Override
                public void onResponse(Call<Push> call, Response<Push> response) {
                    if(response.isSuccessful()){
                        Push push = response.body();
                        if(push.getStatus() == 1){
                            Log.d(Constants._TAG_LOG,"Token add to DB: "+refreshedToken);
                        }else{
                            Log.e(Constants._TAG_LOG,"Error: addDevice.ph | "+push.getData());
                        }
                    }else{
                        Log.e(Constants._TAG_LOG,"Error: "+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Push> call, Throwable t) {
                    Log.e(Constants._TAG_LOG,"Error: "+t.getMessage());
                }
            });
        }



    }
}
