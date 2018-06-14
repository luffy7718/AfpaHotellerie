package com.example.a77011_40_08.afpahotellerie.models;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by 77011-40-05 on 08/03/2018.
 */

public class Users extends ArrayList<User> {

    public static class SortByName implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) { return u1.getName().compareTo(u2.getName());
        }
    }
}
