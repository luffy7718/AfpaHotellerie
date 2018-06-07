package com.example.a77011_40_08.afpahotellerie.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.holders.StateRoomsHolder;
import com.example.a77011_40_08.afpahotellerie.holders.StateRoomsHolderGrid;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;

public class StateRoomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Rooms rooms;
    Activity activity;

    boolean isSwitchView = true;
    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;

    public StateRoomsAdapter(Activity activity) {
        this.rooms = new Rooms();
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == LIST_ITEM){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_state_rooms, parent, false);
            return new StateRoomsHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_grid_state_rooms, parent, false);
            return new StateRoomsHolderGrid(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Room room = rooms.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                StateRoomsHolder stateRoomsHolder = (StateRoomsHolder)holder;
                stateRoomsHolder.setRooms(room, activity);
                break;

            case 1:
                StateRoomsHolderGrid stateRoomsHolderGrid = (StateRoomsHolderGrid)holder;
                stateRoomsHolderGrid.setRooms(room, activity);
                break;
        }

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
        notifyDataSetChanged();
    }

    public boolean toggleItemViewType () {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }

    @Override
    public int getItemViewType (int position) {
        if (isSwitchView){
            return LIST_ITEM;
        }else{
            return GRID_ITEM;
        }
    }
}
