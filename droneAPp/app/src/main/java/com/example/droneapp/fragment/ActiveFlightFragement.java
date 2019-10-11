package com.example.droneapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.droneapp.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ActiveFlightFragement extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flight_active, container, false);

        return v;
    }
}
