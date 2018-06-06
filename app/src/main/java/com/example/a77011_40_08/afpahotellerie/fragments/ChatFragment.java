package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.ChatUsersAdapter;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.ChatMessage;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.GenericAlertDialog;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    RecyclerView lstUsers;
    Button btnRefresh;
    Context context;
    ChatUsersAdapter chatUsersAdapter;
    String idDevice;
    int id;
    SWInterface swInterface;
    GenericAlertDialog genericAlertDialog;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ChatMessage chatMessage = Parcels.unwrap(intent.getParcelableExtra("chatMessage"));

            createNotification(chatMessage);

        }
    };

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        id = 0;
        idDevice = Functions.getPreferenceString(getActivity().getApplicationContext(), "idDevice");
        swInterface = RetrofitApi.getInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        lstUsers = view.findViewById(R.id.lstUsers);
        btnRefresh = view.findViewById(R.id.btnRefresh);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatUsersAdapter.loadUsers();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayout
                .VERTICAL, false);

        lstUsers.setLayoutManager(layoutManager);
        //Effet sur le recyclerView
        lstUsers.setItemAnimator(new DefaultItemAnimator());

        chatUsersAdapter = new ChatUsersAdapter(context, Functions.getPreferenceString(context,
                "idStaff"));
        lstUsers.setAdapter(chatUsersAdapter);
        chatUsersAdapter.loadUsers();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        chatUsersAdapter.loadUsers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (isRemoving()) {
            // onBackPressed()
            alertDialog();
        }
    }


    private void deleteChat() {


        getStaff();
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


    private void alertDialog() {
        genericAlertDialog =
                new GenericAlertDialog(getActivity(), "Quitter !", "quitter le chat ?",
                        null, new GenericAlertDialog.CallGenericAlertDialog() {
                    @Override
                    public void validate() {
                        deleteChat();
                    }
                });
    }

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

    public void createNotification(ChatMessage chatMessage) {


        HomeActivity home = (HomeActivity) context;
        Bundle params = new Bundle();
        params.putString("idUser", chatMessage.getIdFrom());
        params.putString("pseudo", chatMessage.getPseudo());
        home.changeFragment(Constants.FRAG_CHAT_PRIVATE, params);

        PendingIntent pIntent = PendingIntent.getActivity(context,
                (int) System.currentTimeMillis(), new Intent(), PendingIntent.FLAG_ONE_SHOT);

        // Build notification
        // Actions are just fake
        Notification notification = new Notification.Builder(context)
                .setContentTitle(chatMessage.getPseudo())
                .setContentText(chatMessage.getMessage())
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pIntent)
                .setGroup("ssd")
                .build();

        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(id++, notification);

    }
}
