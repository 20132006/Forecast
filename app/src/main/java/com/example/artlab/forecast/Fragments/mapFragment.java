package com.example.artlab.forecast.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artlab.forecast.MainActivity;
import com.example.artlab.forecast.R;
import com.example.artlab.forecast.onMapClicked;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class mapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mGoogleApiClient;
    MapFragment fragment;
    onMapClicked listenOnMapClick;

    public mapFragment(MainActivity listener)
    {
        setInterface(listener);
    }

    public void setInterface(onMapClicked listenOnMapClick) {
        this.listenOnMapClick = listenOnMapClick;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng marker = new LatLng(-33.867, 151.206);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));

        mGoogleApiClient = googleMap;

        googleMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mGoogleApiClient.addMarker(new MarkerOptions().title("Hello Google Maps!").position(latLng));
        listenOnMapClick.buttonClicked(latLng);
    }
}
