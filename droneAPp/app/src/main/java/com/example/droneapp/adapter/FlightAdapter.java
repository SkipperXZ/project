package com.example.droneapp.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.droneapp.R;
import com.example.droneapp.model.FlightInfo;
import com.example.droneapp.ulity.API;
import com.example.droneapp.ulity.DroneApi;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private Context context;
    private List<FlightInfo> flightInfoList;

    private DroneApi droneApi;

    private int adapterCode;
    private final int ACTIVE_ADAPTER_CODE = 0;
    private final int HISTORY_ADAPTER_CODE = 1;



    public FlightAdapter(List<FlightInfo> flightInfoList,int adapterCode){
        this.flightInfoList = flightInfoList;
        this.adapterCode = adapterCode;

    }


    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_flight_adapter,parent,false);
        FlightAdapter.FlightViewHolder flightViewHolder = new FlightAdapter.FlightViewHolder(view,this);
        context = parent.getContext();
        AndroidThreeTen.init(context);



        return flightViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FlightViewHolder holder, final int position) {

        final LocalDateTime startFlightTime = LocalDateTime.parse(flightInfoList.get(position).getTimeStamp());

        holder.tv_flight_name.setText(flightInfoList.get(position).getFlightName());
        holder.tv_device_name.setText(flightInfoList.get(position).getDeviceName());
        if(adapterCode != HISTORY_ADAPTER_CODE) {
            if (holder.timer == null) {
                holder.setTimer(LocalDateTime.parse(flightInfoList.get(position).getTimeStamp()));
            }

            holder.timer.start();
        }else{
            holder.tv_flight_time.setVisibility(TextView.INVISIBLE);
        }
    }

    public List<FlightInfo> getFlightInfoList() {
        return flightInfoList;
    }

    public void setFlightInfoList(List<FlightInfo> flightInfoList) {
        this.flightInfoList = flightInfoList;
    }

    public void setFinishFlight(final int position){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        droneApi = retrofit.create((DroneApi.class));
        Call<Boolean> call = droneApi.finishFlight(flightInfoList.get(position).getFlightID());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("api",response.toString());
                if (!response.isSuccessful()) {
                    return;
                }
                if(response.body()) {
                    FlightAdapter.this.notifyItemRemoved(position);
                    flightInfoList.remove(position);
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return flightInfoList.size();
    }

    public static class FlightViewHolder extends  RecyclerView.ViewHolder{

        TextView tv_flight_name;
        TextView tv_device_name;
        TextView tv_flight_time;
        private Button btn_stop_flight;
        CountDownTimer timer;


        public FlightViewHolder(@NonNull View itemView , final FlightAdapter adapter) {
            super(itemView);
                tv_flight_name = itemView.findViewById(R.id.tv_history_flight_name);
                tv_device_name = itemView.findViewById(R.id.tv_history_device_name);
                tv_flight_time = itemView.findViewById(R.id.tv_history_flight_time);
                btn_stop_flight = itemView.findViewById(R.id.btn_remove_history);
                btn_stop_flight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.setFinishFlight(getAdapterPosition());
                    }
                });

        }

        public void setTimer(final LocalDateTime startTime){
            long totalSeconds = 30;
            long intervalSeconds = 1;
            timer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {
                public void onTick(long millisUntilFinished) {
                    Duration timeElapsed = Duration.between(startTime, LocalDateTime.now());
                    long seconds = Math.abs(timeElapsed.getSeconds());
                    long hours = seconds / 3600;
                    long minutes = seconds / 60;
                    tv_flight_time.setText(hours + ":" + minutes%60 + ":" + seconds % 60);
                }

                public void onFinish() {
                    this.start();
                }

            };
        }
    }
}


