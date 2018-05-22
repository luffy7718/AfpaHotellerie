package com.example.a77011_40_08.afpahotellerie.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Adapters.ListRoomsAdapter;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatuts;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.example.a77011_40_08.afpahotellerie.Utils.Session;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListRoomsFragment extends Fragment {

    Context context;
    RecyclerView rvwListRooms;
    Rooms myRooms;
    ListRoomsAdapter listRoomsAdapter;
    SWInterface swInterface;


    public ListRoomsFragment() {
        // Required empty public constructor
    }


    public static ListRoomsFragment newInstance(String param1, String param2) {
        ListRoomsFragment fragment = new ListRoomsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swInterface = RetrofitApi.getInterface();
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view=inflater.inflate(R.layout.fragment_list_rooms, container, false);
        rvwListRooms = view.findViewById(R.id.rvwListRooms);
        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rvwListRooms.setLayoutManager(layoutManagerR);
        rvwListRooms.setItemAnimator(new DefaultItemAnimator());
        myRooms=new Rooms();
        listRoomsAdapter=new ListRoomsAdapter(myRooms, getActivity());
        rvwListRooms.setAdapter(listRoomsAdapter);
        getAssignedRooms();

        return view;
    }

    private void getAssignedRooms(){
        Call<Push> call = swInterface.getAssignedRooms(Functions.getAuth(), Session.getMyUser().getIdStaff());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if(push.getStatus()==1) {
                        Gson gson = new Gson();
                        myRooms = gson.fromJson(push.getData(),Rooms.class);
                        Log.e(Constants._TAG_LOG,"LENGTH: "+myRooms.size());
                        Log.e(Constants._TAG_LOG,"0: "+myRooms.get(0).getIdRoom()+" "+myRooms.get(0).getNumber());
                        listRoomsAdapter=new ListRoomsAdapter(myRooms, getActivity());
                        rvwListRooms.setAdapter(listRoomsAdapter);
                        //listRoomsAdapter.notifyDataSetChanged();
                        Log.e(Constants._TAG_LOG,"DATA RECIEVE");
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }



}
