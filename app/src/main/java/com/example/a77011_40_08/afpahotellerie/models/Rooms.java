package com.example.a77011_40_08.afpahotellerie.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Rooms extends ArrayList<Room> {

    public Rooms() {
    }

    public Rooms(@NonNull Collection<? extends Room> c) {
        super(c);
    }

    public static class SortByIdRoom implements Comparator<Room> {
        @Override
        public int compare(Room r1, Room r2) {
            return r1.getIdRoom()-r2.getIdRoom();
        }
    }

    public Rooms filterByRoomStatus(int[] statusList){
        Rooms rooms = new Rooms();
        for(Room room: this){
            boolean isValid = false;
            for(int status: statusList){
                if(room.getIdRoomStatus() == status){
                    isValid = true;
                }
            }
            if(isValid){
                rooms.add(room);
            }
        }

        return rooms;
    }

    public Rooms filterByFloor(int floor){
        Rooms rooms = new Rooms();
        for(Room room: this){
            boolean isValid = false;
            if(room.getIdFloor() == floor){
                isValid = true;
            }
            if(isValid){
                rooms.add(room);
            }
        }

        return rooms;
    }

    /*public Rooms filterByFloor(int[] floorList){
        Rooms rooms = new Rooms();
        for(Room room: this){
            boolean isValid = false;
            for(int floor: floorList){
                if(room.getIdFloor() == floor){
                    isValid = true;
                }
            }
            if(isValid){
                rooms.add(room);
            }
        }

        return rooms;
    }*/

    public Rooms filterByRoomType(int[] typeList){
        Rooms rooms = new Rooms();
        for(Room room: this){
            boolean isValid = false;
            for(int type: typeList){
                if(room.getIdRoomType() == type){
                    isValid = true;
                }
            }
            if(isValid){
                rooms.add(room);
            }
        }

        return rooms;
    }

    public Rooms filterByAssignment(int assignment){
        Rooms rooms = new Rooms();
        for(Room room: this){
            boolean isValid = false;
            if(room.getIdStaff() == assignment){
                isValid = true;
            }
            if(isValid){
                rooms.add(room);
            }
        }

        return rooms;
    }
}
