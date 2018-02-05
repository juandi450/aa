package com.bocio23.dam.broadcast;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.bocio23.dam.broadcast.Interfaces.ApiServidorRest;
import com.bocio23.dam.broadcast.POJO.LLamada;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class ServicioClienteRest extends Service {

    public ServicioClienteRest() {
        //1,se ejecuta una vez
    }

    @Override
    public void onCreate() {
        //2,se ejecuta una vez
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags,
                              int startId) {
        //3,se ejecuta muchas veces
        //START_NOT_STICKY,START_REDELIVER_INTENT,START_STICKY

        //recogemos los dos parametros que nos llegan


        String number=intent.getStringExtra("number");
        String type=intent.getStringExtra("type");
        String date= intent.getStringExtra("date");
        String calldate=intent.getStringExtra("callend");


        LLamada llamada1=new LLamada(number,date,type,calldate);


        //1.creamos el objeto retrofit
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://server-rest-juandi45.c9users.io/iOS/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        //2.creamos un objeto cliente de la interface
        ApiServidorRest cliente= retrofit.create(ApiServidorRest.class);

        //3.creo el objeto call para realizar la llamada
        //llamada para post
        Call<LLamada> llamada=cliente.postLlamada(llamada1);

        //4.encolamos la peticion
        llamada.enqueue(new Callback<LLamada>() {
            @Override
            public void onResponse(Call<LLamada> call,
                                   Response<LLamada> response) {
                //obtenemos la respuesta con onresponse

                Log.i(TAG, "post submitted to API." + response.body().toString());

                stopSelf();

            }
            @Override
            public void onFailure(Call<LLamada> call, Throwable t) {
                Log.i(TAG, "fallo");

                Log.d("zzz",t.getMessage());
                stopSelf();
            }
        });
        //llamada para hacer un get
        /*Call<ArrayList<LLamada>> call2 = cliente.getLlamada();
        call2.enqueue(new Callback<ArrayList<LLamada>>() {
            @Override
            public void onResponse(Call<ArrayList<LLamada>> call, Response<ArrayList<LLamada> >response) {
                ArrayList<LLamada>ap=response.body();

            }
            @Override
            public void onFailure(Call<ArrayList<LLamada>> call, Throwable t) {
//t.getLocalizedMessage()
            }
        });*/



        return START_REDELIVER_INTENT;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}
