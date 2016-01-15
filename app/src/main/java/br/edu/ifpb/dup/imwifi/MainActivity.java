package br.edu.ifpb.dup.imwifi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;

import br.br.com.dup.services.json.CustomJsonObjectRequest;

public class MainActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CustomJsonObjectRequest.send(this, Request.Method.GET, CustomJsonObjectRequest.URI_API);

        Handler hd = new Handler();
        hd.postDelayed(this, 5000);
    }
    //depois da chamada da handler ele execulta essa thread e chama a outra activity e fecha essa tela.
    @Override
    public void run() {
        startActivity(new Intent(this, MapsActivity.class));
        finish();
    }
}
