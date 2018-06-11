package com.example.a77011_40_08.afpahotellerie.holders;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;

public class StateRoomsHolder extends RecyclerView.ViewHolder {
    RoomStatut roomStatut;
    TextView txtNumber;
    TextView txtAbbreviation;
    TextView txtInProgress;
    TextView txtAgentInProgress;
    public FrameLayout frlRoom;
    SWInterface swInterface;
    Room room;
    User staff;



    String staffName;

    public StateRoomsHolder(View view) {
        super(view);
        swInterface = RetrofitApi.getInterface();
        txtNumber = view.findViewById(R.id.txtNumber);
        txtAbbreviation = view.findViewById(R.id.txtAbbreviation);
        txtInProgress = view.findViewById(R.id.txtInProgress);
        txtAgentInProgress = view.findViewById(R.id.txtAgentInProgress);
        frlRoom = view.findViewById(R.id.frlRoom);
    }

    public void setRooms(final Room room, Activity activity) {
        this.room = room;
        txtNumber.setText("" + room.getNumber());
        /*app = (App) activity.getApplication();
        RoomStatuts rs = app.getRoomStatuts();*/
        String status = "";
        for (RoomStatut roomStatut : App.getRoomStatuts()) {
            //Log.e(Constants._TAG_LOG, "Entry: " + entry.getIdRoomStatus() + "," + entry.getName() + "," + entry.getAbbreviation());
            //Log.e(Constants._TAG_LOG, entry.getIdRoomStatus() + " = " + room.getIdRoomStatus());
            if (roomStatut.getIdRoomStatus() == room.getIdRoomStatus()) {
                this.roomStatut = roomStatut;
                status = roomStatut.getAbbreviation();
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
                        this.staff = user;
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

        Functions.setViewBgColorByStatus(txtAbbreviation, status);

        /*GradientDrawable bgShape = (GradientDrawable)txtNumber.getBackground();
        //int idRessource = App.getColors().get(status);
        bgShape.setColor(activity.getResources().getColor(R.color.colorAccent));*/

        frlRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)activity).showRoomDetails(room, roomStatut, staff);
            }
        });
    }
}
