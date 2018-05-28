package com.example.a77011_40_08.afpahotellerie.utils;

import com.example.a77011_40_08.afpahotellerie.models.User;

/**
 * Created by 77011-40-05 on 08/03/2018.
 */

public class Session {

 
    private static User myUser;

    private static Boolean connectionChecked = false;

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
    }


}
