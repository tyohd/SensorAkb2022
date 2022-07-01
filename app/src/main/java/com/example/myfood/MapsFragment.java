package com.example.myfood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsFragment extends Fragment {


    // Menginisialisasikan variabel
    FusedLocationProviderClient client;
    private GoogleMap map;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng Rest1 = new LatLng(-6.8848146541884025, 107.61348693400421);
    LatLng Rest2 = new LatLng(-6.88650361490836, 107.61502729526497);
    LatLng Rest3 = new LatLng(-6.886961626344163, 107.61482881180387);
    LatLng Rest4 = new LatLng(-6.885230766852113, 107.61559592355891);
    LatLng Rest5 = new LatLng(-6.88560889361866, 107.61341796990472);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inisialisasi view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        // inisialisasi map Fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Initialize location client
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        arrayList.add(Rest1);
        arrayList.add(Rest2);
        arrayList.add(Rest3);
        arrayList.add(Rest4);
        arrayList.add(Rest5);

        //singkron map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //dimana map berhasil dijalankan
                map = googleMap;
                map.addMarker(new MarkerOptions().position(Rest1).title("Mcdonals Dago"));
                map.addMarker(new MarkerOptions().position(Rest2).title("SPG Dipatiukur"));
                map.addMarker(new MarkerOptions().position(Rest3).title("Warman Dipatiukur"));
                map.addMarker(new MarkerOptions().position(Rest4).title("Kantin Sakinah"));
                map.addMarker(new MarkerOptions().position(Rest5).title("SHu guo yin hotpot dago"));
                for (int i=0;i<arrayList.size();i++){
                    map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                    map.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                }
            }
        });

        // chc condition
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //jika lokasi diizinkan
            // memanggil method
            getCurrentLocation();
        }
        else {
            // when loaction not allowed
            // call method
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        }

        // Return view
        return view;
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {
        // initiate map fragment
        SupportMapFragment mapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        // initiatelisasi lokasi manager
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        // chc condtion
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(
                    task -> {
                        // Initiate Loc
                        Location location = task.getResult();
                        // Check Cond
                        if (location != null) {
                            // When location result is not null set latitude and longitude
                            mapFragment.getMapAsync(googleMap -> {
                                LatLng lokasi = new LatLng(location.getLatitude(),location.getLongitude());
                                MarkerOptions options = new MarkerOptions().position(lokasi).title("Lokasi Anda");
                                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,17));
                                googleMap.addMarker(options);
                            });
                        }
                        else {
                            LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void
                                onLocationResult(@NonNull LocationResult locationResult)
                                {
                                    mapFragment.getMapAsync(googleMap -> {
                                        LatLng lokasi = new LatLng(location.getLatitude(),location.getLongitude());
                                        MarkerOptions options = new MarkerOptions().position(lokasi).title("Lokasi Sekarang");
                                        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,17));
                                        googleMap.addMarker(options);
                                    });
                                }
                            };

                            // Updating Loc
                            client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    });
        }
        else {
            startActivity(
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}