package com.example.a77011_40_08.afpahotellerie.holders;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;

public class StateRoomsHolderGrid extends RecyclerView.ViewHolder {
    RoomStatut roomStatut;
    TextView txtNumberGrid;
    ImageView imgNotifBg;
    ImageView imgNotif;
    public FrameLayout frlRoom;
    SWInterface swInterface;
    Room room;
    User staff;
    String staffName;

    public StateRoomsHolderGrid(View view) {
        super(view);
        swInterface = RetrofitApi.getInterface();
        txtNumberGrid = view.findViewById(R.id.txtNumberGrid);
        imgNotifBg = view.findViewById(R.id.imgNotifBg);
        imgNotif = view.findViewById(R.id.imgNotif);
        frlRoom = view.findViewById(R.id.frlRoom);
    }

    public void setRooms(final Room room, Activity activity) {
        this.room = room;
        txtNumberGrid.setText("" + room.getNumber());

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
                imgNotifBg.setVisibility(View.VISIBLE);
                imgNotif.setVisibility(View.VISIBLE);

                Log.e(Constants._TAG_LOG, "Valeur recherchée : " + room.getIdStaff());

                for(User user : App.getStaff()) {
                    Log.e(Constants._TAG_LOG, "idStaff : " + user.getIdStaff());
                    if(user.getIdStaff() == (room.getIdStaff())) {
                        Log.e(Constants._TAG_LOG, "idStaff Success");
                        this.staff = user;
                        staffName = user.getFullName();
                    }
                }

                Log.e(Constants._TAG_LOG, "Changement LE");
                break;
            case "OE":
                status = "OS";
                imgNotifBg.setVisibility(View.VISIBLE);
                imgNotif.setVisibility(View.VISIBLE);
                Log.e(Constants._TAG_LOG, "Changement OE");
                break;
            default:
                imgNotifBg.setVisibility(View.GONE);
                imgNotif.setVisibility(View.GONE);
                break;
        }

        GradientDrawable bgShape = (GradientDrawable)txtNumberGrid.getBackground();
        int idRessource = App.getColors().get(status);
        bgShape.setColor(idRessource);
        bgShape.setStroke(1, idRessource);

        frlRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)activity).showRoomDetails(room, roomStatut, staff);
            }
        });
    }
}
