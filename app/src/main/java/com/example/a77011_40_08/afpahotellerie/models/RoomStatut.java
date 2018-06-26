package com.example.a77011_40_08.afpahotellerie.models;

public class RoomStatut {

    private int idRoomStatus;
    private String name;
    private String abbreviation;
    private int statusOrder;

    public int getIdRoomStatus() {
        return idRoomStatus;
    }

    public void setIdRoomStatus(int idRoomStatus) {
        this.idRoomStatus = idRoomStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }
}
