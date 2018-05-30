package com.example.a77011_40_08.afpahotellerie.utils;

import android.util.Log;

import com.example.a77011_40_08.afpahotellerie.models.User;

import java.util.HashMap;

/**
 * Created by 77011-40-05 on 08/03/2018.
 */

public class Session {

 
    private static User myUser;

    private static Boolean connectionChecked = false;

    //NAVIGATION ACCESS
    public static HashMap<Integer,Boolean> myAccess = new HashMap<>();


    public static Boolean getConnectionChecked() {
        return connectionChecked;
    }

    public static void setConnectionChecked(Boolean connectionChecked) {
        Session.connectionChecked = connectionChecked;
    }

    public static User getMyUser() {
        return myUser;
    }

    public static void setMyUser(User myUser) {
        Session.myUser = myUser;
        buildAccess();
    }

    public static HashMap<Integer, Boolean> getMyAccess() {
        return myAccess;
    }

    public static void setMyAccess(HashMap<Integer, Boolean> myAccess) {
        Session.myAccess = myAccess;
    }

    private static void buildAccess(){
        int job = myUser.getIdJob();
        switch (job){
            case 1://Directeur
                myAccess.put(Constants._FRAG_HOME,true);
                myAccess.put(Constants.FRAG_ROOMS_CLEAN,false);
                myAccess.put(Constants.FRAG_ASSIGNED_STAFF,false);
                myAccess.put(Constants.FRAG_SATEROOMS,true);
                myAccess.put(Constants.FRAG_ASSIGNED_ROOM,false);
                break;
            case 2://Responsable H
                myAccess.put(Constants._FRAG_HOME,true);
                myAccess.put(Constants.FRAG_ROOMS_CLEAN,false);
                myAccess.put(Constants.FRAG_ASSIGNED_STAFF,true);
                myAccess.put(Constants.FRAG_SATEROOMS,true);
                myAccess.put(Constants.FRAG_ASSIGNED_ROOM,true);
                break;
            case 3://Réceptionniste
                myAccess.put(Constants._FRAG_HOME,true);
                myAccess.put(Constants.FRAG_ROOMS_CLEAN,false);
                myAccess.put(Constants.FRAG_ASSIGNED_STAFF,false);
                myAccess.put(Constants.FRAG_SATEROOMS,true);
                myAccess.put(Constants.FRAG_ASSIGNED_ROOM,false);
                break;
            case 4://Gouvernante
                myAccess.put(Constants._FRAG_HOME,true);
                myAccess.put(Constants.FRAG_ROOMS_CLEAN,false);
                myAccess.put(Constants.FRAG_ASSIGNED_STAFF,true);
                myAccess.put(Constants.FRAG_SATEROOMS,true);
                myAccess.put(Constants.FRAG_ASSIGNED_ROOM,true);
                break;
            case 5://Agents
                myAccess.put(Constants._FRAG_HOME,true);
                myAccess.put(Constants.FRAG_ROOMS_CLEAN,true);
                myAccess.put(Constants.FRAG_ASSIGNED_STAFF,false);
                myAccess.put(Constants.FRAG_SATEROOMS,false);
                myAccess.put(Constants.FRAG_ASSIGNED_ROOM,false);
                break;
            default:
                Log.e(Constants._TAG_LOG,"IdJob non reconnu");
                break;
        }

        /* if (job.getIdJob() == 1) {
            Map<Boolean, String> myMap = new HashMap<Boolean, String>();
            myMap.put(true, "1");
            myMap.put(false, "2");
            myMap.put(false, "3");
            myMap.put(false, "4");
            myMap.put(false, "5");
        } else if (job.getIdJob() == 2) {

            Map<Boolean, String> myMap = new HashMap<Boolean, String>();
            myMap.put(false, "1");
            myMap.put(true, "2");
            myMap.put(false, "3");
            myMap.put(false, "4");
            myMap.put(false, "5");
        } else if (job.getIdJob() == 3) {
            Map<Boolean, String> myMap = new HashMap<Boolean, String>();
            myMap.put(false, "1");
            myMap.put(false, "2");
            myMap.put(true, "3");
            myMap.put(false, "4");
            myMap.put(false, "5");
        } else if (job.getIdJob() == 4) {
            Map<Boolean, String> myMap = new HashMap<Boolean, String>();
            myMap.put(false, "1");
            myMap.put(false, "2");
            myMap.put(false, "3");
            myMap.put(true, "4");
            myMap.put(false, "5");
        } else if (job.getIdJob() == 5) {
            Map<Boolean, String> myMap = new HashMap<Boolean, String>();
            myMap.put(false, "1");
            myMap.put(false, "2");
            myMap.put(false, "3");
            myMap.put(false, "4");
            myMap.put(true, "5");
        }*/
    }
}
