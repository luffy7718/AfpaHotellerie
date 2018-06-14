package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.models.Floor;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.RoomType;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;


public class RoomDetailDialogFragment extends DialogFragment {

    TextView txtTitleBar;
    TextView txtAbbreviation;
    TextView txtStatus;
    TextView txtAssignment;
    TextView txtRoomType;
    TextView txtRoomBeds;
    TextView txtFloor;
    FrameLayout frlClose;
    ImageView imgDetailPhoto;
    Room room;
    RoomStatut roomStatut;
    User staff;

    public RoomDetailDialogFragment() {
        // Required empty public constructor
    }

    public static RoomDetailDialogFragment newInstance() {
        RoomDetailDialogFragment fragment = new RoomDetailDialogFragment();
        return fragment;
    }

    public void setDetailRoom(Room room, RoomStatut roomStatut, User staff) {
        this.room = room;
        this.roomStatut = roomStatut;
        this.staff = staff;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_room_detail_dialog, container, false);

        int txtNextColor = ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark);

        //String path = Constants.URL_SW + Constants.PHOTOS_FOLDER + detailPhoto.getPath();

        frlClose = root.findViewById(R.id.frlClose);
        txtTitleBar = root.findViewById(R.id.txtTitleBar);
        txtAbbreviation = root.findViewById(R.id.txtAbbreviation);
        txtStatus = root.findViewById(R.id.txtStatus);
        txtAssignment = root.findViewById(R.id.txtAssignment);
        txtRoomType = root.findViewById(R.id.txtRoomType);
        txtRoomBeds = root.findViewById(R.id.txtRoomBeds);
        txtFloor = root.findViewById(R.id.txtFloor);
        imgDetailPhoto = root.findViewById(R.id.imgDetailPhoto);

        //imgDetailPhoto.setImageResource(R.drawable.room);

        /*imgDetailPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*String path2 = Constants.URL_SW + Constants.PHOTOS_FOLDER + detailPhoto.getPath();
                Intent intent = new Intent(getActivity(),FullScreenImageActivity.class);
                intent.putExtra("path", path2);
                startActivity(intent);
            }
        });*/

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

        String status = roomStatut.getAbbreviation();
        String statusFullName = roomStatut.getName();

        switch(status){
            case "LE":
                status = "LS";
                statusFullName = "Libre Sale / En cours...";
                break;
            case "OE":
                status = "OS";
                statusFullName = "Occupée Sale / En cours...";
                break;
        }

        Functions.setBiColorString("Statut : ", statusFullName, txtStatus, txtNextColor, false);

        String srtStaff = "";
        if (staff == null) {
            srtStaff = "Pas d'affectation";
        } else {
            srtStaff = staff.getFullName();
        }
        Functions.setBiColorString("Affectation : ", srtStaff, txtAssignment, txtNextColor, false);

        txtAbbreviation.setText(status);

        Functions.setViewBgColorByStatus(txtAbbreviation, status);


        String type = "";
        for(RoomType roomType : App.getRoomsTypes()) {
            if(roomType.getIdRoomType() == room.getIdRoomType()) {
                type = roomType.getName();
            }
        }
        Functions.setBiColorString("Type : ", type, txtRoomType, txtNextColor, false);

        String srtBeds = "";
        for(RoomType roomType : App.getRoomsTypes()) {
            if(roomType.getIdRoomType() == room.getIdRoomType()) {
                srtBeds = ""+roomType.getBeds();
            }
        }

        Functions.setBiColorString("Nombre de lits : ", srtBeds, txtRoomBeds, txtNextColor, false);

        String srtFloor = "";
        for(Floor floor : App.getFloors()) {
            Log.e(Constants._TAG_LOG, floor.getIdFloor() + " = " + room.getIdFloor());
            if(floor.getIdFloor() == room.getIdFloor()) {
                srtFloor = ""+floor.getNumber();
                if(floor.getNumber() < 1) {
                    srtFloor = floor.getName();
                }
            }
        }
        Functions.setBiColorString("Étage : ", srtFloor, txtFloor, txtNextColor, false);

        txtTitleBar.setText("Chambre " + room.getNumber());

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
