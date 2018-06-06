package com.example.a77011_40_08.afpahotellerie.holders;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toolbar;


import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.RoomsToCleanAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.GenericAlertDialog;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class RoomsToCleanHolder extends RecyclerView.ViewHolder {
    public final TextView txtNumber;
    public final TextView txtAbbréviation;
    public final ImageButton btnPlay;
    public final ImageButton btnPause;
    public final Toolbar toolbar;
    SWInterface swInterface;
    Room room;
    App app;
    int position;
    RoomsToCleanAdapter parent;
    GenericAlertDialog genericAlertDialog;

    public RoomsToCleanHolder(View view) {
        super(view);
        Context context = (Activity) view.getContext();
        swInterface = RetrofitApi.getInterface();
        txtNumber = (TextView) view.findViewById(R.id.txtNumber);
        txtAbbréviation = (TextView) view.findViewById(R.id.txtAbbréviation);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnPause = (ImageButton) view.findViewById(R.id.btnPause);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_card_view);

        btnPause.setVisibility(View.GONE);

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
                                .getNumber(), "", null,
                                new GenericAlertDialog.CallGenericAlertDialog() {
                                    @Override
                                    public void validate() {


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
                String code = roomStatuts.get(room.getIdRoomStatus() - 1).getAbbreviation();
                int idRoomStatus = -1;
                if (code.equals("LS")) {
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("LE");
                } else if (code.equals("OS")) {
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("OE");
                }
                if (idRoomStatus != -1) {
                    btnPlay.setVisibility(View.GONE);
                    btnPause.setVisibility(View.VISIBLE);
                    callRoomsHistory(idRoomStatus);
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoomStatuts roomStatuts = app.getRoomStatuts();
                String code = roomStatuts.get(room.getIdRoomStatus() - 1).getAbbreviation();
                int idRoomStatus = -1;
                if (code.equals("LE")) {
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("LP");
                } else if (code.equals("OE")) {
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("OP");
                }
                if (idRoomStatus != -1) {
                    btnPlay.setVisibility(View.VISIBLE);
                    btnPause.setVisibility(View.GONE);
                    callRoomsHistory(idRoomStatus);
                }
            }
        });

    }


    public void setRooms(final Room room, int position, Activity activity, RoomsToCleanAdapter
            parent) {
        this.parent = parent;
        this.room = room;
        this.position = position;
        txtNumber.setText("" + room.getNumber());
        app = (App) activity.getApplication();
        RoomStatuts rs = app.getRoomStatuts();
        String status = "XX";
        for (RoomStatut entry : rs) {
            if (entry.getIdRoomStatus() == room.getIdRoomStatus()) {
                status = entry.getAbbreviation();
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
        int position = getAdapterPosition();
        Log.e(Constants._TAG_LOG, "Position: " + position);
        Call<Push> call = swInterface.addRoomsHistory(Functions.getAuth(), room
                .getIdRoom(), Session.getMyUser().getIdStaff(), Functions.today(), idRoomStatus);

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
                        if (idRoomStatus == 1) {
                            parent.removeRoom(position);
                        }
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
    }


}
