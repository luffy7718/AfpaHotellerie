package com.example.a77011_40_08.afpahotellerie.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.Fragments.ListRoomsFragment;
import com.example.a77011_40_08.afpahotellerie.Holders.ListRoomsHolder;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.App;


/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class ListRoomsAdapter extends RecyclerView.Adapter<ListRoomsHolder> {


    Rooms rooms;
    Activity activity;



    public ListRoomsAdapter(Rooms rooms, Activity activity) {
        this.rooms = rooms;
        this.activity = activity;
    }


    @Override
    public ListRoomsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_rooms, parent, false);
        return new ListRoomsHolder(view);
    }

    @Override
    public void onBindViewHolder(ListRoomsHolder holder, int position) {
        Room room = rooms.get(position);
        holder.setRooms(room,position,activity,this);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


}