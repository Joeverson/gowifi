package br.com.dup.services.wifimanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import br.com.dup.services.db.DB;
import br.edu.ifpb.dup.imwifi.MapsActivity;

/**
 * Created by joerverson ... joerverson.santos@gmail.com on 1/16/16.
 */
public class WifiControl {
    public static final String APP_TAG = "APP_wifi";
    private static WifiManager wifimanager;
    private static DB db;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void info(Context context){
        wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);



        /*if(wifimanager.getConfiguredNetworks() != null){
            for(int i = 0; i < wifimanager.getConfiguredNetworks().size(); i++){

                //Log.i(APP_TAG, (wifimanager.getConfiguredNetworks()).get(i).SSID);

               //verificando se existe as redes no db se houver ele não vai adicionar de novo
                if (!db.exist(wifimanager.getConfiguredNetworks().get(i).SSID)) {
                    Log.i(APP_TAG, "Não tem rede configurada!!");
                } else {
                    // adicionando as novas redes no banco de dados
                    db.setData(wifimanager.getConfiguredNetworks().get(i).SSID,
                            wifimanager.getConfiguredNetworks().get(i).preSharedKey,
                            00000,
                            00000);

                    Log.i(APP_TAG, (wifimanager.getConfiguredNetworks()).get(i).SSID);
                }
            }
        }*/
    }
}
