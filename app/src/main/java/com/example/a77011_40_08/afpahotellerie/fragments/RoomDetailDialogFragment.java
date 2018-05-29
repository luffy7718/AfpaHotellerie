package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.RoomType;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;


public class RoomDetailDialogFragment extends DialogFragment {

    TextView txtTitleBar;
    TextView txtAbbreviation;
    TextView txtRoomType;
    TextView txtStatus;
    FrameLayout frlClose;
    ImageView imgDetailPhoto;
    Room room;
    RoomStatut roomStatut;

    public RoomDetailDialogFragment() {
        // Required empty public constructor
    }

    public static RoomDetailDialogFragment newInstance() {
        RoomDetailDialogFragment fragment = new RoomDetailDialogFragment();
        return fragment;
    }

    public void setDetailRoom(Room room, RoomStatut roomStatut) {
        this.room = room;
        this.roomStatut = roomStatut;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_room_detail_dialog, container, false);

        //String path = Constants.URL_SW + Constants.PHOTOS_FOLDER + detailPhoto.getPath();

        frlClose = root.findViewById(R.id.frlClose);
        txtTitleBar = root.findViewById(R.id.txtTitleBar);
        txtAbbreviation = root.findViewById(R.id.txtAbbreviation);
        txtRoomType = root.findViewById(R.id.txtRoomType);
        txtStatus = root.findViewById(R.id.txtStatus);
        imgDetailPhoto = root.findViewById(R.id.imgDetailPhoto);

        imgDetailPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*String path2 = Constants.URL_SW + Constants.PHOTOS_FOLDER + detailPhoto.getPath();
                Intent intent = new Intent(getActivity(),FullScreenImageActivity.class);
                intent.putExtra("path", path2);
                startActivity(intent);*/
            }
        });

        frlClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        frlClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        txtAbbreviation.setText(roomStatut.getAbbreviation());

        Functions.setViewBgColorByStatus(txtAbbreviation, roomStatut.getAbbreviation());



        /*String first = "Status : ";
        String next = "";

        for(RoomType roomType : App.getRoomsTypes()) {
            if(roomType.getIdRoomType() == (room.getIdRoomType())) {
                next = roomType.getName();
            }
        }

        txtStatus.setText(first + next, TextView.BufferType.SPANNABLE);
        Spannable s = (Spannable)txtStatus.getText();
        int start = first.length();
        int end = start + next.length();
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/





        /*String first = "Type : ";*/
        String type = "";

        for(RoomType roomType : App.getRoomsTypes()) {
            if(roomType.getIdRoomType() == (room.getIdRoomType())) {
                type = roomType.getName();
            }
        }
        Functions.setBiColorString("Type : ", type, txtRoomType, ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        /*txtRoomType.setText(first + next, TextView.BufferType.SPANNABLE);
        Spannable s = (Spannable)txtRoomType.getText();
        int start = first.length();
        int end = start + next.length();
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/




        /*txtStatus.setText(first + next, TextView.BufferType.SPANNABLE);
        Spannable s = (Spannable)txtStatus.getText();
        int start = first.length();
        int end = start + next.length();
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/



        txtTitleBar.setText("Chambre " + room.getNumber());


        //txtDetailTitle.setText("" + room.getNumber());




        //Functions.createServerRoundedImage(getActivity(), imgDetailPhoto, detailPhoto.getPath(), 500, 500, "");


        /*public int getDrawableResIdByName(String resName) {
            String pkgName = context.getPackageName();
            int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
            return resID;
        }*/



        /*Picasso.with(getActivity()).load(path)
                //.transform(transformation)
                .fit()
                //.centerInside()
                //.resize(300, -1)
                .centerCrop()
                .into(imgDetailPhoto);*/

        //imgDetailPhoto.setImageResource(detailPhoto.getId());

        return root;
    }

    // Calcule la hauteur et largeur du Dialog Fragment
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        //window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout((int) (width * 0.99), (int) (height * 0.96));
        window.setGravity(Gravity.CENTER);
    }

}
