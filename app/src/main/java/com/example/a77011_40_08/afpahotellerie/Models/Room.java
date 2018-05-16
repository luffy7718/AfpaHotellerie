package com.example.a77011_40_08.afpahotellerie.Models;

//https://www.programcreek.com/java-api-examples/index.php?source_dir=Softwaresystemen-master/ss/week3/hotel/Hotel.java#

public class Room {
    private int idRoom;
    private int number;
    private int idFloor;
    private Guest guest;
    public Room(int n, int idr, int idf) {
        this.number=n;
        this.idRoom=idr;
        this.idFloor=idf;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdFloor() {
        return idFloor;
    }

    public void setIdFloor(int idFloor) {
        this.idFloor = idFloor;
    }
}