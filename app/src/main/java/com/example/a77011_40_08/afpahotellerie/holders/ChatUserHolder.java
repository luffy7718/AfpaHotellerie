package com.example.a77011_40_08.afpahotellerie.holders;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Session;


public class ChatUserHolder extends RecyclerView.ViewHolder {

    public final TextView txtPseudo;
    public final ImageView imgUser;

    public ChatUserHolder(View view) {
        super(view);

        txtPseudo = view.findViewById(R.id.txtPseudo);
        imgUser = view.findViewById(R.id.imgUser);
    }

    public void setUser(final User user, final Context context) {
        //Bind les donneés à la Card View
        txtPseudo.setText(Session.getMyUser().getFullName());
        imgUser.setTag(Session.getMyUser().getIdStaff());

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUser = (String) v.getTag();
                HomeActivity home = (HomeActivity) context;
                Bundle params = new Bundle();
                   /* Gson gson=new Gson();
                    String json=gson.toJson(prestation.getClass());*/
                params.putString("idUser", idUser);
                params.putString("pseudo",Session.getMyUser().getFullName());
                home.changeFragment(Constants.FRAG_CHAT_PRIVATE, params);

            }
        });
    }
}
