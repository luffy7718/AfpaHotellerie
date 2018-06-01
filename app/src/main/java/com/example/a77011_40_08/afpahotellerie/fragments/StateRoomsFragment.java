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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.StateRoomsAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateRoomsFragment extends Fragment {

    Context context;
    RecyclerView rvwStateRooms;
    ImageButton btnSwitchView;
    Button btnInfos;
    Button btnFilter;

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

        rvwStateRooms = view.findViewById(R.id.rvwStateRooms);
        btnSwitchView = view.findViewById(R.id.btnSwitchView);
        btnInfos = view.findViewById(R.id.btnInfos);
        btnFilter = view.findViewById(R.id.btnFilter);

        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context);
        rvwStateRooms.setLayoutManager(layoutManagerR);

        rvwStateRooms.setAdapter(stateRoomsAdapter);

        btnSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Mon_texte", Toast.LENGTH_LONG).show();
                boolean isSwitched = stateRoomsAdapter.toggleItemViewType();
                rvwStateRooms.setLayoutManager(isSwitched ? new LinearLayoutManager(context) : new GridLayoutManager(context, 4));
                stateRoomsAdapter.notifyDataSetChanged();

                if (isSwitched)
                    btnSwitchView.setImageResource(R.drawable.ic_view_comfy_white_24dp);
                else
                    btnSwitchView.setImageResource(R.drawable.ic_list_white_24dp);
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

        btnInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.show();
            }
        });

        /*btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });*/

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
                        Gson gson = new Gson();
                        Rooms rooms = gson.fromJson(push.getData(),Rooms.class);
                        stateRoomsAdapter.loadRoom(rooms);
                        stateRoomsAdapter.notifyDataSetChanged();
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
                        Gson gson = new Gson();
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

}
