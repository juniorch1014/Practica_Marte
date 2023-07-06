package com.upn.chuquilin.practica_martes.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public static Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl("https://6478763f362560649a2dd15c.mockapi.io/") // revisar
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
