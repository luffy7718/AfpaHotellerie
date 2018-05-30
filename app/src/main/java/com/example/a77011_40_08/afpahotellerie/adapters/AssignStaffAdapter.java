package com.example.a77011_40_08.afpahotellerie.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.a77011_40_08.afpahotellerie.fragments.AssignStaffFragment;
import com.example.a77011_40_08.afpahotellerie.holders.AssignStaffHolder;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.models.Users;
import com.example.a77011_40_08.afpahotellerie.R;

public class AssignStaffAdapter extends RecyclerView.Adapter<AssignStaffHolder> {

    Users users;
    AssignStaffFragment assignStaffFragment;

    public AssignStaffAdapter(AssignStaffFragment assignStaffFragment) {

        this.assignStaffFragment = assignStaffFragment;

    }


    @Override
    public AssignStaffHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_staffs, parent, false);
        return new AssignStaffHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignStaffHolder holder, int position) {
        User user = users.get(position);
        holder.setUser(user, position, this);
    }

    @Override
    public int getItemCount() {

        if (users == null) {
            return 0;
        } else {
            return users.size();
        }
    }

    public void loadStaff(Users users) {
        this.users = users;
    }


}
