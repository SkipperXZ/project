package com.example.droneapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.droneapp.R;
import com.example.droneapp.model.Device;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ManageDeviceAdapter extends RecyclerView.Adapter<ManageDeviceAdapter.ManageDeviceViewHolder> {
    private Context context;
    private List<Device> deviceList ;

    public ManageDeviceAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public ManageDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_manage_device_layout,parent,false);
        ManageDeviceAdapter.ManageDeviceViewHolder settingViewHolder = new ManageDeviceAdapter.ManageDeviceViewHolder(view);
        return settingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageDeviceViewHolder holder, int position) {
        String deviceName = deviceList.get(position).getDeviceName();
        String deviceKey = deviceList.get(position).getDeviceKey();
        holder.tv_manage_device_name.setText(deviceName);
        holder.tv_manage_device_key.setText(deviceKey);
    }


    @Override
    public int getItemCount() {
       return deviceList.size();
    }

    public static class ManageDeviceViewHolder extends RecyclerView.ViewHolder{
        TextView tv_manage_device_name;
        TextView tv_manage_device_key;

        public ManageDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_manage_device_name = itemView.findViewById(R.id.tv_manage_device_name);
            tv_manage_device_key = itemView.findViewById(R.id.tv_manage_device_key);
        }
    }

}
