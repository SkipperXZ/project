package com.example.droneapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.droneapp.R;
import com.example.droneapp.model.Device;
import com.example.droneapp.ulity.Constant;
import com.example.droneapp.ulity.DroneApi;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageDeviceAdapter extends RecyclerView.Adapter<ManageDeviceAdapter.ManageDeviceViewHolder> {
    private Context context;
    private List<Device> deviceList ;
    private DroneApi droneApi;

    public ManageDeviceAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public ManageDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_manage_device_layout,parent,false);
        ManageDeviceAdapter.ManageDeviceViewHolder settingViewHolder = new ManageDeviceAdapter.ManageDeviceViewHolder(view,this);
        return settingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageDeviceViewHolder holder, int position) {
        String deviceName = deviceList.get(position).getDeviceName();
        String deviceKey = deviceList.get(position).getDeviceKey();
        holder.tv_manage_device_name.setText(deviceName);
        holder.tv_manage_device_key.setText(deviceKey);
    }

    public void removeItem(int position){

    }

    public void removeDevice(final int position){

        SharedPreferences sp =context.getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<Boolean> call = droneApi.removeDevice("Bearer "+token,deviceList.get(position).getDeviceID());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("Constant",response.toString());
                if(!response.isSuccessful()){

                }else {
                    if(response.body()){
                        deviceList.remove(position);
                        ManageDeviceAdapter.this.notifyItemRemoved(position);
                    }
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }



    @Override
    public int getItemCount() {
       return deviceList.size();
    }

    public static class ManageDeviceViewHolder extends RecyclerView.ViewHolder{
        TextView tv_manage_device_name;
        TextView tv_manage_device_key;
        ImageButton btn_remove_device;

        public ManageDeviceViewHolder(@NonNull final View itemView, final ManageDeviceAdapter adapter) {
            super(itemView);
            tv_manage_device_name = itemView.findViewById(R.id.tv_manage_device_name);
            tv_manage_device_key = itemView.findViewById(R.id.tv_manage_device_key);
            btn_remove_device = itemView.findViewById(R.id.btn_remove_device);
            btn_remove_device.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.removeDevice(getAdapterPosition());
                }
            });
        }
    }



}
