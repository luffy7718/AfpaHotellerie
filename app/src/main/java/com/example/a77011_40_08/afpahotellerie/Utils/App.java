package com.example.a77011_40_08.afpahotellerie.Utils;

import android.app.Application;
import android.content.res.Configuration;

import com.example.a77011_40_08.afpahotellerie.Models.Floors;
import com.example.a77011_40_08.afpahotellerie.Models.Jobs;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class App extends Application {
    private  static Jobs jobs;
    private  static RoomStatuts roomStatuts;
    private  static Floors floors;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
 /*   @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }*/

    public static Jobs getJobs() {
        return jobs;
    }

    public static void setJobs(Jobs jobs) {
        App.jobs = jobs;
    }

    public static RoomStatuts getRoomStatuts() {
        return roomStatuts;
    }

    public static void setRoomStatuts(RoomStatuts roomStatuts) {
        App.roomStatuts = roomStatuts;
    }

    public static Floors getFloors() {
        return floors;
    }

    public static void setFloors(Floors floors) {
        App.floors = floors;
    }
}