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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.adapters.ChatMessagesAdapter;
import com.example.a77011_40_08.afpahotellerie.models.ChatMessage;
import com.example.a77011_40_08.afpahotellerie.models.ChatUser;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.Session;

import org.parceler.Parcels;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */

public class ChatPrivateMessagesFragment extends Fragment {
    RecyclerView lstMessages;
    Button btnSend;
    Context context;
    ChatMessagesAdapter chatMessagesAdapter;
    String idDevice;
    String iduser;
    EditText txtMessage;
    TextView txtPseudo;
    String pseudo;
    ChatUser myChatUser;
    int idNotification;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ChatMessage chatMessage = Parcels.unwrap(intent.getParcelableExtra("chatMessage"));

            String idFrom = chatMessage.getIdFrom();

            if (idFrom.equals(iduser)) {

                String message = chatMessage.getMessage();
                Log.e("TAG", "Message: " + message);

                chatMessagesAdapter.addMessageReceive(message);

            } else {

                createNotification(chatMessage);
                //Toast.makeText(context,"From "+idFrom+": "+intent.getStringExtra("message"),
                // Toast.LENGTH_LONG).show();
            }
        }
    };


    public static ChatPrivateMessagesFragment newInstance(Bundle args) {
        ChatPrivateMessagesFragment fragment = new ChatPrivateMessagesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_private_messages, container, false);
        lstMessages = view.findViewById(R.id.lstMessages);
        txtMessage = view.findViewById(R.id.txtMessage);
        txtPseudo = view.findViewById(R.id.txtPseudo);
        btnSend = view.findViewById(R.id.btnSend);
        txtPseudo.setText(pseudo);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = txtMessage.getText().toString();

                if (!message.isEmpty()) {
                    txtMessage.setText("");
                    chatMessagesAdapter.addMessage(message, myChatUser.getPseudo());
                }
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayout
                .VERTICAL, false);

        lstMessages.setLayoutManager(layoutManager);
        //Effet sur le recyclerView
        lstMessages.setItemAnimator(new DefaultItemAnimator());

        chatMessagesAdapter = new ChatMessagesAdapter(context, idDevice, iduser);
        lstMessages.setAdapter(chatMessagesAdapter);
        chatMessagesAdapter.loadMessages();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = getActivity();
        idDevice = Functions.getPreferenceString(context, "idDevice");
        idNotification = 0;

        myChatUser = Session.getMyChatUser();

        if (getArguments() != null) {
            if (getArguments().containsKey("idUser") && getArguments().containsKey("pseudo")) {
                iduser = getArguments().getString("idUser");
                pseudo = getArguments().getString("pseudo");
            }
        }
        // Intent intent = getIntent();
        // iduser = intent.getStringExtra("idUser");
        //pseudo = intent.getStringExtra("pseudo");


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

    @Override
    public void onPause() {
        super.onPause();
        getActivity().finish();
    }

    public void createNotification(ChatMessage chatMessage) {


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

        notificationManager.notify(idNotification++, notification);

    }
}

