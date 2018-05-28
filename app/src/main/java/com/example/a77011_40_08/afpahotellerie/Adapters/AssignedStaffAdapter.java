package com.example.a77011_40_08.afpahotellerie.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.a77011_40_08.afpahotellerie.Fragments.AssignedStaffFragment;
import com.example.a77011_40_08.afpahotellerie.Holders.AssignedStaffHolder;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.Models.Users;
import com.example.a77011_40_08.afpahotellerie.R;

public class AssignedStaffAdapter extends RecyclerView.Adapter<AssignedStaffHolder> {

    Users users;
    AssignedStaffFragment assignedStaffFragment;

    public AssignedStaffAdapter(AssignedStaffFragment assignedStaffFragment) {

        this.assignedStaffFragment = assignedStaffFragment;

    }


    @Override
    public AssignedStaffHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_staffs, parent, false);
        return new AssignedStaffHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignedStaffHolder holder, int position) {
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
