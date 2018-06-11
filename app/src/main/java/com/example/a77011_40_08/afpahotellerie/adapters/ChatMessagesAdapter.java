package com.example.a77011_40_08.afpahotellerie.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.holders.ChatMessageHolder;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.ChatMessage;
import com.example.a77011_40_08.afpahotellerie.models.ChatMessages;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatMessagesAdapter
        extends RecyclerView.Adapter<ChatMessageHolder> {

    ChatMessages chatMessages;
    Context context;
    String idDevice;
    String idUser;
    SWInterface swInterface;

    User user;

    public ChatMessagesAdapter(Context context, String idDevice, String idUser) {
        this.chatMessages = new ChatMessages();
        this.context = context;
        this.idDevice = idDevice;
        this.idUser = idUser;
    }

    @Override
    public ChatMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        swInterface = RetrofitApi.getInterface();
        return new ChatMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatMessageHolder chatMessageHolder, int position) {

        ChatMessage chatMessage = chatMessages.get(position);
        chatMessageHolder.setMessage(chatMessage, context, idDevice);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void loadMessages() {
        // String idDevice = Functions.getPreferenceString(getApplicationContext(),"idDevice");

        Call<Push> call = swInterface.getMessagesChat(Functions.getAuth(), Integer.parseInt
                (idDevice), Session.getMyUser().getIdStaff());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Gson gson = new Gson();


                        //textView.setText(push.getName());
                        Log.e("TAG ", "[status:" + push.getStatus() + ", type:" + push.getType()
                                + ", data:" + push.getData()
                                + "]");
                    } else {
                        Log.e(Constants._TAG_LOG, "push.getdata = " + push.getData());

                    }
                    //Toast.makeText(context, "name= " + push.getName(), Toast.LENGTH_LONG)
                    // .show();
                } else {
                    //todo:gérer les code erreur de retour
                    Log.e(Constants._TAG_LOG, response.toString());
                }

            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {
                Log.e("error", "");

            }
        });
    }


    public void addMessage(String message, String pseudo) {


        Gson gson = new Gson();
        HashMap<String, String> body = new HashMap<>();
        body.put("title", "message de:" + pseudo);
        body.put("text", "Vous avez de nouveaux messages."+message);
        body.put("update", Constants._FRAG_HOME+ "");
        String json = gson.toJson(body);
        Log.e(Constants._TAG_LOG, "Body: " + json);
        Call<Push> call = swInterface.sendMessage(Functions.getAuth(), user.getIdStaff(),
                Session.getMyUser().getIdStaff(), "message", json);
        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Log.e(Constants._TAG_LOG, "Success ");
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

    public void addMessageReceive(String message) {
        Date date = new Date();

        Log.e("TAG", "idStaff=" + idDevice + " idUser=" + idUser + " message" + message);

        final ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setIdFrom(idUser);
        chatMessage.setIdTo(idDevice);
        chatMessage.setDate(date.toString());
        chatMessages.add(chatMessage);
        notifyDataSetChanged();
    }

}