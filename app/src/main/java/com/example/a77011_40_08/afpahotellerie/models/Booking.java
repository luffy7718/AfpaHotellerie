package com.example.a77011_40_08.afpahotellerie.models;

public class Booking {
    private int idBooking;
    private  Room room;


    public Booking() {

    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    /*public boolean checkin(Room r,Guest guest) {
        if (r.getGuest() == null) {
            r.setGuest(guest);
            this.room = r;
            return true;
        }
        return false;
    }

    /**
     * Check guest out
     * @return boolean
     */
   /* public boolean checkout() {
        if (this.room != null && this.room.getGuest() == this) {
            this.room.setGuest(null);
            this.room = null;
            return true;
        }
        return false;
    }*/
}
