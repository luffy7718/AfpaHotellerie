package com.example.a77011_40_08.afpahotellerie.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.holders.ChatUserHolder;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatUsersAdapter
        extends RecyclerView.Adapter<ChatUserHolder> {

    Users users;
    Context context;
    String idStaff;
    SWInterface swInterface;
    public ChatUsersAdapter(Context context, String idStaff) {
        this.users = new Users();
        this.context = context;
        this.idStaff = idStaff;
    }

    @Override
    public ChatUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        swInterface = RetrofitApi.getInterface();
        return new ChatUserHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatUserHolder chatUserHolder, int position) {

        User user = users.get(position);
        chatUserHolder.setUser(user, context);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void loadUsers() {
        callgetStaff();
    }
    private void callgetStaff() {

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