package com.example.a77011_40_08.afpahotellerie.holders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.AssignStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.fragments.AssignStaffFragment;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.Rooms;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssignStaffHolder extends RecyclerView.ViewHolder {
    public final TextView txtName;
    public final TextView txtFirstName;
    public final TextView txtAssign;
    SWInterface swInterface;
    User user;
    int position;
    AssignStaffAdapter parent;
    public final CardView cv;
    AssignStaffFragment assignStaffFragment;
    Gson gson;
    AssignStaffAdapter assignStaffAdapter;

    public AssignStaffHolder(View view) {
        super(view);
        Context context = (Activity) view.getContext();
        swInterface = RetrofitApi.getInterface();
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtFirstName = (TextView) view.findViewById(R.id.txtFirstName);
        txtAssign = (TextView) view.findViewById(R.id.txtAssign);
        cv = (CardView) view.findViewById(R.id.cv);
        gson = new Gson();
        assignStaffAdapter = new AssignStaffAdapter(assignStaffFragment);


        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home = (HomeActivity) context;
                Bundle args = new Bundle();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                args.putString("user", json);
                home.changeFragment(Constants.FRAG_ASSIGNED_ROOM, args);
            }
        });
    }

    public void setUser(final User user, int position, AssignStaffAdapter parent) {
        this.parent = parent;
        this.user = user;
        this.position = position;
        txtName.setText(user.getName());
        txtFirstName.setText(user.getFirstname());
        txtAssign.setText("" + user.getRoomsAssigned());
    }


}
