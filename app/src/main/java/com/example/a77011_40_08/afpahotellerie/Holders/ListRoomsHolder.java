package com.example.a77011_40_08.afpahotellerie.Holders;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toolbar;


import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.App;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;


/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class ListRoomsHolder extends RecyclerView.ViewHolder {
    public final TextView txtNumber;
    public final TextView txtAbbréviation;
    public final ImageButton btnPlay;
    public final ImageButton btnPause;
    public final Toolbar toolbar;

    public ListRoomsHolder(View view) {
        super(view);

        txtNumber = (TextView) view.findViewById(R.id.txtNumber);
        txtAbbréviation = (TextView) view.findViewById(R.id.txtAbbréviation);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnPause = (ImageButton) view.findViewById(R.id.btnPause);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_card_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.action_BonTechnique)
                {
                    Log.e("TAG","bon technique");
                }
                return false;
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    public void setRooms(final Room room, Activity activity) {
        txtNumber.setText(""+room.getNumber());
        App app = (App) activity.getApplication();
        RoomStatuts rs = app.getRoomStatuts();
        String status = "TT";
        for(RoomStatut entry : rs){
            Log.e(Constants._TAG_LOG,"Entry: "+entry.getIdRoomStatus()+","+entry.getName()+","+entry.getAbbreviation());
            Log.e(Constants._TAG_LOG,entry.getIdRoomStatus()+" = "+room.getIdRoomStatus());
            if(entry.getIdRoomStatus() == room.getIdRoomStatus()){
                status = entry.getAbbreviation();
                Log.e(Constants._TAG_LOG,"Status: "+status);
            }
        }
        txtAbbréviation.setText(status);


    }


}
