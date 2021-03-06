package com.example.a77011_40_08.afpahotellerie.utils;

import android.app.Application;

import com.example.a77011_40_08.afpahotellerie.models.Floors;
import com.example.a77011_40_08.afpahotellerie.models.Jobs;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.models.RoomsTypes;
import com.example.a77011_40_08.afpahotellerie.models.Users;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class App extends Application {
    private  static Jobs jobs;
    private  static RoomStatuts roomStatuts;
    private  static Floors floors;
    private static RoomsTypes roomsTypes;
    private static Users staff;


    //COLOR ROOM STATUS
    private static HashMap<String,Integer> colors;


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

    public static RoomsTypes getRoomsTypes() {
        return roomsTypes;
    }

    public static void setRoomsTypes(RoomsTypes roomsTypes) {
        App.roomsTypes = roomsTypes;
    }

    public static void loadColors(){

    }

    public static HashMap<String, Integer> getColors() {
        return colors;
    }

    public static void setColors(HashMap<String, Integer> colors) {
        App.colors = colors;
    }

    public static Users getStaff() {
        return staff;
    }

    public static void setStaff(Users staff) {
        App.staff = staff;
    }

}