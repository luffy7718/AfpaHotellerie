package com.example.a77011_40_08.afpahotellerie.Models;



public class Room {
    private int idRoom;
    private int number;
    private int idFloor;
    private int idRoomStatus;
    private String date;
    private int idRoomType;
    private int idStaff;

    public Room(int n, int idr, int idf) {
        this.number=n;
        this.idRoom=idr;
        this.idFloor=idf;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdFloor() {
        return idFloor;
    }

    public void setIdFloor(int idFloor) {
        this.idFloor = idFloor;
    }

    public int getIdRoomStatus() {
        return idRoomStatus;
    }

    public void setIdRoomStatus(int idRoomStatus) {
        this.idRoomStatus = idRoomStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdRoomType() {
        return idRoomType;
    }

    public void setIdRoomType(int idRoomType) {
        this.idRoomType = idRoomType;
    }
}