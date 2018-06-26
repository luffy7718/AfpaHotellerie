package com.example.a77011_40_08.afpahotellerie.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.models.ChatMessage;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.parceler.Parcels;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private LocalBroadcastManager broadcaster;

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(Constants._TAG_LOG, "From: " + remoteMessage.getFrom());
        Log.e(Constants._TAG_LOG, remoteMessage.toString());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(Constants._TAG_LOG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            Log.d(Constants._TAG_LOG, data.get("type"));
            switch (data.get("type")) {
                case "notification":
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(data.get("body"), JsonObject.class);
                    if (json.has("title") && json.has("text")) {
                        Functions.createNotification(getApplicationContext(), json.get("title")
                                .getAsString(), json.get("text").getAsString());
                    } else {
                        Log.e(Constants._TAG_LOG, "Données insuffisantes pour la notification: " + json);
                    }
                    if (json.has("update")) {
                        int idFragment = Integer.parseInt(json.get("update").getAsString());
                        broadcastToActivities(idFragment);
                    } else {
                        Log.e(Constants._TAG_LOG, "Données insuffisantes pour l'update: " + json);
                    }
                    if (json.has("topic")) {
                        String topic = json.get("topic").getAsString();
                        broadcastTopicToActivities(topic);
                        Log.e(Constants._TAG_LOG, "Topic: " + json);
                    } else {
                        Log.e(Constants._TAG_LOG, "Données insuffisantes pour le topic: " + json);
                    }
                    break;
                case "message":
                    Toast.makeText(getApplicationContext(), data.get("body"), Toast.LENGTH_LONG)
                            .show();
                    gson = new Gson();
                    JsonObject jsonMessage = gson.fromJson(data.get("body"), JsonObject.class);
                    if (jsonMessage.has("title") && jsonMessage.has("text")) {
                        Functions.createNotification(getApplicationContext(), jsonMessage.get
                                ("title")
                                .getAsString(), jsonMessage.get("text").getAsString());


                    } else {
                        Log.e(Constants._TAG_LOG, "Body mal formé: " + jsonMessage);
                    }
                    break;
                case "alert":
                    Toast.makeText(getApplicationContext(), data.get("body"), Toast.LENGTH_LONG)
                            .show();
                    break;
                default:
                    Log.e(Constants._TAG_LOG, "Mauvais type: " + data.get("type"));
                    break;
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(Constants._TAG_LOG, "Message Notification Body: " + remoteMessage
                    .getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void broadcastToActivities(int idFragment) {
        Intent intent = new Intent("MessageReceive");
        intent.putExtra("idFragment", idFragment);
        broadcaster.sendBroadcast(intent);
    }

    public void broadcastTopicToActivities(String topic) {
        Intent intent = new Intent("TopicReceive");
        intent.putExtra("topic", topic);
        broadcaster.sendBroadcast(intent);
    }
}
