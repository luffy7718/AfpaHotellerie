package com.example.a77011_40_08.afpahotellerie.Models;

import java.util.ArrayList;

public class RoomStatuts extends ArrayList<RoomStatut> {

    public int getIdRoomStatusByCode(String code){
        int id = -1;
        for(RoomStatut rs : this){
            if(rs.getAbbreviation().equals(code)){
                id = rs.getIdRoomStatus();
            }
        }
        return id;
    }

}
