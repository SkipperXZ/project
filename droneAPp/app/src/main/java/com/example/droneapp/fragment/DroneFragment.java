package com.example.droneapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.droneapp.R;
import com.example.droneapp.activity.NewFlightActivity;
import com.example.droneapp.activity.RouteActivity;
import com.example.droneapp.adapter.FlightPageAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class DroneFragment extends Fragment {

    private Button btn_new_flight ;
    private ViewPager pager;
    private TabLayout tabLayout;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drone, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        btn_new_flight = (Button) v.findViewById(R.id.btn_new_flight);

        btn_new_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFlightActivity();
            }
        });

      /*  btn_gotomap = v.findViewById(R.id.btn_gotomap);

        btn_gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSetRouteActivity();
            }
        });*/

        FlightPageAdapter adapter = new FlightPageAdapter(getChildFragmentManager());
        pager = (ViewPager) v.findViewById(R.id.viewPager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setText("Active");
        tabLayout.getTabAt(1).setText("History");



        return v;
    }
    private void newSetRouteActivity(){
        Intent intent = new Intent(getActivity(), RouteActivity.class);
        startActivity(intent);
    }

    private void newFlightActivity(){
        Intent intent = new Intent(getActivity(), NewFlightActivity.class);
        startActivity(intent);
    }
}
