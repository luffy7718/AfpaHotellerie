package com.example.a77011_40_08.afpahotellerie.Holders;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Adapters.AssignRoomsByStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.Adapters.AssignedRoomsAdapter;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.Models.Users;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.App;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.example.a77011_40_08.afpahotellerie.Utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignRoomsByStaffHolder extends RecyclerView.ViewHolder {

    int position;
    AssignRoomsByStaffAdapter parent;
    Room room;
    CardView cvaffected;
    SWInterface swInterface;
    public final TextView txtNumber;

    public AssignRoomsByStaffHolder(View view) {
        super(view);
        swInterface = RetrofitApi.getInterface();
        txtNumber = (TextView) view.findViewById(R.id.txtNumber);
        cvaffected = (CardView) view.findViewById(R.id.cvaffected);
        cvaffected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(Constants._TAG_LOG, "click ");
                if (parent.isAssigned()) {
                    removeAssignment();
                } else {

                    addAssignment();
                }
            }
        });
    }


    public void setRooms(final Room room, int position, AssignRoomsByStaffAdapter parent, boolean
            isAssigned) {
        this.parent = parent;
        this.room = room;
        this.position = position;
        if (isAssigned) {
            txtNumber.setText("" + room.getNumber());
            cvaffected.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
        } else {
            txtNumber.setText("" + room.getNumber());
        }


    }

    private void removeAssignment() {
        Call<Push> call = swInterface.removeAssignment(Functions.getAuth(), room.getIdRoom());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        parent.removeRoom(position);
                        parent.notifyDataSetChanged();
                        Log.e(Constants._TAG_LOG, "DATA RECIEVE");
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }

    private void addAssignment() {
        Call<Push> call = swInterface.addAssignment(Functions.getAuth(), room.getIdRoom(),
                parent.getIdStaff());
        Log.e(Constants._TAG_LOG, "Donné envoyé " + room.getIdRoom() + " " + parent.getIdStaff());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        parent.removeRoom(position);
                        parent.notifyDataSetChanged();
                        Log.e(Constants._TAG_LOG, "DATA RECIEVE");

                    }
                } else {
                    Log.e(Constants._TAG_LOG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });


    }


}



