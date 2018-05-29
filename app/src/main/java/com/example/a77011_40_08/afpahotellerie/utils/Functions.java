package com.example.a77011_40_08.afpahotellerie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class Functions {

    public static void emulateTime(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void addPreferenceString(Context context, String key, String value) {
        addPreferenceString(context, key, value, true);
    }

    public static void addPreferenceString(Context context, String key, String value, boolean allowUpdate) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_PRIVATE);

        //Ouvre le fichier en mode édition
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //On enregistre les données dans le fichier
        if (!sharedPreferences.contains(key) || allowUpdate) {
            editor.putString(key, value);
        }
        //On referme le fichier
        editor.commit();
    }

    public static String getPreferenceString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE, MODE_PRIVATE);

        String value = "";
        if (sharedPreferences.contains(key)) {
            value = sharedPreferences.getString(key, "");
        }

        return value;
    }

    public static int getResourceId(Context context, String pVariableName, String pResourcename) {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean isConnectionAvailable(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


    public static String getAuth() {

        String htpasswd = null;
        try {
            htpasswd = Base64.encodeToString(("gpr40Hotellerie:@=2018G40").getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + htpasswd;
    }

    public static String today() {
        Date today;
        today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format.format(today);
    }

    public static int getMyIdDevice(Context context){
        String id = getPreferenceString(context,"idDevice");
        return Integer.parseInt(id);
    }

    public static String getMyToken(Context context){
        return getPreferenceString(context,"token");
    }
}
