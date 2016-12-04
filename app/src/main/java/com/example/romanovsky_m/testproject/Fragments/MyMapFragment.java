package com.example.romanovsky_m.testproject.Fragments;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Romanovsky_m on 28.10.2016.
 */
public class MyMapFragment extends SupportMapFragment{

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng BSUIR = new LatLng(53.917833, 27.594846);
                LatLng CDS = new LatLng(53.91617, 27.586220);
                LatLng BSUIRHostel4 = new LatLng(53.858150, 27.485091);
                LatLng BSUIRHostel2 = new LatLng(53.929948, 27.588689);

                try {
                    googleMap.setMyLocationEnabled(true);
                }catch (SecurityException e) {

                }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BSUIR, 13));

                googleMap.addMarker(new MarkerOptions()
                        .title("BSUIR")
                        .snippet("Белорусский государственный университет информатики и радиоэлектроники")
                        .position(BSUIR));

                googleMap.addMarker(new MarkerOptions()
                        .title("CDS")
                        .snippet("Центральный Универмаг")
                        .position(CDS));

                googleMap.addMarker(new MarkerOptions()
                        .title("BSUIR Hostel #4")
                        .snippet("Общежитие БГУИР №4")
                        .position(BSUIRHostel4));

                googleMap.addMarker(new MarkerOptions()
                        .title("BSUIR Hostel #2")
                        .snippet("Общежитие БГУИР №2")
                        .position(BSUIRHostel2));
            }
        });
    }

}
