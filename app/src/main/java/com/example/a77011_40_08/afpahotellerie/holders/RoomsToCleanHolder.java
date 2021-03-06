package com.example.a77011_40_08.afpahotellerie.holders;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.Toolbar;


import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.fragments.RoomsToCleanFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.StateRoomsFragment;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
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

import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class RoomsToCleanHolder extends RecyclerView.ViewHolder {
    public final TextView txtNumber;
    public final TextView txtAbbreviation;
    public final ImageButton btnPlay;
    public final ImageButton btnPause;
    public final Toolbar toolbar;
    RoomStatut roomStatut;
    SWInterface swInterface;
    Room room;
    int position;
    RoomsToCleanAdapter parent;
    RoomStatuts roomStatuts;
    String code;
    CardView cvRoomsToClean;
    Activity activity;
    int idRoomStatus;
    String status;
    RoomsToCleanFragment roomsToCleanFragment;
    Gson gson;

    public RoomsToCleanHolder(View view) {
        super(view);
        Context context = (Activity) view.getContext();
        swInterface = RetrofitApi.getInterface();
        txtNumber = (TextView) view.findViewById(R.id.txtNumber);
        txtAbbreviation = (TextView) view.findViewById(R.id.txtAbbreviation);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnPause = (ImageButton) view.findViewById(R.id.btnPause);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_card_view);
        cvRoomsToClean=view.findViewById(R.id.cvRoomsToClean);
        btnPause.setVisibility(View.GONE);

        gson = new Gson();

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
                roomStatuts = App.getRoomStatuts();
                code = roomStatuts.get(room.getIdRoomStatus() - 1).getAbbreviation();
                idRoomStatus = -1;

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

                roomStatuts = App.getRoomStatuts();
                code = roomStatuts.get(room.getIdRoomStatus() - 1).getAbbreviation();
                idRoomStatus = -1;

                if (code.equals("LE")) {
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("LP");
                } else if (code.equals("OE")) {
                    idRoomStatus = roomStatuts.getIdRoomStatusByCode("OP");
                }
                if (idRoomStatus != -1) {
                    btnPlay.setVisibility(View.VISIBLE);
                    btnPause.setVisibility(View.GONE);
                    callRoomsHistory(idRoomStatus);
                    HomeActivity home = (HomeActivity) activity;
                    RoomsToCleanFragment frag = (RoomsToCleanFragment) home.getLastFragment();
                    frag.callRefreshRoomView();
                }

            }
        });

    }


    public void setRooms(final Room room, int position, Activity activity, RoomsToCleanAdapter parent) {
        this.parent = parent;
        this.room = room;
        this.position = position;
        this.activity= activity;
        this.roomsToCleanFragment= roomsToCleanFragment;

        txtNumber.setText("" + room.getNumber());
        RoomStatuts rs = App.getRoomStatuts();
        status= "";
        cvRoomsToClean.setForeground(null);


        btnPlay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.GONE);
        for (RoomStatut entry : rs) {
            if (entry.getIdRoomStatus() == room.getIdRoomStatus()) {
                status = entry.getAbbreviation();
            }
            code = rs.get(room.getIdRoomStatus() - 1).getAbbreviation();
            int idRoomStatus = -1;
            if (code.equals("LE")) {
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                idRoomStatus = rs.getIdRoomStatusByCode("LP");
            } else if (code.equals("OE")) {
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                idRoomStatus = rs.getIdRoomStatusByCode("OP");
            } else if (code.equals("LP") || (code.equals("OP"))) {
                cvRoomsToClean.setForeground(activity.getDrawable(R.drawable.card_view_disable));
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.GONE);
            }
            if (idRoomStatus != -1) {
            }
        }

        txtAbbreviation.setText(status);
        switch(status){
            case "LE":
                status = "LS";
                break;
            case "OE":
                status = "OS";
                break;
        }

        Functions.setViewBgColorByStatus(txtAbbreviation, status);

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
                    parent.refreshRoomsView();
                    if (push.getStatus() == 1) {
                        if (idRoomStatus == 1) {
                            //parent.removeRoom(position);
                        }
                    } else {
                        Log.e("push.getdata = ", push.getData());

                    }
                    sendToTopic();
                } else {
                    //todo:gérer les code erreur de retour
                    Log.e(Constants._TAG_LOG, "ERROR code :" + response.code());
                }

            }

            private void sendToTopic() {

                HashMap<String, String> body = new HashMap<>();
                body.put("topic", "roomState");
                String json = gson.toJson(body);
                Log.e(Constants._TAG_LOG, "Body: " + json);
                Call<Push> call = swInterface.sendMessageToTopic(Functions.getAuth(), "roomState",
                        Session.getMyUser().getIdStaff(), "notification", json);
                call.enqueue(new Callback<Push>() {
                    @Override
                    public void onResponse(Call<Push> call, Response<Push> response) {
                        if (response.isSuccessful()) {
                            Push push = response.body();
                            if (push.getStatus() == 1) {
                                Log.e(Constants._TAG_LOG, "Success onStop RoomSate");
                                Log.e(Constants._TAG_LOG, push.getData());
                            } else {
                                Log.e(Constants._TAG_LOG, push.getData());
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

            @Override
            public void onFailure(Call<Push> call, Throwable t) {
                Log.e("error", "");

            }
        });
    }


}
