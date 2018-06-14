package com.example.a77011_40_08.afpahotellerie.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.fragments.AssignRoomFragment;
import com.example.a77011_40_08.afpahotellerie.holders.AssignRoomHolder;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;

import java.util.Collections;


public class AssignRoomAdapter extends RecyclerView.Adapter<AssignRoomHolder> {

    Rooms rooms;
    int idStaff;
    boolean isAssigned;
    Activity activity;

    public AssignRoomAdapter(boolean isAssigned, Activity activity) {
        this.rooms = new Rooms();
        this.isAssigned = isAssigned;
        this.activity = activity;

    }

    @Override
    public AssignRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_assigned_rooms, parent, false);

        return new AssignRoomHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignRoomHolder holder, int position) {
        Room room = rooms.get(position);
        holder.setRooms(room, position, this, isAssigned);
    }

    @Override
    public int getItemCount() {
        if (rooms == null) {
            return 0;
        } else {
            return rooms.size();
        }
    }

    public int getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public void loadRoom(Rooms rooms) {

        this.rooms = rooms;
    }

    public void removeRoom(int position) {
        Room deletedRoom = rooms.get(position);
        rooms.remove(position);
        HomeActivity home= (HomeActivity) activity;
        AssignRoomFragment frag = (AssignRoomFragment) home.getLastFragment();
        frag.transfert(deletedRoom, isAssigned);
    }

    public void addRoom(Room room) {
        rooms.add(room);
        Collections.sort(rooms,new Rooms.SortByNumberAsc());
        notifyDataSetChanged();
    }


    public boolean compareRooms(Rooms toCompare){
        //null checking
        if(rooms==null && toCompare==null)
            return true;
        if((rooms == null && toCompare != null) || (rooms != null && toCompare == null))
            return false;

        if(rooms.size()!=toCompare.size())
            return false;
        for(Room room: rooms)
        {
            Log.e(Constants._TAG_LOG,"ROOM: "+room.getIdRoom()+" n°"+room.getNumber());
            if(!toCompare.contains(room))
                return false;
        }

        return true;
    }

}
