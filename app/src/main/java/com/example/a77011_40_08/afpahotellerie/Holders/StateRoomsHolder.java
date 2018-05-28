package com.example.a77011_40_08.afpahotellerie.Holders;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.App;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;

public class StateRoomsHolder extends RecyclerView.ViewHolder {
    TextView txtNumber;
    TextView txtAbbreviation;
    TextView txtInProgress;
    TextView txtAgentInProgress;
    SWInterface swInterface;
    Room room;
    App app;



    String staffName;

    public StateRoomsHolder(View view) {
        super(view);
        swInterface = RetrofitApi.getInterface();
        txtNumber = view.findViewById(R.id.txtNumber);
        txtAbbreviation = view.findViewById(R.id.txtAbbreviation);
        txtInProgress = view.findViewById(R.id.txtInProgress);
        txtAgentInProgress = view.findViewById(R.id.txtAgentInProgress);
    }

    public void setRooms(final Room room, Activity activity) {
        this.room = room;
        txtNumber.setText("" + room.getNumber());
        app = (App) activity.getApplication();
        RoomStatuts rs = app.getRoomStatuts();
        String status = "XX";
        for (RoomStatut entry : rs) {
            //Log.e(Constants._TAG_LOG, "Entry: " + entry.getIdRoomStatus() + "," + entry.getName() + "," + entry.getAbbreviation());
            //Log.e(Constants._TAG_LOG, entry.getIdRoomStatus() + " = " + room.getIdRoomStatus());
            if (entry.getIdRoomStatus() == room.getIdRoomStatus()) {
                status = entry.getAbbreviation();
            }
        }

        Log.e(Constants._TAG_LOG, "Room: " + room.getNumber() + ", " + status);
        switch(status){
            case "LE":
                status = "LS";
                txtInProgress.setVisibility(View.VISIBLE);
                txtAgentInProgress.setVisibility(View.VISIBLE);

                Log.e(Constants._TAG_LOG, "Valeur recherchée : " + room.getIdStaff());

                for(User user : App.getStaff()) {
                    Log.e(Constants._TAG_LOG, "idStaff : " + user.getIdStaff());
                    if(user.getIdStaff() == (room.getIdStaff())) {
                        Log.e(Constants._TAG_LOG, "idStaff Success");
                        staffName = user.getFullName();
                    }
                }

                txtAgentInProgress.setText(staffName);

                Log.e(Constants._TAG_LOG, "Changement LE");
                break;
            case "OE":
                status = "OS";
                txtInProgress.setVisibility(View.VISIBLE);
                Log.e(Constants._TAG_LOG, "Changement OE");
                break;
            default:
                txtInProgress.setVisibility(View.GONE);
                txtAgentInProgress.setVisibility(View.GONE);
                break;
        }

        txtAbbreviation.setText(status);

        GradientDrawable bgShape = (GradientDrawable)txtAbbreviation.getBackground();
        int idRessource = App.getColors().get(status);
        bgShape.setColor(idRessource);
    }
}
