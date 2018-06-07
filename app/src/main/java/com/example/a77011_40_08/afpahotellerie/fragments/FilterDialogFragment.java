package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
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
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.models.Floor;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.RoomType;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.example.a77011_40_08.afpahotellerie.utils.StringWithTag;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class FilterDialogFragment extends DialogFragment {

    FrameLayout frlClose;
    LinearLayout llFilterStatus;
    LinearLayout llFilterRoomType;
    Spinner spFloor;
    Button btnFilter;
    List<CheckBox> checkBoxesStatus;
    List<CheckBox> checkBoxesRoomType;
    List<StringWithTag> spinnerArray;
    Gson gson;
    Boolean isRoomStatusFilter = false;
    Boolean isFloorFilter = false;
    Boolean isRoomTypeFilter = false;
    int[] idsRoomStatus;
    int idFloor;
    int[] idsRoomType;

    JsonObject jo;

    public FilterDialogFragment() {
        // Required empty public constructor
    }

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        gson = new Gson();

        spinnerArray = new ArrayList<StringWithTag>();

        frlClose = root.findViewById(R.id.frlClose);
        llFilterStatus = root.findViewById(R.id.llFilterStatus);
        llFilterRoomType = root.findViewById(R.id.llFilterRoomType);
        btnFilter = root.findViewById(R.id.btnFilter);
        spFloor = root.findViewById(R.id.spFloor);

        checkBoxesStatus =  new ArrayList<>();
        checkBoxesRoomType =  new ArrayList<>();

        spinnerArray.add(new StringWithTag("Tous les étages", 0));
        for (Floor floor : App.getFloors()) {
            spinnerArray.add(new StringWithTag(floor.getName(), floor.getIdFloor()));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag> (getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFloor.setAdapter(adapter);

        if (Session.getJoRoomFilter() != null) {
            JsonObject joFilter = Session.getJoRoomFilter();
            if(joFilter.has("roomStatus")) {
                idsRoomStatus = gson.fromJson(Session.getJoRoomFilter().getAsJsonArray("roomStatus"), int[].class);
                isRoomStatusFilter = true;
            }
            if(joFilter.has("floor")) {
                idFloor = joFilter.get("floor").getAsInt();
                isFloorFilter = true;
                if (idFloor != 0) {
                    spFloor.setSelection(idFloor);
                }
            }
            if(joFilter.has("roomType")) {
                idsRoomType = gson.fromJson(Session.getJoRoomFilter().getAsJsonArray("roomType"), int[].class);
                isRoomTypeFilter = true;
            }
        }


        for(RoomStatut roomStatut : App.getRoomStatuts()) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(roomStatut.getIdRoomStatus());
            cb.setText(roomStatut.getAbbreviation() + " ("+roomStatut.getName()+")");
            if(isRoomStatusFilter){
                if(contains(idsRoomStatus, roomStatut.getIdRoomStatus())){
                    cb.setChecked(true);
                }
            }
            llFilterStatus.addView(cb);
            checkBoxesStatus.add(cb);
        }

        for(RoomType roomType : App.getRoomsTypes()) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(roomType.getIdRoomType());
            cb.setText(roomType.getName());
            if(isRoomTypeFilter){
                if(contains(idsRoomType, roomType.getIdRoomType())){
                    cb.setChecked(true);
                }
            }
            llFilterRoomType.addView(cb);
            checkBoxesRoomType.add(cb);
        }

        frlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jo = new JsonObject();
                roomStatusFilter();
                floorFilter();
                roomTypeFilter();
                Session.setJoRoomFilter(jo);
                closeFilterDialogFragment();
                dismiss();
                Log.e(Constants._TAG_LOG, "JSON: " + jo);
            }
        });

        return root;
    }

    private void closeFilterDialogFragment() {
        HomeActivity home = (HomeActivity) getActivity();
        StateRoomsFragment frag = (StateRoomsFragment) home.getLastFragment();
        frag.refreshRoomFilter();
    }

    private void roomStatusFilter() {
        boolean isValid = false;
        JsonArray jsonArray = new JsonArray();
        for (CheckBox cb : checkBoxesStatus) {
            if (cb.isChecked()) {
                isValid = true;
                int val = (int) cb.getTag();
                //Log.e(Constants._TAG_LOG,"Status: "+val);
                jsonArray.add(val);
            }
        }
        if(isValid){
            jo.add("roomStatus", jsonArray);
        }
        Log.e(Constants._TAG_LOG,"FILTER1: "+jo.toString());
    }

    private void floorFilter() {
        if(spFloor != null && spFloor.getSelectedItem() != null ) {
            StringWithTag swt = (StringWithTag) spFloor.getSelectedItem();
            Integer id = (Integer) swt.tag;
            jo.addProperty("floor", id);
        }
        Log.e(Constants._TAG_LOG,"FILTER2: "+jo.toString());
    }


    private void roomTypeFilter() {
        boolean isValid = false;
        JsonArray jsonArray = new JsonArray();
        for (CheckBox cb : checkBoxesRoomType) {
            if (cb.isChecked()) {
                isValid = true;
                int val = (int) cb.getTag();
                jsonArray.add(val);
            }
        }
        if(isValid){
            jo.add("roomType", jsonArray);
        }
        Log.e(Constants._TAG_LOG,"FILTER3: "+jo.toString());
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

        window.setLayout((int) (width * 0.99), (int) (height * 0.96));
        window.setGravity(Gravity.CENTER);
    }

    public static boolean contains(final int[] array, final int v) {
        boolean result = false;
        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }
        return result;
    }
}
