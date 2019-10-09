package com.example.droneapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.droneapp.R;
import com.example.droneapp.activity.RouteActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DroneFragment extends Fragment {

    private Button btn_gotomap ;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drone, container, false);

        btn_gotomap = v.findViewById(R.id.btn_gotomap);

        btn_gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                startActivity(intent);
            }
        });





        return v;
    }
}
