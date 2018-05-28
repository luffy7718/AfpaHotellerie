package com.example.a77011_40_08.afpahotellerie.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.Activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.Fragments.AssignedStaffFragment;
import com.example.a77011_40_08.afpahotellerie.Holders.AssignRoomsByStaffHolder;
import com.example.a77011_40_08.afpahotellerie.Holders.AssignedRoomsHolder;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;

import java.util.Collections;


public class AssignRoomsByStaffAdapter extends RecyclerView.Adapter<AssignRoomsByStaffHolder> {

    Rooms rooms;
    int idStaff;
    boolean isAssigned;
    Activity activity;

    public AssignRoomsByStaffAdapter(boolean isAssigned, Activity activity) {
        this.rooms = new Rooms();
        this.isAssigned = isAssigned;
        this.activity = activity;

    }

    @Override
    public AssignRoomsByStaffHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_assigned_rooms, parent, false);

        return new AssignRoomsByStaffHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignRoomsByStaffHolder holder, int position) {
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
        AssignedStaffFragment frag = (AssignedStaffFragment) home.getLastFragment();
        frag.transfert(deletedRoom, isAssigned);
    }

    public void addRoom(Room room) {
        rooms.add(room);
        Collections.sort(rooms,new Rooms.SortByIdRoom());
        notifyDataSetChanged();
    }


}
