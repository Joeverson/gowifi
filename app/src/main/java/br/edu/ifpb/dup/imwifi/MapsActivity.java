package br.edu.ifpb.dup.imwifi;


import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;

import com.android.volley.Request;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;

import br.com.dup.services.json.CustomJsonObjectRequest;
import br.com.dup.services.db.DB;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DB db;
    private Hotpots hotpot;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //inicializando o banco de dados;
        db = new DB(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        Iterator i = this.db.getData().iterator();
        while(i.hasNext()) {
            hotpot = (Hotpots) i.next();

            LatLng sydneyw = new LatLng(hotpot.getLagitude(), hotpot.getLongitude());
            mMap.addMarker(new MarkerOptions().snippet("PW: " + hotpot.getPassword()).position(sydneyw).title(hotpot.getSsid()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_black_24dp)));
        }


        //todo ativar depois isso quando for testar em um cell;
        //mostra a area onde eu possa estar.
        //LatLng here = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(here));

        //event
        mMap.setOnMarkerClickListener(new OnclickMarker());
    }

    @Override
    protected void onPause(){
        super.onPause();
        CustomJsonObjectRequest.send(this, Request.Method.GET, CustomJsonObjectRequest.URI_API);
        Log.i("APP", "here...pause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        CustomJsonObjectRequest.stop();
    }


    // class called when for clicked in a marked
    private class OnclickMarker implements GoogleMap.OnMarkerClickListener{

        @Override
        public boolean onMarkerClick(Marker marker) {
            Toast.makeText(MapsActivity.this, "A senha é: "+marker.getSnippet(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
