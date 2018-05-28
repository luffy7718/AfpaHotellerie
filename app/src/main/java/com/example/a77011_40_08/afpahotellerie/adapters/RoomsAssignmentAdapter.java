package com.example.a77011_40_08.afpahotellerie.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.fragments.AssignedStaffFragment;
import com.example.a77011_40_08.afpahotellerie.holders.RoomsAssignmentHolder;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;

import java.util.Collections;


public class RoomsAssignmentAdapter extends RecyclerView.Adapter<RoomsAssignmentHolder> {

    Rooms rooms;
    int idStaff;
    boolean isAssigned;
    Activity activity;

    public RoomsAssignmentAdapter(boolean isAssigned, Activity activity) {
        this.rooms = new Rooms();
        this.isAssigned = isAssigned;
        this.activity = activity;

    }

    @Override
    public RoomsAssignmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_assigned_rooms, parent, false);

        return new RoomsAssignmentHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsAssignmentHolder holder, int position) {
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
