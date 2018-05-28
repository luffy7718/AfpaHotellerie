package com.example.a77011_40_08.afpahotellerie.holders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.adapters.AssignedStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.fragments.AssignRoomFragment;
import com.example.a77011_40_08.afpahotellerie.fragments.AssignedStaffFragment;
import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.google.gson.Gson;


public class AssignedStaffHolder extends RecyclerView.ViewHolder {
    public final TextView txtName;
    public final TextView txtFirstName;
    public final TextView txtAssign;
    SWInterface swInterface;
    User user;
    int position;
    AssignedStaffAdapter parent;
    public final CardView cv;

    public AssignedStaffHolder(View view) {
        super(view);
        Context context = (Activity) view.getContext();
        swInterface = RetrofitApi.getInterface();
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtFirstName = (TextView) view.findViewById(R.id.txtFirstName);
        txtAssign = (TextView) view.findViewById(R.id.txtAssign);
        cv = (CardView) view.findViewById(R.id.cv);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home= (HomeActivity) context;
                Bundle args = new Bundle();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                args.putString("user",json);
                home.changeFragment(Constants.FRAG_ASSIGNED_ROOM,args);
            }
        });
    }

    public void setUser(final User user, int position, AssignedStaffAdapter parent) {
        this.parent = parent;
        this.user = user;
        this.position = position;
        txtName.setText(user.getName());
        txtFirstName.setText(user.getFirstname());
        txtAssign.setText("affections");


    }

}
