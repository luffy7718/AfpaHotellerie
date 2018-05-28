package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.StateRoomsAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.R;
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

    StateRoomsAdapter stateRoomsAdapter;
    SWInterface swInterface;

    public StateRoomsFragment() {
        // Required empty public constructor
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
        //RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager layoutManagerR = new GridLayoutManager(context, 1);
        rvwStateRooms.setLayoutManager(layoutManagerR);
        //rvwStateRooms.setItemAnimator(new DefaultItemAnimator());

        rvwStateRooms.setAdapter(stateRoomsAdapter);
        
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
                        /*stateRoomsAdapter.loadStaff(staff);
                        stateRoomsAdapter.notifyDataSetChanged();*/
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
