package br.br.com.dup.services.json;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.dup.services.db.DB;
import br.edu.ifpb.dup.imwifi.R;

/**
 * Created by joeverson.... email: joerverson.santos@gmail.com on 15/01/16.
 */
public class CustomJsonObjectRequest extends JsonObjectRequest{

    private static String params;
    private static RequestQueue mQueue;
    //variavel statica para poder ter controlle em cima da requisicao
    private final static String TAG_REQUEST = "Locations";
    private static String error;
    private static String response;
    private static Context context;
    private static DB db;

    public static final String URI_API = "http://314.bl.ee/api.php";


    private CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        super.setTag(TAG_REQUEST);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-type", "application/json; charset-utf-8");
        return super.getHeaders();
    }

    /**
     * param context
     * param int
     * param string
     *
     * return void
     *
     * esse method é para enviar as informcoes por http para
     * o servidor que esta esperando por essas requisicoes.
     * */
    public static void send(Context context, int method, String url){
        CustomJsonObjectRequest.context = context;
        CustomJsonObjectRequest.db = new DB(context);

        mQueue = CustomVolleyRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();

        //caso nao seja enviado parametros ele envia aquantidade de tuplas
        //para fazer o teste e ve se precisa atualizar o banco local
        if(params == null){
            params = "?c="+db.getCountLines();
        }


        mQueue.add(new CustomJsonObjectRequest(method, url+params, new JSONObject(), new EventJSON(), new EventJSON()));
        Log.i("APP", url+params);
    }

    public static void setParams(String key, String value){
        if(params == null)
            params = "?";

        params += key+"="+value+"&";
    }

    public static void stop(){
        if(mQueue != null)
            mQueue.cancelAll(TAG_REQUEST);
    }


    public static String getError(){
        if(error == null)
            error = "error nothing";

        return error;
    }

    public static String getResponse(){
        if(response == null)
            response = "response nothing";

        return response;
    }


    private static class EventJSON implements Response.Listener, Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {
            CustomJsonObjectRequest.error = error.getMessage();
        }

        @Override
        public void onResponse(Object response) {
            try {
                Log.i("APP", response.toString());
                JSONObject json = new JSONObject(response.toString());



                // verifica o status caso seja atualozar ele vai ler um outro campo que vai ser 
                //enviado com os dados para ser jogado no banco de dados local
                
                if(json.getString("status").equals("atualizar")){
                    JSONArray array = (JSONArray) json.get("data");

                    //gravando no banco local as novas localizacoes
                    for (int i = 0; i < array.length(); i++) {
                        db.setData(array.getJSONObject(i).getString("ssid"),
                                array.getJSONObject(i).getString("password"),
                                Double.parseDouble(array.getJSONObject(i).getString("latitude")),
                                Double.parseDouble(array.getJSONObject(i).getString("longitude")));
                    }


                    //notificacao para o usuario de novas atualizacoes.
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setTicker("GoWifi");
                    builder.setContentText("Acabei de saber que tem \n novas redes wifi descobertas.");
                    builder.setContentTitle("Ei, Tenho novidades!!");
                    builder.setSubText("Os desbravadores estão explorando \n muito, não fique para atrás");
                    builder.setSmallIcon(R.mipmap.ic_directions_boat_white_24dp);

                    NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
                    nmc.notify(1, builder.build());
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
