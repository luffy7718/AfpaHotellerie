package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.models.Floor;
import com.example.a77011_40_08.afpahotellerie.models.Room;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.RoomType;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class FilterDialogFragment extends DialogFragment {

    /*TextView txtTitleBar;
    TextView txtAbbreviation;
    TextView txtStatus;
    TextView txtAssignment;
    TextView txtRoomType;
    TextView txtRoomBeds;
    TextView txtFloor;
    ImageView imgDetailPhoto;
    Room room;
    RoomStatut roomStatut;
    User staff;*/
    FrameLayout frlClose;
    LinearLayout llFilterStatus;
    LinearLayout llFilterRoomType;
    Spinner spFloor;
    Button btnFilter;
    List<String> spinnerArray;
    List<CheckBox> checkBoxesSatus;
    List<CheckBox> checkBoxesRoomType;

    public FilterDialogFragment() {
        // Required empty public constructor
    }

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        return fragment;
    }

    public void setDetailRoom(Room room, RoomStatut roomStatut, User staff) {
        /*this.room = room;
        this.roomStatut = roomStatut;
        this.staff = staff;*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        frlClose = root.findViewById(R.id.frlClose);
        llFilterStatus = root.findViewById(R.id.llFilterStatus);
        llFilterRoomType = root.findViewById(R.id.llFilterRoomType);
        btnFilter = root.findViewById(R.id.btnFilter);
        spFloor = root.findViewById(R.id.spFloor);

        spinnerArray =  new ArrayList<>();
        checkBoxesSatus =  new ArrayList<>();

        for (Floor floor : App.getFloors()) {
            spinnerArray.add(floor.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFloor.setAdapter(adapter);

        for(RoomStatut roomStatut : App.getRoomStatuts()) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(roomStatut.getIdRoomStatus());
            cb.setText(roomStatut.getAbbreviation() + " ("+roomStatut.getName()+")");
            llFilterStatus.addView(cb);
            checkBoxesSatus.add(cb);
        }

        for(RoomType roomType : App.getRoomsTypes()) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(roomType.getIdRoomType());
            cb.setText(roomType.getName());
            llFilterRoomType.addView(cb);
            //checkBoxesRoomType.add(cb);
        }

        /*JsonObject jo = new JsonObject();
        jo.addProperty("name", "path");

        JsonArray jsonArray = new JsonArray();
        jsonArray.add("my-path");
        jsonArray.add("my-path2");
        jsonArray.add("my-new-path");
        jo.add("value", jsonArray);*/

        //Log.e(Constants._TAG_LOG, "JSON: " + jo);
        /*int txtNextColor = ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark);

        //String path = Constants.URL_SW + Constants.PHOTOS_FOLDER + detailPhoto.getPath();

        txtTitleBar = root.findViewById(R.id.txtTitleBar);
        txtAbbreviation = root.findViewById(R.id.txtAbbreviation);
        txtStatus = root.findViewById(R.id.txtStatus);
        txtAssignment = root.findViewById(R.id.txtAssignment);
        txtRoomType = root.findViewById(R.id.txtRoomType);
        txtRoomBeds = root.findViewById(R.id.txtRoomBeds);
        txtFloor = root.findViewById(R.id.txtFloor);
        imgDetailPhoto = root.findViewById(R.id.imgDetailPhoto);*/

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


        JsonObject jo = new JsonObject();

        /*JsonArray jsonArray = new JsonArray();
        jsonArray.add("my-path");
        jsonArray.add("my-path2");
        jsonArray.add("my-new-path");
        jo.add("value", jsonArray);*/

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomStatusFilter(jo);
                /*floorFilter(jo);
                roomTypeFilter(jo);
                Log.e(Constants._TAG_LOG, "JSON: " + jo);*/
            }
        });





        /*boolean isValid = false;
        for(int floor: floorList){
            if(room.getIdFloor() == floor){
                isValid = true;
            }
        }
        if(isValid){
            rooms.add(room);
        }*/

        /*cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignedStaffFragment parent = ((AssignedStaffFragment) ((HomeActivity) context)
                        .getLastFragment());
                parent.showRoomPanel(user,user.getIdStaff());

            }
        });*/

        /*String status = roomStatut.getAbbreviation();
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

        Functions.setBiColorString("Statut : ", statusFullName, txtStatus, txtNextColor);

        String srtStaff = "";
        if (staff == null) {
            srtStaff = "Pas d'affectation";
        } else {
            srtStaff = staff.getFullName();
        }
        Functions.setBiColorString("Affectation : ", srtStaff, txtAssignment, txtNextColor);

        txtAbbreviation.setText(status);

        Functions.setViewBgColorByStatus(txtAbbreviation, status);


        String type = "";
        for(RoomType roomType : App.getRoomsTypes()) {
            if(roomType.getIdRoomType() == room.getIdRoomType()) {
                type = roomType.getName();
            }
        }
        Functions.setBiColorString("Type : ", type, txtRoomType, txtNextColor);

        String srtBeds = "";
        for(RoomType roomType : App.getRoomsTypes()) {
            if(roomType.getIdRoomType() == room.getIdRoomType()) {
                srtBeds = ""+roomType.getBeds();
            }
        }
        Functions.setBiColorString("Nombre de lits : ", srtBeds, txtRoomBeds, txtNextColor);

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
        Functions.setBiColorString("Étage : ", srtFloor, txtFloor, txtNextColor);*/

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



        //txtTitleBar.setText("Chambre " + room.getNumber());


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

    private void roomStatusFilter(JsonObject jo) {
        boolean isValid = false;
        JsonArray jsonArray = new JsonArray();
        for (CheckBox cb : checkBoxesSatus) {
            if (cb.isChecked()) {
                isValid = true;
                jsonArray.add((JsonElement) cb.getTag());
            }
        }
        if(isValid){
            jo.add("roomStatus", jsonArray);
        }
    }

    private void floorFilter(JsonObject jo) {
        String stringResult = null;
        if(spFloor != null && spFloor.getSelectedItem() !=null ) {
            stringResult = spFloor.getSelectedItem().toString();
        }
        if(stringResult != null){
            jo.addProperty("floor", "stringSesult");
        }
    }

    private void roomTypeFilter(JsonObject jo) {
        boolean isValid = false;
        JsonArray jsonArray = new JsonArray();
        for (CheckBox cb : checkBoxesRoomType) {
            if (cb.isChecked()) {
                isValid = true;
                jsonArray.add((JsonElement) cb.getTag());
            }
        }
        if(isValid){
            jo.add("roomType", jsonArray);
        }
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
