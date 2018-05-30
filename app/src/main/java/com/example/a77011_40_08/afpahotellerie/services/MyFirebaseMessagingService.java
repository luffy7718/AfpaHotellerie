package com.example.a77011_40_08.afpahotellerie.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(Constants._TAG_LOG, "From: " + remoteMessage.getFrom());
        Log.d(Constants._TAG_LOG, remoteMessage.toString());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(Constants._TAG_LOG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            Log.d(Constants._TAG_LOG,data.get("type"));
            switch (data.get("type")){
                case "notification":
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(data.get("body"),JsonObject.class);
                    if(json.has("title") && json.has("text")){
                        Functions.createNotification(getApplicationContext(),json.get("title").getAsString(),json.get("text").getAsString());
                    }else{
                        Log.e(Constants._TAG_LOG,"Body mal formé: "+json);
                    }
                    break;
                case "message":
                    Toast.makeText(getApplicationContext(),data.get("body"),Toast.LENGTH_LONG).show();
                    break;
                case "alert":
                    Toast.makeText(getApplicationContext(),data.get("body"),Toast.LENGTH_LONG).show();
                    break;
                default:
                    Log.e(Constants._TAG_LOG,"Mauvais type: "+data.get("type"));
                    break;
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(Constants._TAG_LOG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
