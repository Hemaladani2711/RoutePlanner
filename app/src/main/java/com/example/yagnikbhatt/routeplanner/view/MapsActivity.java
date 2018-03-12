package com.example.yagnikbhatt.routeplanner.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.yagnikbhatt.routeplanner.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static String TAG=MapsActivity.class.getSimpleName();
    public static String DEST_LONG;
    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationProviderClient;
    LatLng current,dest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    current = new LatLng(location.getLatitude(), location.getLongitude());
                    //Bundle bundle=getIntent().getExtras();
                    double[]array=getIntent().getDoubleArrayExtra(DEST_LONG);
                    dest=new LatLng(array[0],array[1]);
                    Log.i(TAG,"Dest Lat: "+dest.latitude);
                    Log.i(TAG,"Dest Lon: "+dest.longitude);
                    mMap.addMarker(new MarkerOptions().position(current).title("Your location"));
                    mMap.addMarker(new MarkerOptions().position(dest).title("Dest"));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,10));
                    mMap.addPolyline(new PolylineOptions().color(R.color.colorPrimary).add(current,dest));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,12));




                }

            }
        });
        /*mlocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(current).title("Your location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }


        };
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, (float) 1.4, mlocationListener);*/
    }



/*
*/
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
     //   mMap.addPolyline(new PolylineOptions().color(R.color.colorPrimary).add(current,dest));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,8));

        // Add a marker in Sydney and move the camera
/*        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        LatLng current = new LatLng(googleMap.);
        mMap.addMarker(new MarkerOptions().position(current).title("Your location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));*/


    }



}
