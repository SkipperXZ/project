package com.example.droneapp.adapter;

import android.content.Context;

import com.example.droneapp.R;
import com.example.droneapp.fragment.ActiveFlightFragement;
import com.example.droneapp.fragment.HistoryFlightFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FlightPageAdapter extends FragmentPagerAdapter  {

   public FlightPageAdapter(FragmentManager fm) {
       super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new ActiveFlightFragement();
        else if(position == 1)
            return new HistoryFlightFragment();
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
