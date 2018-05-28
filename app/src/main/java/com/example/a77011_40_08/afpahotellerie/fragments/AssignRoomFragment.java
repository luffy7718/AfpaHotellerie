package com.example.a77011_40_08.afpahotellerie.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.AssignedStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.adapters.RoomsAssignmentAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.GridSpacingItemDecoration;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignRoomFragment extends Fragment {
    SWInterface swInterface;
    Context context;
    RoomsAssignmentAdapter roomsAssignmentAdapter;
    RoomsAssignmentAdapter unaffectedRoomsAdapter;
    TextView txtName;
    TextView txtFirstName;
    RecyclerView rvwListAssign;
    RecyclerView rvwListUnaffected;
    int numberOfColumns = 5;
    int idStaff;
    User user;

    public AssignRoomFragment() {
        // Required empty public constructor
    }


    public static AssignRoomFragment newInstance(Bundle args) {
        AssignRoomFragment fragment = new AssignRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        HomeActivity home= (HomeActivity) context;
        if (getArguments() != null) {
            if (getArguments().containsKey("user") ) {
                String json = getArguments().getString("user");
                Log.e(Constants._TAG_LOG,"User selected: "+json);
                Gson gson = new Gson();
                user = gson.fromJson(json,User.class);
            }
        }
        swInterface = RetrofitApi.getInterface();
        roomsAssignmentAdapter = new RoomsAssignmentAdapter(true, getActivity());
        unaffectedRoomsAdapter = new RoomsAssignmentAdapter(false, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view=inflater.inflate(R.layout.fragment_assign_room, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtFirstName = view.findViewById(R.id.txtFirstName);
        rvwListAssign = view.findViewById(R.id.rvwListAssign);
        rvwListUnaffected = view.findViewById(R.id.rvwListUnaffected);
        RecyclerView.LayoutManager layoutManagerR2 = new GridLayoutManager(context,
                numberOfColumns);
        RecyclerView.LayoutManager layoutManagerR3 = new GridLayoutManager(context,
                numberOfColumns);
        rvwListAssign.setLayoutManager(layoutManagerR2);
        rvwListUnaffected.setLayoutManager(layoutManagerR3);
        rvwListAssign.setItemAnimator(new DefaultItemAnimator());
        rvwListUnaffected.setItemAnimator(new DefaultItemAnimator());
        rvwListAssign.setAdapter(roomsAssignmentAdapter);
        rvwListUnaffected.setAdapter(unaffectedRoomsAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen
                .recycler_view_item_spacing);
        rvwListAssign.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns,
                spacingInPixels, true, 0));
        rvwListUnaffected.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns,
                spacingInPixels, true, 0));

        selectedUser();
        return view;
    }

    private void getAssignedRooms() {
        Call<Push> call = swInterface.getAssignedRooms(Functions.getAuth(), user.getIdStaff());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Gson gson = new Gson();
                        Rooms rooms = gson.fromJson(push.getData(), Rooms.class);
                        roomsAssignmentAdapter.loadRoom(rooms);
                        roomsAssignmentAdapter.notifyDataSetChanged();
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

    private void getUnassignedRooms() {
        Call<Push> call = swInterface.getUnassignedRooms(Functions.getAuth());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Gson gson = new Gson();
                        Rooms rooms = gson.fromJson(push.getData(), Rooms.class);
                        unaffectedRoomsAdapter.loadRoom(rooms);
                        unaffectedRoomsAdapter.notifyDataSetChanged();
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
    public void transfert(Room room, boolean fromAssigned) {
        if (fromAssigned) {
            unaffectedRoomsAdapter.addRoom(room);
        } else {
            roomsAssignmentAdapter.addRoom(room);
        }
    }

    public void selectedUser(){
        txtName.setText(user.getName());
        txtFirstName.setText(user.getFirstname());
        unaffectedRoomsAdapter.setIdStaff(user.getIdStaff());
        getAssignedRooms();
        getUnassignedRooms();
    }

    public void showRoomPanel(User user, int idStaff) {
        Log.e(Constants._TAG_LOG,"User selected: "+idStaff);
        //isRoomPanel = true;
        txtName.setText(user.getName());
        txtFirstName.setText(user.getFirstname());
        this.idStaff =idStaff;
        unaffectedRoomsAdapter.setIdStaff(idStaff);
        getAssignedRooms();
        getUnassignedRooms();

    }

}
