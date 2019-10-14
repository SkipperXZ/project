package com.example.droneapp.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.droneapp.R;
import com.example.droneapp.model.Flight;
import com.example.droneapp.model.FlightInfo;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActiveFlightAdapter extends RecyclerView.Adapter<ActiveFlightAdapter.FlightViewHolder> {

    private Context context;
    private List<FlightInfo> flightInfoList;
    long totalSeconds = 30;
    long intervalSeconds = 1;

    public ActiveFlightAdapter(List<FlightInfo> flightInfoList){
        this.flightInfoList = flightInfoList;
    }


    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_flight_adapter,parent,false);

        ActiveFlightAdapter.FlightViewHolder flightViewHolder = new ActiveFlightAdapter.FlightViewHolder(view);
        context = parent.getContext();
        AndroidThreeTen.init(context);
        return flightViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FlightViewHolder holder, final int position) {

        final LocalDateTime startFlightTime = LocalDateTime.parse(flightInfoList.get(position).getTimeStamp());

        holder.tv_flight_name.setText(flightInfoList.get(position).getFlightName());
        holder.tv_device_name.setText(flightInfoList.get(position).getDeviceName());

        final CountDownTimer timer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {


            public void onTick(long millisUntilFinished) {
                Duration timeElapsed = Duration.between(startFlightTime,LocalDateTime.now());
                long seconds = Math.abs(timeElapsed.getSeconds());
                long hours = seconds / 3600;
                long minutes = seconds / 60;


                holder.tv_flight_time.setText(hours+":"+minutes+":"+seconds);
            }

            public void onFinish() {
                this.start();
            }

        };
        timer.start();
    }

    @Override
    public int getItemCount() {
        return flightInfoList.size();
    }

    public static class FlightViewHolder extends  RecyclerView.ViewHolder{

        TextView tv_flight_name;
        TextView tv_device_name;
        TextView tv_flight_time;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_flight_name = itemView.findViewById(R.id.tv_flight_name);
            tv_device_name = itemView.findViewById(R.id.tv_device_name);
            tv_flight_time = itemView.findViewById(R.id.tv_flight_time);
        }
    }
}


