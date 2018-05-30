package com.example.a77011_40_08.afpahotellerie.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.holders.RoomsToCleanHolder;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;


/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class RoomsToCleanAdapter extends RecyclerView.Adapter<RoomsToCleanHolder> {

    Rooms rooms;
    Activity activity;
    TextView roomCount = null;

    public RoomsToCleanAdapter(Activity activity) {
        this.activity = activity;
        this.rooms = new Rooms();
    }

    public void setRoomsCountDisplay(TextView txt){
        roomCount = txt;
    }

    @Override
    public RoomsToCleanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_rooms_to_clean, parent, false);
        return new RoomsToCleanHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomsToCleanHolder holder, int position) {
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
            displayRoomsCount();
            return rooms.size();
        }
    }

    private void displayRoomsCount(){
        if(roomCount != null){
            roomCount.setText(rooms.size()+"");
        }else{
            Log.e(Constants._TAG_LOG,"Affichage non disponible");
        }
    }

    public  void loadRoom(Rooms rooms)
    {
      this.rooms = rooms;
    }

    public void removeRoom(int position) {
        rooms.remove(position);
        notifyDataSetChanged();
    }

}