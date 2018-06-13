package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.StateRoomsAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateRoomsFragment extends Fragment {

    Context context;
    RecyclerView rvwStateRooms;
    ImageButton btnSwitchView;
    ImageButton btnSort;
    Button btnInfos;
    Button btnFilter;
    Boolean isSnackbarClose = true;
    Boolean isAsc = true;
    Rooms roomsFromDB;
    Rooms rooms;
    Gson gson;
    TextView txtResultInfo;
    String[] strArr;

    public StateRoomsAdapter stateRoomsAdapter;
    SWInterface swInterface;

    public StateRoomsFragment() {
    }

    public static StateRoomsFragment newInstance(String param1, String param2) {
        StateRoomsFragment fragment = new StateRoomsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swInterface = RetrofitApi.getInterface();
        context = getActivity();

        stateRoomsAdapter = new StateRoomsAdapter(getActivity());

        getRooms();
        getStaff();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_rooms, container, false);

        gson = new Gson();

        rvwStateRooms = view.findViewById(R.id.rvwStateRooms);
        btnSwitchView = view.findViewById(R.id.btnSwitchView);
        btnSort = view.findViewById(R.id.btnSort);
        btnInfos = view.findViewById(R.id.btnInfos);
        btnFilter = view.findViewById(R.id.btnFilter);
        txtResultInfo = view.findViewById(R.id.txtResultInfo);

        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context);
        rvwStateRooms.setLayoutManager(layoutManagerR);

        rvwStateRooms.setAdapter(stateRoomsAdapter);

        btnSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSwitched = stateRoomsAdapter.toggleItemViewType();
                rvwStateRooms.setLayoutManager(isSwitched ? new LinearLayoutManager(context) : new GridLayoutManager(context, 4));
                stateRoomsAdapter.notifyDataSetChanged();

                if (isSwitched) {
                    btnSwitchView.setImageResource(R.drawable.ic_view_comfy_white_24dp);
                    TooltipCompat.setTooltipText(btnSwitchView, "Grille");
                }
                else {
                    btnSwitchView.setImageResource(R.drawable.ic_list_white_24dp);
                    TooltipCompat.setTooltipText(btnSwitchView, "Liste");
                }
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAsc = !isAsc;

                if (isAsc) {
                    btnSort.setImageResource(R.drawable.ic_sort_24dp);
                    btnSort.setScaleX(1);
                    btnSort.setScaleY(1);
                    btnSort.setTranslationX(1);
                    TooltipCompat.setTooltipText(btnSort, "Ordre décroissant");
                }
                else {
                    btnSort.setScaleX(1);
                    btnSort.setScaleY(-1);
                    btnSort.setTranslationX(1);
                    TooltipCompat.setTooltipText(btnSort, "Ordre croissant");
                }

                refreshRoomFilter();
            }
        });

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final FrameLayout frameLayout = view.findViewById(R.id.frlState);

        // Create the Snackbar
        Snackbar snackbar = Snackbar.make(frameLayout, "", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setAction("Fermer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorTextBtn));

        // Hide the text
        TextView textView = layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = mInflater.inflate(R.layout.my_snackbar, null);

        // Configure the view
        TextView txtCodeLP = snackView.findViewById(R.id.txtCodeLP);
        Functions.setViewBgColorByStatus(txtCodeLP, txtCodeLP.getText().toString());

        TextView txtCodeLS = snackView.findViewById(R.id.txtCodeLS);
        Functions.setViewBgColorByStatus(txtCodeLS, txtCodeLS.getText().toString());

        TextView txtCodeOP = snackView.findViewById(R.id.txtCodeOP);
        Functions.setViewBgColorByStatus(txtCodeOP, txtCodeOP.getText().toString());

        TextView txtCodeOS = snackView.findViewById(R.id.txtCodeOS);
        Functions.setViewBgColorByStatus(txtCodeOS, txtCodeOS.getText().toString());

        TextView txtCodeDA = snackView.findViewById(R.id.txtCodeDA);
        Functions.setViewBgColorByStatus(txtCodeDA, txtCodeDA.getText().toString());

        TextView txtNbRoom = snackView.findViewById(R.id.txtNbRoom);
        GradientDrawable bgShape = (GradientDrawable)txtNbRoom.getBackground();
        int idRessource = App.getColors().get("LS");
        bgShape.setColor(idRessource);
        bgShape.setStroke(1, idRessource);

        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0,0,0,0);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);

        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                isSnackbarClose = true;
            }

            @Override
            public void onShown(Snackbar snackbar) {
                isSnackbarClose = false;
            }
        });

        btnInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSnackbarClose) {
                    snackbar.show();
                } else {
                    snackbar.dismiss();
                }
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateRoomsAdapter.notifyDataSetChanged();
                ((HomeActivity)context).showFilterDialog();
            }
        });

        return view;
    }

    private void getRooms(){
        Call<Push> call = swInterface.getRooms(Functions.getAuth());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if(push.getStatus()==1) {
                        roomsFromDB = gson.fromJson(push.getData(),Rooms.class);
                        refreshRoomFilter();
                        Log.e(Constants._TAG_LOG,"DATA RECIEVE");
                    }
                } else {
                    Log.e(Constants._TAG_LOG,"Erreur : getRooms " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }

    private void getStaff(){
        Call<Push> call = swInterface.getStaff(Functions.getAuth());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if(push.getStatus()==1) {
                        Users staff = gson.fromJson(push.getData(),Users.class);
                        App.setStaff(staff);
                        Log.e(Constants._TAG_LOG,"DATA RECIEVE");
                    }
                } else {
                    Log.e(Constants._TAG_LOG,"Erreur : getStaff " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }


    public void refreshRoomFilter() {
        rooms = new Rooms(roomsFromDB);

        Collections.sort(rooms, isAsc ? new Rooms.SortByNumberAsc() : new Rooms.SortByNumberDesc());

        JsonObject joFilter = Session.getJoRoomFilter();

        if(joFilter != null) {
            if(joFilter.has("roomStatus")) {
                int[] ids = gson.fromJson(Session.getJoRoomFilter().getAsJsonArray("roomStatus"), int[].class);
                rooms = rooms.filterByRoomStatus(ids);
            }

            if(joFilter.has("floor")) {
                int id = joFilter.get("floor").getAsInt();
                Log.e(Constants._TAG_LOG,"Floor: "+id);
                if (id != 0) {
                    rooms = rooms.filterByFloor(id);
                }
            }

            if(joFilter.has("roomType")) {
                int[] ids = gson.fromJson(Session.getJoRoomFilter().getAsJsonArray("roomType"), int[].class);
                rooms = rooms.filterByRoomType(ids);
            }


            if(joFilter.has("assignment")) {
                int id = joFilter.get("assignment").getAsInt();
                Log.e(Constants._TAG_LOG,"Assignment: "+id);
                if (id != 0) {
                    rooms = rooms.filterByAssignment(id);
                }
            }
        }

        strArr = Functions.singlePlural(rooms.size(), " chambre trouvée", " chambres trouvées", "Aucune");
        Functions.setBiColorString(strArr[0], strArr[1], txtResultInfo, App.getColors().get("colorNext"), true);

        stateRoomsAdapter.loadRoom(rooms);
    }

}
