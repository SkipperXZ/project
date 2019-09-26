package com.example.droneapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (getActivity() != null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
            if (mapFragment == null) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                mapFragment = SupportMapFragment.newInstance();
                ft.replace(R.id.mapView, mapFragment).commit();
            }
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }

        }
    }

    private void setUpClusterManager(GoogleMap googleMap) {
        ClusterManager<User> clusterManager = new ClusterManager(this.getContext(), googleMap);  // 3
        googleMap.setOnCameraIdleListener(clusterManager);
        List<User> items = getItems();
        clusterManager.addItems(items);  // 4
        clusterManager.cluster();  // 5
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(2);
        LatLng ny = new LatLng(13.7143528, -100.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        gmap.addMarker(new MarkerOptions().position(
                new LatLng(13.76488, 100.538334)).title("TITLE"));
        setUpClusterManager(gmap);

    }

    private List<User> getItems() {
        return Arrays.asList(
                new User("AhsenSaeed1", new LatLng(-31.563910, 147.154312), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed2", new LatLng(-33.718234, 150.363181), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed3", new LatLng(-33.727111, 150.371124), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed4", new LatLng(-33.848588, 151.209834), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed5", new LatLng(-33.851702, 151.216968), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed6", new LatLng(-34.671264, 150.863657), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed7", new LatLng(-35.304724, 148.662905), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed8", new LatLng(-36.817685, 175.699196), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed9", new LatLng(-36.828611, 175.790222), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed10", new LatLng(-37.750000, 145.116667), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed11", new LatLng(-37.759859, 145.128708), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed12", new LatLng(-37.765015, 145.133858), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed13", new LatLng(-37.770104, 145.143299), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed14", new LatLng(-37.773700, 145.145187), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed15", new LatLng(-37.774785, 145.137978), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed16", new LatLng(-37.819616, 144.968119), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed17", new LatLng(-38.330766, 144.695692), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed18", new LatLng(-39.927193, 175.053218), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed19", new LatLng(-41.330162, 174.865694), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed20", new LatLng(-42.734358, 147.439506), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed21", new LatLng(-42.734358, 147.501315), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed22", new LatLng(-42.735258, 147.438000), "https://www.ahsensaeed.com"),
                new User("AhsenSaeed23", new LatLng(-43.999792, 170.463352), "https://www.ahsensaeed.com")
        );


    }
}