package com.example.a77011_40_08.afpahotellerie.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.AssignStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignStaffFragment extends Fragment {
    Context context;
    RecyclerView rvwListStaff;
    TextView txtAvailable;
    TextView txtResultInfo;
    Button btnUnassignment;
    AssignStaffFragment assignStaffFragment;
    AssignStaffAdapter assignStaffAdapter;
    SWInterface swInterface;

    public AssignStaffFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swInterface = RetrofitApi.getInterface();
        context = getActivity();
        assignStaffAdapter = new AssignStaffAdapter(assignStaffFragment);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_assign_staff, container, false);
        rvwListStaff = view.findViewById(R.id.rvwListStaff);
        getSubordinates();
        txtResultInfo = view.findViewById(R.id.txtResultInfo);
        btnUnassignment = view.findViewById(R.id.btnUnassignment);

        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        rvwListStaff.setLayoutManager(layoutManagerR);
        rvwListStaff.setItemAnimator(new DefaultItemAnimator());

        rvwListStaff.setAdapter(assignStaffAdapter);

        btnUnassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllAssignment();
            }
        });

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
                        assignStaffAdapter.loadStaff(users);
                        String[] strArr = Functions.singlePlural(assignStaffAdapter.getItemCount(), " agent disponible", " agents disponibles", "Aucun");
                        Functions.setBiColorString(strArr[0], strArr[1], txtResultInfo, App.getColors().get("colorNext"), true);
                        assignStaffAdapter.notifyDataSetChanged();
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

    private void deleteAllAssignment() {
        Call<Push> call = swInterface.deleteAllAssignment(Functions.getAuth());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Toast.makeText(getActivity(), "Désaffectation complète effectuée avec succès", Toast.LENGTH_LONG).show();
                        getSubordinates();
                    }
                } else {
                    Toast.makeText(getActivity(), "Erreur lors de la désaffectation complète, veuillez réessayer.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });

    }


}
