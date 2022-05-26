package com.example.MireaProject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {
    protected static GoogleMap googleMap;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        searchEditText = view.findViewById(R.id.searchEditText);
        view.findViewById(R.id.searchButton).setOnClickListener(this::onClickSearch);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            MapsFragment.googleMap = googleMap;

            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setTrafficEnabled(true);

            LatLng moscow = new LatLng(55.5815244,36.8251221);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(moscow));


            LatLng pos1 = new LatLng(55.6695953,37.4798824);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos1)
                    .title(getResources().getString(R.string.Mirea1))
                    .snippet(getResources().getString(R.string.Mirea1Snippet)));

            LatLng pos2 = new LatLng(55.6618971,37.4745255);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos2)
                    .title(getResources().getString(R.string.Mirea2))
                    .snippet(getResources().getString(R.string.Mirea2Snippet)));

            LatLng pos3 = new LatLng(55.7938058,37.7000664);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos3)
                    .title(getResources().getString(R.string.Mirea3))
                    .snippet(getResources().getString(R.string.Mirea3Snippet)));

            LatLng pos4 = new LatLng(55.7317977,37.5745506);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos4)
                    .title(getResources().getString(R.string.Mirea4))
                    .snippet(getResources().getString(R.string.Mirea4Snippet)));

            LatLng pos5 = new LatLng(55.7648399,37.7392163);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos5)
                    .title(getResources().getString(R.string.Mirea5))
                    .snippet(getResources().getString(R.string.Mirea5Snippet)));

            LatLng pos6 = new LatLng(55.7250254,37.6304868);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos6)
                    .title(getResources().getString(R.string.Mirea6))
                    .snippet(getResources().getString(R.string.Mirea6Snippet)));

            LatLng pos7 = new LatLng(55.728676,37.5708812);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos7)
                    .title(getResources().getString(R.string.Mirea7))
                    .snippet(getResources().getString(R.string.Mirea7Snippet)));

            LatLng pos9 = new LatLng(55.9604333,38.049562);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos9)
                    .title(getResources().getString(R.string.Mirea9))
                    .snippet(getResources().getString(R.string.Mirea9Snippet)));

            LatLng pos8 = new LatLng(45.0508385,41.9097125);
            googleMap.addMarker(new MarkerOptions()
                    .position(pos8)
                    .title(getResources().getString(R.string.Mirea8))
                    .snippet(getResources().getString(R.string.Mirea8Snippet)));
        }
    };

    public void onClickSearch(View view) {
        String location = searchEditText.getText().toString();

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            List<Address>  listAddress = geocoder.getFromLocationName(location,1);

            if (listAddress.size() > 0) {
                LatLng latLng = new LatLng(listAddress.get(0).getLatitude(),
                        listAddress.get(0).getLongitude());

                String searchTitle = "Search";

                googleMap.addMarker(new MarkerOptions().position(latLng).title(searchTitle));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5);
                googleMap.animateCamera(cameraUpdate);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}