package com.example.a77011_40_08.afpahotellerie.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.models.ChatMessage;


public class ChatMessageHolder extends RecyclerView.ViewHolder {

    public final TextView txtMessage;
    public final LinearLayout lltItemMessage;

    public ChatMessageHolder(View view) {
        super(view);
        lltItemMessage = view.findViewById(R.id.lltItemMessage);
        txtMessage = view.findViewById(R.id.txtMessage);
    }

    public void setMessage(final ChatMessage chatMessage, final Context context, String idDevice) {
        //Bind les donneés à la Card View
        if (idDevice.equals(chatMessage.getIdFrom())) {
            lltItemMessage.setGravity(Gravity.RIGHT);
        } else {
            lltItemMessage.setGravity(Gravity.LEFT);
        }
        txtMessage.setText(chatMessage.getMessage());
    }
}
