package com.example.a77011_40_08.afpahotellerie.Holders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.Activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.Activities.RetrofitApi;
import com.example.a77011_40_08.afpahotellerie.Adapters.AssignedStaffAdapter;
import com.example.a77011_40_08.afpahotellerie.Fragments.AssignedStaffFragment;
import com.example.a77011_40_08.afpahotellerie.Interface.SWInterface;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.R;


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
                AssignedStaffFragment parent = ((AssignedStaffFragment) ((HomeActivity) context)
                        .getLastFragment());
                parent.showRoomPanel(user,user.getIdStaff());

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
