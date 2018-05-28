package com.example.a77011_40_08.afpahotellerie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.Holders.AssignRoomsByStaffHolder;
import com.example.a77011_40_08.afpahotellerie.Holders.UnaffectedRoomsHolder;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;

public class UnaffectedRoomsAdapter  extends RecyclerView.Adapter<UnaffectedRoomsHolder> {

    Rooms rooms;
    public UnaffectedRoomsAdapter() {
        this.rooms = new Rooms();
    }

    @Override
    public UnaffectedRoomsHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_unaffected_rooms, parent, false);
        return new UnaffectedRoomsHolder(view);
    }

    @Override
    public void onBindViewHolder( UnaffectedRoomsHolder holder, int position) {
        Room room = rooms.get(position);
        holder.setRooms(room, position, this);
    }

    @Override
    public int getItemCount() {
        if (rooms == null) {
            return 0;
        } else {
            return rooms.size();
        }
    }
    public void loadRoom(Rooms rooms) {
        this.rooms = rooms;
    }
    public void AddRoom(Rooms rooms) {

    }
}
