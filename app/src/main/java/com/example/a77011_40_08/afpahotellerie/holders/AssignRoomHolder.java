package com.example.a77011_40_08.afpahotellerie.holders;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.AssignRoomAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignRoomHolder extends RecyclerView.ViewHolder {

    int position;
    AssignRoomAdapter parent;
    Room room;
    CardView cvaffected;
    SWInterface swInterface;
    public final TextView txtNumber;

    public AssignRoomHolder(View view) {
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


    public void setRooms(final Room room, int position, AssignRoomAdapter parent, boolean
            isAssigned) {
        this.parent = parent;
        this.room = room;
        this.position = position;
        if (isAssigned) {
            txtNumber.setText("" + room.getNumber());
            cvaffected.setCardBackgroundColor(Color.parseColor("#00FF00"));
        } else {
            txtNumber.setText("" + room.getNumber());
            cvaffected.setCardBackgroundColor(Color.parseColor("#AFAFAF"));
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



