package com.shipwebsource.mywebsource;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class RequestShuttleFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = RequestShuttleFragment.class.getSimpleName();
    private LatLng userLocation;
    private GoogleMap map;
    private View view;
    private Marker userMarker;
    private TextView getDirections;

    private String latitude;
    private String longitude;
    private final LatLng websource = new LatLng(10.6185, -61.3456);
    private boolean mapReady;

    public RequestShuttleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style_standard_remix);
        map.setMapStyle(style);
        map.setMyLocationEnabled(true);
        if (userLocation != null)
        {
//            userMarker = map.addMarker(new MarkerOptions().position(userLocation).title("You are here"));
            // Set the camera's starting position
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(userLocation) // set the camera's center position
                    .zoom(17)  // set the camera's zoom level
                    .tilt(20)  // set the camera's tilt
                    .build();



            // Move the camera to that position
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }

        mapReady = true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_request_shuttle, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mapReady = false;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getDirections = (TextView) view.findViewById(R.id.menu_getDirections);

        getDirections.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startNavigation();
            }
        });

    }



    public void startNavigation()
    {
        if (latitude != null || longitude != null)
        {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+websource.latitude+","+websource.longitude+"&mode=d");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }




    }

}
