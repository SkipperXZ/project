package com.example.droneapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.droneapp.R;
import com.example.droneapp.activity.CheckLogin;
import com.example.droneapp.activity.LoginActivity;
import com.example.droneapp.activity.ManageDeviceActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder>{
    private Context context;

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.settings_adapter_layout,parent,false);
        SettingAdapter.SettingViewHolder settingViewHolder = new SettingAdapter.SettingViewHolder(view,context);
        return settingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        switch (position){
            case 0: ;
                holder.tv_setting_name.setText("Manage Acoount");
                holder.tv_setting_detail.setText("manage your acoount");
                break;
            case 1:
                holder.tv_setting_name.setText("Manage Device");
                holder.tv_setting_detail.setText("remove and add your device");
                break;
            case 2:
                holder.tv_setting_name.setText("Log out");
                holder.tv_setting_detail.setText("log gu out to");
                break;
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }

    public static class SettingViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener  {
        TextView tv_setting_name;
        TextView tv_setting_detail;
        Context context;

        public SettingViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = context;
            tv_setting_name = itemView.findViewById(R.id.tv_manage_device_name);
            tv_setting_detail = itemView.findViewById(R.id.tv_manage_device_key);
        }

        @Override
        public void onClick(View v) {
            Log.d("onclick","getAdapterPosition()");
            switch (getAdapterPosition()){
                case 0 :
                    break;
                case 1 :
                    startManageDeviceActivity();
                    break;
                case 2 :
                    Logout();
                    break;
            }


        }
        private void startManageDeviceActivity(){
            Intent intent = new Intent(context, ManageDeviceActivity.class);
            context.startActivity(intent);
        }
        private void Logout(){
            SharedPreferences sp = context.getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
            sp.edit().putString("token",null).commit();
            startCheckLoginActivity();
            ((Activity)context).finish();
        }
        private void startCheckLoginActivity(){
            Intent intent = new Intent(context, CheckLogin.class);
            context.startActivity(intent);
        }


    }




}
