package com.example.a77011_40_08.afpahotellerie.fragments;


import android.app.DialogFragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.activities.HomeActivity;
import com.example.a77011_40_08.afpahotellerie.models.Floor;
import com.example.a77011_40_08.afpahotellerie.models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.models.RoomType;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.utils.App;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.example.a77011_40_08.afpahotellerie.utils.StringWithTag;
import com.example.a77011_40_08.afpahotellerie.views.SectionView;
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
    Spinner spStaff;
    Button btnFilter;
    Button btnDelFilter;
    List<CheckBox> checkBoxesStatus;
    List<CheckBox> checkBoxesRoomType;
    List<StringWithTag> spinnerArray;
    List<StringWithTag> spArrayStaff;
    Gson gson;
    Boolean isRoomStatusFilter = false;
    Boolean isFloorFilter = false;
    Boolean isRoomTypeFilter = false;
    int[] idsRoomStatus;
    int idFloor;
    int[] idsRoomType;
    int idStaff;
    SectionView svStatus;
    SectionView svFloor;
    SectionView svRoomType;
    SectionView svAssignment;
    String colorTxtLight;
    String colorPrimaryDark;
    LinearLayout.LayoutParams params;
    Button btnSelectAllStatus;
    Button btnDelStatus;
    Button btnSelectAllRoomType;
    Button btnDelRoomType;

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

        colorTxtLight = "#" + Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorTxtLight));
        colorPrimaryDark = "#" + Integer.toHexString(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        gson = new Gson();

        spinnerArray = new ArrayList<StringWithTag>();
        spArrayStaff = new ArrayList<StringWithTag>();

        frlClose = root.findViewById(R.id.frlClose);
        llFilterStatus = root.findViewById(R.id.llFilterStatus);
        llFilterRoomType = root.findViewById(R.id.llFilterRoomType);
        btnFilter = root.findViewById(R.id.btnFilter);
        btnDelFilter = root.findViewById(R.id.btnDelFilter);
        spFloor = root.findViewById(R.id.spFloor);
        spStaff = root.findViewById(R.id.spStaff);
        svStatus = root.findViewById(R.id.svStatus);
        svFloor = root.findViewById(R.id.svFloor);
        svRoomType = root.findViewById(R.id.svRoomType);
        svAssignment = root.findViewById(R.id.svAssignment);
        btnSelectAllStatus = root.findViewById(R.id.btnSelectAllStatus);
        btnDelStatus = root.findViewById(R.id.btnDelStatus);
        btnSelectAllRoomType = root.findViewById(R.id.btnSelectAllRoomType);
        btnDelRoomType = root.findViewById(R.id.btnDelRoomType);

        checkBoxesStatus =  new ArrayList<>();
        checkBoxesRoomType =  new ArrayList<>();

        spinnerArray.add(new StringWithTag("Tous les étages", 0));
        for (Floor floor : App.getFloors()) {
            spinnerArray.add(new StringWithTag(floor.getName(), floor.getIdFloor()));
        }

        spArrayStaff.add(new StringWithTag("Aucun agent sélectionné", 0));
        for (User user : App.getStaff()) {
            spArrayStaff.add(new StringWithTag(user.getFullName(), user.getIdStaff()));
        }

        /*for(User user : App.getStaff()) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(user.getIdStaff());
            cb.setText(user.getFullName());
            cb.setTextColor(getResources().getColor(R.color.colorTxtLight));
            params.bottomMargin = 25;

            CompoundButtonCompat.setButtonTintList(cb,colorStateList);

            if(isRoomStatusFilter){
                if(contains(idsRoomStatus, roomStatut.getIdRoomStatus())){
                    cb.setChecked(true);
                }
            }
            llFilterStatus.addView(cb, paramsCb());
            checkBoxesStatus.add(cb);
            if(user.getIdStaff() == (room.getIdStaff())) {
                Log.e(Constants._TAG_LOG, "idStaff Success");
                this.staff = user;
                staffName = user.getFullName();
            }
        }*/

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFloor.setAdapter(adapter);

        ArrayAdapter<StringWithTag> adapterStaff = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_item, spArrayStaff);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStaff.setAdapter(adapterStaff);

        if (Session.getJoRoomFilter() != null) {
            JsonObject joFilter = Session.getJoRoomFilter();
            if(joFilter.has("roomStatus")) {
                idsRoomStatus = gson.fromJson(Session.getJoRoomFilter().getAsJsonArray("roomStatus"), int[].class);
                isRoomStatusFilter = true;
                svStatus.initBody(false);
            }
            if(joFilter.has("floor")) {
                idFloor = joFilter.get("floor").getAsInt();
                //isFloorFilter = true;
                if (idFloor != 0) {
                    spFloor.setSelection(idFloor);
                    svFloor.initBody(false);
                }
            }
            if(joFilter.has("roomType")) {
                idsRoomType = gson.fromJson(Session.getJoRoomFilter().getAsJsonArray("roomType"), int[].class);
                isRoomTypeFilter = true;
                svRoomType.initBody(false);
            }
            if(joFilter.has("assignment")) {
                idStaff = joFilter.get("assignment").getAsInt();
                //isAssignmentFilter = true;
                if (idStaff != 0) {
                    spStaff.setSelection(idFloor);
                    svAssignment.initBody(false);
                }
            }
        }


        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked}, // unchecked
                        new int[]{android.R.attr.state_checked} , // checked
                },
                new int[]{
                        Color.parseColor(colorTxtLight),
                        Color.parseColor(colorPrimaryDark),
                }
        );

        for(RoomStatut roomStatut : App.getRoomStatuts()) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(roomStatut.getIdRoomStatus());
            cb.setText(roomStatut.getAbbreviation() + " ("+roomStatut.getName()+")");
            cb.setTextColor(getResources().getColor(R.color.colorTxtLight));
            params.bottomMargin = 25;

            CompoundButtonCompat.setButtonTintList(cb,colorStateList);

            if(isRoomStatusFilter){
                if(contains(idsRoomStatus, roomStatut.getIdRoomStatus())){
                    cb.setChecked(true);
                }
            }
            llFilterStatus.addView(cb, paramsCb());
            checkBoxesStatus.add(cb);
        }

        for(RoomType roomType : App.getRoomsTypes()) {
            CheckBox cb = new CheckBox(getActivity());
            cb.setTag(roomType.getIdRoomType());
            cb.setText(roomType.getName());
            cb.setTextColor(getResources().getColor(R.color.colorTxtLight));
            params.bottomMargin = 25;

            CompoundButtonCompat.setButtonTintList(cb,colorStateList);

            if(isRoomTypeFilter){
                if(contains(idsRoomType, roomType.getIdRoomType())){
                    cb.setChecked(true);
                }
            }
            llFilterRoomType.addView(cb, paramsCb());
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
                assignmentFilter();
                Session.setJoRoomFilter(jo);
                closeFilterDialogFragment();
                dismiss();
                Log.e(Constants._TAG_LOG, "JSON: " + jo);
            }
        });

        btnDelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.setJoRoomFilter(null);
                clearForm(llFilterStatus);
                spFloor.setSelection(0);
                clearForm(llFilterRoomType);
                spStaff.setSelection(0);
                svStatus.collapseAction();
                svFloor.collapseAction();
                svRoomType.collapseAction();
                svAssignment.collapseAction();
            }
        });

        btnSelectAllStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAllForm(llFilterStatus);
            }
        });

        btnDelStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm(llFilterStatus);
            }
        });

        btnSelectAllRoomType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAllForm(llFilterRoomType);
            }
        });

        btnDelRoomType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm(llFilterRoomType);
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

    private void assignmentFilter() {
        if(spStaff != null && spStaff.getSelectedItem() != null ) {
            StringWithTag swt = (StringWithTag) spStaff.getSelectedItem();
            Integer id = (Integer) swt.tag;
            jo.addProperty("assignment", id);
        }
        Log.e(Constants._TAG_LOG,"FILTER4: "+jo.toString());
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

    private void clearForm(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(false);
            }
        }
    }

    private void selectAllForm(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(true);
            }
        }
    }

    private LinearLayout.LayoutParams paramsCb() {
        params.bottomMargin = 25;
        return params;
    }

}
