package com.example.a77011_40_08.afpahotellerie.Holders;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.Toolbar;


import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Adapters.ListRoomsAdapter;
import com.example.a77011_40_08.afpahotellerie.Fragments.ListRoomsFragment;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.App;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.example.a77011_40_08.afpahotellerie.Utils.GenericAlertDialog;
import com.example.a77011_40_08.afpahotellerie.Utils.Session;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;


/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class ListRoomsHolder extends RecyclerView.ViewHolder {
    public final TextView txtNumber;
    public final TextView txtAbbréviation;
    public final ImageButton btnPlay;
    public final ImageButton btnPause;
    public final Toolbar toolbar;
    SWInterface swInterface;
    Room room;
    App app;
    int position;
    ListRoomsAdapter parent;


    public ListRoomsHolder(View view) {
        super(view);
        Context context = (Activity) view.getContext();
        swInterface = RetrofitApi.getInterface();
        txtNumber = (TextView) view.findViewById(R.id.txtNumber);
        txtAbbréviation = (TextView) view.findViewById(R.id.txtAbbréviation);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnPause = (ImageButton) view.findViewById(R.id.btnPause);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_card_view);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_BonTechnique) {
                    Log.e("TAG", "bon technique chambre " + room.getNumber());

                    if (room != null) {

                        View container = View.inflate(context, R.layout.dialog_technique, null);


                        callGetFurnituresTroubles();
                        new GenericAlertDialog((Activity) view.getContext(), "Chambre " + room
                                .getNumber(), container,
                                new GenericAlertDialog.CallGenericAlertDialog() {
                                    @Override
                                    public void onValidate() {


                                    }
                                });
                    }

                }
                return false;
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                RoomStatuts roomStatuts = app.getRoomStatuts();
                String code = roomStatuts.get(room.getIdRoomStatus()-1).getAbbreviation();
                int idRoomStatus = -1;
                if( code.equals("LS")){
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("LE");
                }else if(code.equals("OS")){
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("OE");
                }
                if(idRoomStatus != -1){
                    callRoomsHistory(idRoomStatus);
                }
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomStatuts roomStatuts = app.getRoomStatuts();
                String code = roomStatuts.get(room.getIdRoomStatus()-1).getAbbreviation();
                int idRoomStatus = -1;
                if( code.equals("LE")){
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("LP");
                }else if(code.equals("OE")){
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("OP");
                }
                if(idRoomStatus != -1){
                    callRoomsHistory(idRoomStatus);
                }
            }
        });

    }


    public void setRooms(final Room room,int position, Activity activity, ListRoomsAdapter parent) {
        this.parent = parent;
        this.room = room;
        this.position = position;
        txtNumber.setText("" + room.getNumber());
         app = (App) activity.getApplication();
        RoomStatuts rs = app.getRoomStatuts();
        String status = "XX";
        for (RoomStatut entry : rs) {
            Log.e(Constants._TAG_LOG, "Entry: " + entry.getIdRoomStatus() + "," + entry.getName()
                    + "," + entry.getAbbreviation());
            Log.e(Constants._TAG_LOG, entry.getIdRoomStatus() + " = " + room.getIdRoomStatus());
            if (entry.getIdRoomStatus() == room.getIdRoomStatus()) {
                status = entry.getAbbreviation();
                Log.e(Constants._TAG_LOG, "Status: " + status);
            }
        }
        txtAbbréviation.setText(status);


    }


    private void callGetFurnituresTroubles() {
        try {


            Call<Push> call = swInterface.getFurnituresTroublesByIdRoom(Functions.getAuth(), room
                    .getIdRoom());

            call.enqueue(new Callback<Push>() {
                @Override
                public void onResponse(Call<Push> call, Response<Push> response) {
                    if (response.isSuccessful()) {
                        Log.e(Constants._TAG_LOG, "callGetFurnituresTroubles : " + response.body
                                ().toString());
                        Push push = response.body();
                        if (push.getStatus() == 1) {
                            Gson gson = new Gson();

                        } else {
                            Log.e("push.getdata = ", push.getData());

                        }

                    } else {
                        //todo:gérer les code erreur de retour
                        Log.e(Constants._TAG_LOG, "ERROR code :" + response.code());
                    }

                }

                @Override
                public void onFailure(Call<Push> call, Throwable t) {
                    Log.e("error", "");

                }
            });


        } catch (Exception e) {
            Log.e("Tag", e.toString());
        }
    }

    private void callRoomsHistory(int idRoomStatus) {
        try {

            Log.e(Constants._TAG_LOG,"callRoomsHistory2: "+room
            .getIdRoom()+"  "+Session.getMyUser().getIdStaff()+" "+Functions.today()+" "+idRoomStatus+" ");
            Call<Push> call = swInterface.addRoomsHistory(Functions.getAuth(), room
                    .getIdRoom(),Session.getMyUser().getIdStaff(),Functions.today(),idRoomStatus);

            call.enqueue(new Callback<Push>() {
                @Override
                public void onResponse(Call<Push> call, Response<Push> response) {
                    if (response.isSuccessful()) {
                        Log.e(Constants._TAG_LOG, "callRoomsHistory : " + response.body
                                ().toString());
                        Push push = response.body();
                        room.setIdRoomStatus(idRoomStatus);
                        parent.notifyDataSetChanged();
                        if (push.getStatus() == 1) {
                            Gson gson = new Gson();

                        } else {
                            Log.e("push.getdata = ", push.getData());

                        }

                    } else {
                        //todo:gérer les code erreur de retour
                        Log.e(Constants._TAG_LOG, "ERROR code :" + response.code());
                    }

                }

                @Override
                public void onFailure(Call<Push> call, Throwable t) {
                    Log.e("error", "");

                }
            });


        } catch (Exception e) {
            Log.e("Tag", e.toString());
        }
    }


}
