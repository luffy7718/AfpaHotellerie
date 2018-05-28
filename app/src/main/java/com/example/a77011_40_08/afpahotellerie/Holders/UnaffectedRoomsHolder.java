package com.example.a77011_40_08.afpahotellerie.Holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Adapters.AssignRoomsByStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.Adapters.UnaffectedRoomsAdapter;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.example.a77011_40_08.afpahotellerie.Utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnaffectedRoomsHolder extends RecyclerView.ViewHolder {
    int position;
    UnaffectedRoomsAdapter parent;
    Room room;
    CardView cvUnaffected;
    SWInterface swInterface;
    public final TextView txtNumber;
    public UnaffectedRoomsHolder(View view) {
        super(view);
        swInterface = RetrofitApi.getInterface();
        txtNumber = (TextView) view.findViewById(R.id.txtNumber);
        cvUnaffected=(CardView)view.findViewById(R.id.cvUnaffected);

        cvUnaffected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addAssignement();

            }
        });
    }


    public void setRooms(final Room room, int position, UnaffectedRoomsAdapter parent) {
        this.parent = parent;
        this.room = room;
        this.position = position;
        txtNumber.setText("" + room.getNumber());

    }

}
