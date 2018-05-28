package com.example.a77011_40_08.afpahotellerie.Models;

import java.util.ArrayList;
import java.util.Comparator;

public class Rooms extends ArrayList<Room> {

    public static class SortByIdRoom implements Comparator<Room> {
        @Override
        public int compare(Room r1, Room r2) {
            return r1.getIdRoom()-r2.getIdRoom();
        }
    }
}
