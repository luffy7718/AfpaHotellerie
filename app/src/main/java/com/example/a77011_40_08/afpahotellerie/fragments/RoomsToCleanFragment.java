package com.example.a77011_40_08.afpahotellerie.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.RoomsToCleanAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoomsToCleanFragment extends Fragment {

    Context context;
    RecyclerView rvwListRooms;
    TextView txtRoomsCount;
    RoomsToCleanAdapter roomsToCleanAdapter;
    SWInterface swInterface;

    public RoomsToCleanFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int idFrom = intent.getIntExtra("idFragment", -1);
            if (idFrom != -1) {
                if (idFrom == Constants.FRAG_ROOMS_CLEAN) {
                    Log.e(Constants._TAG_LOG, "RoomsToClean: " + idFrom);
                    //TODO update
                    getRoomsToClean();


                }
            } else {
                Log.e(Constants._TAG_LOG, "RoomsToClean: pas d'idFrom");
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(context).registerReceiver((mMessageReceiver),
                new IntentFilter("MessageReceive")
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swInterface = RetrofitApi.getInterface();
        context = getActivity();

        roomsToCleanAdapter = new RoomsToCleanAdapter(getActivity());

        getRoomsToClean();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms_to_clean, container, false);

        txtRoomsCount = view.findViewById(R.id.txtRoomsCount);
        roomsToCleanAdapter.setRoomsCountDisplay(txtRoomsCount);
        txtRoomsCount.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        rvwListRooms = view.findViewById(R.id.rvwListRooms);
        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        rvwListRooms.setLayoutManager(layoutManagerR);
        rvwListRooms.setItemAnimator(new DefaultItemAnimator());

        rvwListRooms.setAdapter(roomsToCleanAdapter);

        return view;
    }

    private void getRoomsToClean() {
        Call<Push> call = swInterface.getRoomsToClean(Functions.getAuth(), Session.getMyUser()
                .getIdStaff());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Gson gson = new Gson();
                        Rooms rooms = gson.fromJson(push.getData(), Rooms.class);
                        txtRoomsCount.setText(rooms.size() + "");
                        roomsToCleanAdapter.loadRoom(rooms);
                        roomsToCleanAdapter.notifyDataSetChanged();
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


}
