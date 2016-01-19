package br.com.dup.services.json;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by joeverson.... email: joerverson.santos@gmail.com on 15/01/16.
 */
public class CustomVolleyRequestQueue {
    private static CustomVolleyRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private CustomVolleyRequestQueue(Context ctx){
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    /**
     * method used for not intance more one time
     *
     * param context.
     * return instance of request queue.
     * */
    public static synchronized CustomVolleyRequestQueue getInstance(Context ctx){
        if(mInstance == null)
            mInstance = new CustomVolleyRequestQueue(ctx);

        return mInstance;
    }

    //preparando para iniciar o reqiest queue caso o volley nao inicialize, e dando um cache para ele de 10mb
    public RequestQueue getRequestQueue(){
        if (this.mRequestQueue == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            this.mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            this.mRequestQueue.start();
        }
        return mRequestQueue;

    }
}
