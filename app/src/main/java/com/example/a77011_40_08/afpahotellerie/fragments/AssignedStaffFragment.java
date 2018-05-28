package com.example.a77011_40_08.afpahotellerie.fragments;


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

import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.RoomsAssignmentAdapter;
import com.example.a77011_40_08.afpahotellerie.adapters.AssignedStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.GridSpacingItemDecoration;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignedStaffFragment extends Fragment {
    Context context;
    RecyclerView rvwListStaff;
    TextView txtAvailable;
    TextView txtUnaffected;
    AssignedStaffFragment assignedStaffFragment;
    AssignedStaffAdapter assignedStaffAdapter;
    SWInterface swInterface;

    public AssignedStaffFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swInterface = RetrofitApi.getInterface();
        context = getActivity();
        assignedStaffAdapter = new AssignedStaffAdapter(assignedStaffFragment);
        getSubordinates();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_assign_staff, container, false);
        rvwListStaff = view.findViewById(R.id.rvwListStaff);


        txtAvailable = view.findViewById(R.id.txtAvailable);
        txtUnaffected = view.findViewById(R.id.txtUnaffected);

        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        rvwListStaff.setLayoutManager(layoutManagerR);
        rvwListStaff.setItemAnimator(new DefaultItemAnimator());

        rvwListStaff.setAdapter(assignedStaffAdapter);


        txtAvailable.setText("agents disponibles");
        txtUnaffected.setText("sans affectations");


        return view;

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








}
