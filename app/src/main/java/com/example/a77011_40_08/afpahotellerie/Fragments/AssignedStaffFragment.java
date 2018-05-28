package com.example.a77011_40_08.afpahotellerie.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Adapters.RoomsAssignmentAdapter;
import com.example.a77011_40_08.afpahotellerie.Adapters.AssignedStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.Room;
import com.example.a77011_40_08.afpahotellerie.Models.Rooms;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.Models.Users;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Functions;
import com.example.a77011_40_08.afpahotellerie.Utils.GridSpacingItemDecoration;
import com.example.a77011_40_08.afpahotellerie.Utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignedStaffFragment extends Fragment {
    Context context;
    RecyclerView rvwListStaff;
    SWInterface swInterface;
    AssignedStaffAdapter assignedStaffAdapter;
    RoomsAssignmentAdapter roomsAssignmentAdapter;
    RoomsAssignmentAdapter unaffectedRoomsAdapter;
    TextView txtAvailable;
    TextView txtUnaffected;
    TextView txtName;
    TextView txtFirstName;
    ViewSwitcher vswAssign;
    AssignedStaffFragment assignedStaffFragment;
    RecyclerView rvwListAssign;
    RecyclerView rvwListUnaffected;
    int idStaff;
    int numberOfColumns = 5;

    boolean isRoomPanel = false;

    public AssignedStaffFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swInterface = RetrofitApi.getInterface();
        context = getActivity();
        assignedStaffAdapter = new AssignedStaffAdapter(assignedStaffFragment);
        roomsAssignmentAdapter = new RoomsAssignmentAdapter(true, getActivity());
        unaffectedRoomsAdapter = new RoomsAssignmentAdapter(false, getActivity());
        getSubordinates();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        rvwListStaff = view.findViewById(R.id.rvwListStaff);
        rvwListAssign = view.findViewById(R.id.rvwListAssign);
        rvwListUnaffected = view.findViewById(R.id.rvwListUnaffected);
        vswAssign = view.findViewById(R.id.vswAssign);
        txtName = view.findViewById(R.id.txtName);
        txtFirstName = view.findViewById(R.id.txtFirstName);
        txtAvailable = view.findViewById(R.id.txtAvailable);
        txtUnaffected = view.findViewById(R.id.txtUnaffected);

        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        rvwListStaff.setLayoutManager(layoutManagerR);
        RecyclerView.LayoutManager layoutManagerR2 = new GridLayoutManager(context,
                numberOfColumns);
        RecyclerView.LayoutManager layoutManagerR3 = new GridLayoutManager(context,
                numberOfColumns);
        rvwListAssign.setLayoutManager(layoutManagerR2);
        rvwListUnaffected.setLayoutManager(layoutManagerR3);
        rvwListStaff.setItemAnimator(new DefaultItemAnimator());
        rvwListAssign.setItemAnimator(new DefaultItemAnimator());
        rvwListUnaffected.setItemAnimator(new DefaultItemAnimator());
        rvwListStaff.setAdapter(assignedStaffAdapter);
        rvwListAssign.setAdapter(roomsAssignmentAdapter);
        rvwListUnaffected.setAdapter(unaffectedRoomsAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen
                .recycler_view_item_spacing);
        rvwListAssign.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns,
                spacingInPixels, true, 0));
        rvwListUnaffected.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns,
                spacingInPixels, true, 0));
        txtAvailable.setText("agents disponibles");
        txtUnaffected.setText("sans affectations");


        return view;

    }

    public boolean doBack() {
        if (isRoomPanel) {
            vswAssign.showPrevious();
            isRoomPanel = false;
            return false;
        } else {
            return true;
        }
    }

    private void getSubordinates() {
        Call<Push> call = swInterface.getSubordinates(Functions.getAuth(), Session.getMyUser()
                .getIdStaff());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Gson gson = new Gson();
                        Users users = gson.fromJson(push.getData(), Users.class);
                        assignedStaffAdapter.loadStaff(users);
                        assignedStaffAdapter.notifyDataSetChanged();
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

    private void getAssignedRooms() {
        Call<Push> call = swInterface.getAssignedRooms(Functions.getAuth(), idStaff);

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

    public void showRoomPanel(User user, int idStaff) {
        Log.e(Constants._TAG_LOG,"User selected: "+idStaff);
        isRoomPanel = true;
        vswAssign.showNext();
        txtName.setText(user.getName());
        txtFirstName.setText(user.getFirstname());
        this.idStaff =idStaff;
        unaffectedRoomsAdapter.setIdStaff(idStaff);
        getAssignedRooms();
        getUnassignedRooms();

    }

    public void transfert(Room room, boolean fromAssigned) {
        if (fromAssigned) {
            unaffectedRoomsAdapter.addRoom(room);
        } else {
            roomsAssignmentAdapter.addRoom(room);
        }
    }


}
