package com.example.a77011_40_08.afpahotellerie.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.Holders.AssignedRoomsHolder;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;


/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class AssignedRoomsAdapter extends RecyclerView.Adapter<AssignedRoomsHolder> {


    Rooms rooms;
    Activity activity;



    public AssignedRoomsAdapter(Activity activity) {

        this.activity = activity;
        this.rooms = new Rooms();
    }


    @Override
    public AssignedRoomsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_rooms, parent, false);
        return new AssignedRoomsHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignedRoomsHolder holder, int position) {
        Room room = rooms.get(position);
        holder.setRooms(room,position,activity,this);
    }

    @Override
    public int getItemCount() {

        if(rooms == null)
        {
            return 0;
        }
        else
        {
            return rooms.size();
        }
    }

    public  void loadRoom(Rooms rooms)
    {
      this.rooms=rooms;
    }

}