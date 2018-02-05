package com.bocio23.dam.broadcast.Interfaces;

import com.bocio23.dam.broadcast.POJO.LLamada;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by dam on 24/01/2018.
 */

public interface ApiServidorRest {
    // https//.../persona - GET ->Listado de personas
    @GET("llamada")
    Call<ArrayList<LLamada>> getLlamada();

    // https//.../persona - POST - PERSONA ->  persona
    @POST("llamada")
    Call<LLamada> postLlamada(@Body LLamada body);

}
