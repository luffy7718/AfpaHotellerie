package com.example.a77011_40_08.afpahotellerie.models;

public class RoomType {

    private int idRoomType;
    private String name;
    private int beds;

    public RoomType() {
    }


    public int getIdRoomType() {
        return idRoomType;
    }

    public void setIdRoomType(int idRoomType) {
        this.idRoomType = idRoomType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }
}
