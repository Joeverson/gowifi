package br.com.dup.services.wifimanager;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import br.com.dup.services.db.DB;
import br.com.dup.services.notification.Notification;
import br.edu.ifpb.dup.imwifi.MapsActivity;

/**
 * Created by joerverson on 1/16/16.
 */
public class WifiReceiver extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private Location location;
    private GoogleApiClient gac;
    private DB db;
    private Context context;
    private Intent it;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        this.it = intent;

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        builder.addApi(LocationServices.API);
        this.gac = builder.build();
    }


    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(this.gac);

        if (location != null){
            if(db.nearNetwork(location.getLatitude(), location.getLongitude())){
                Notification.create(context,
                        "Tem redes por perto!!",
                        "Aqui por perto possue redes já encontradas por desbravadores.",
                        PendingIntent.getActivity(context,
                                0,
                                new Intent(this.context, MapsActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT));
            }
        }

        this.gac.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        
    }
}
