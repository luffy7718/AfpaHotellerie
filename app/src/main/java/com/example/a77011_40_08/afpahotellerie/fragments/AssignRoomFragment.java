package com.example.a77011_40_08.afpahotellerie.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.AssignRoomAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.GridSpacingItemDecoration;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignRoomFragment extends Fragment {
    SWInterface swInterface;
    Context context;
    AssignRoomAdapter assignRoomAdapter;
    AssignRoomAdapter unaffectedRoomsAdapter;
    TextView txtName;
    TextView txtAssignRoom;
    TextView txtUnaffectedRoom;
    Button btnValidate;
    RecyclerView rvwListAssign;
    RecyclerView rvwListUnaffected;
    int numberOfColumns = 4;
    User user;
    Rooms savedRooms;
    Gson gson;
    String singular;
    String singular2;
    String plural;
    String plural2;
    String nothing;
    String[] strArr1;
    String[] strArr2;


    public AssignRoomFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(Constants._TAG_LOG, "BroadcastReceiver topic Assign");
            String topic = intent.getStringExtra("topic");
            Log.e(Constants._TAG_LOG, "Var topic : " + topic);
            if (topic != "") {
                if (topic.equals("roomState")) {
                    Log.e(Constants._TAG_LOG, "RoomsState: " + topic);
                    getAssignedRooms();
                    getUnassignedRooms();
                    Toast.makeText(getActivity(), "mMessageReceiver Assign Update", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e(Constants._TAG_LOG, "RoomState assign : pas de topic");
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(context).registerReceiver((mMessageReceiver),
                new IntentFilter("TopicReceive")
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
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
        gson = new Gson();
        HomeActivity home = (HomeActivity) context;
        if (getArguments() != null) {
            if (getArguments().containsKey("user")) {
                String json = getArguments().getString("user");
                Log.e(Constants._TAG_LOG, "User selected: " + json);
                Gson gson = new Gson();
                user = gson.fromJson(json, User.class);
            }
        }else{
           Log.e(Constants._TAG_LOG,"Pas d'utilisateur");
        }
        swInterface = RetrofitApi.getInterface();
        assignRoomAdapter = new AssignRoomAdapter(true, getActivity());
        unaffectedRoomsAdapter = new AssignRoomAdapter(false, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assign_room, container, false);
        txtName = view.findViewById(R.id.txtName);
        btnValidate = view.findViewById(R.id.btnValidate);
        txtAssignRoom = view.findViewById(R.id.txtAssignRoom);
        txtUnaffectedRoom = view.findViewById(R.id.txtUnaffectedRoom);

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
        rvwListAssign.setAdapter(assignRoomAdapter);
        rvwListUnaffected.setAdapter(unaffectedRoomsAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen
                .recycler_view_item_spacing);
        rvwListAssign.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns,
                spacingInPixels, true, 0));
        rvwListUnaffected.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns,
                spacingInPixels, true, 0));
        selectedUser();

        singular =  " chambre affectée";
        singular2 =  " chambre non affectée";
        plural =  " chambres affectées";
        plural2 =  " chambres non affectées";
        nothing =  "Aucune";


        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Constants._TAG_LOG, "OnStop");
                if (!assignRoomAdapter.compareRooms(savedRooms) && user != null) {
                    Log.e(Constants._TAG_LOG, "Changements detectés");
                    HashMap<String, String> body = new HashMap<>();
                    body.put("title", "Changement d'affectation");
                    body.put("text", "Vous avez de nouvelles affectations.");
                    body.put("update",Constants.FRAG_ROOMS_CLEAN+"");
                    String json = gson.toJson(body);
                    Log.e(Constants._TAG_LOG, "Body: " + json);
                    Call<Push> call = swInterface.sendMessage(Functions.getAuth(), user.getIdStaff(),
                            Session.getMyUser().getIdStaff(), "notification", json);
                    call.enqueue(new Callback<Push>() {
                        @Override
                        public void onResponse(Call<Push> call, Response<Push> response) {
                            if (response.isSuccessful()) {
                                Push push = response.body();
                                if (push.getStatus() == 1) {
                                    Log.e(Constants._TAG_LOG, "Success onStop");
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
                Toast.makeText(getActivity(), "Affectation notifiée et validée", Toast.LENGTH_LONG).show();

                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void getAssignedRooms() {
        Call<Push> call;
        if(user != null){
            call = swInterface.getAssignedRooms(Functions.getAuth(), user.getIdStaff());
        }else{
            call = swInterface.getAssignedRooms(Functions.getAuth(), 0);
        }

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Rooms rooms = gson.fromJson(push.getData(), Rooms.class);
                        savedRooms = gson.fromJson(push.getData(), Rooms.class);

                        assignRoomAdapter.loadRoom(rooms);
                        assignRoomAdapter.notifyDataSetChanged();
                        Log.e(Constants._TAG_LOG, "DATA RECIEVE");
                        strArr1 = Functions.singlePlural(assignRoomAdapter.getItemCount(), singular, plural, nothing);
                        Functions.setBiColorString(strArr1[0], strArr1[1], txtAssignRoom, App.getColors().get("colorNext"), true);
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
                        Rooms rooms = gson.fromJson(push.getData(), Rooms.class);
                        unaffectedRoomsAdapter.loadRoom(rooms);
                        unaffectedRoomsAdapter.notifyDataSetChanged();
                        strArr2 = Functions.singlePlural(unaffectedRoomsAdapter.getItemCount(), singular2, plural2, nothing);
                        Functions.setBiColorString(strArr2[0], strArr2[1], txtUnaffectedRoom, App.getColors().get("colorNext"), true);
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

    public void transfert(Room room, boolean fromAssigned, int roomsCount) {
        if (fromAssigned) {
            unaffectedRoomsAdapter.addRoom(room);
            strArr1 = Functions.singlePlural(roomsCount, singular, plural, nothing);
            Functions.setBiColorString(strArr1[0], strArr1[1], txtAssignRoom, App.getColors().get("colorNext"), true);
            strArr2 = Functions.singlePlural(unaffectedRoomsAdapter.getItemCount(), singular2, plural2, nothing);
            Functions.setBiColorString(strArr2[0], strArr2[1], txtUnaffectedRoom, App.getColors().get("colorNext"), true);
        } else {
            assignRoomAdapter.addRoom(room);
            strArr2 = Functions.singlePlural(roomsCount, singular2, plural2, nothing);
            Functions.setBiColorString(strArr2[0], strArr2[1], txtUnaffectedRoom, App.getColors().get("colorNext"), true);
            strArr1 = Functions.singlePlural(assignRoomAdapter.getItemCount(), singular, plural, nothing);
            Functions.setBiColorString(strArr1[0], strArr1[1], txtAssignRoom, App.getColors().get("colorNext"), true);
        }
    }

    public void selectedUser() {
        if(user != null){
            txtName.setText(user.getFullName());
            unaffectedRoomsAdapter.setIdStaff(user.getIdStaff());
        }else{
            Log.e(Constants._TAG_LOG,"Pas d'utilisateur selectionné");
        }
        getAssignedRooms();
        getUnassignedRooms();
    }


}
