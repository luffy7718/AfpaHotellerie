package com.example.a77011_40_08.afpahotellerie.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.holders.StateRoomsHolder;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;

public class StateRoomsAdapter extends RecyclerView.Adapter<StateRoomsHolder> {

    Rooms rooms;
    Activity activity;

    public StateRoomsAdapter(Activity activity) {
        this.rooms = new Rooms();
        this.activity = activity;
    }

    @NonNull
    @Override
    public StateRoomsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_state_rooms, parent, false);
        return new StateRoomsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateRoomsHolder holder, int position) {
        Room room = rooms.get(position);
        holder.setRooms(room, activity);
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
        this.rooms = rooms;
    }
}
