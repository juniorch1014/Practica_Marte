package com.upn.chuquilin.practica_martes.services;

import com.upn.chuquilin.practica_martes.entities.Cuenta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CuentaService {

    @GET("cuentas")
    Call<List<Cuenta>> getAllUser();

    @GET("cuentas")
    Call<List<Cuenta>> getAllUser(@Query("limit") int limit, @Query("page") int page);

    @GET("cuentas")
    Call<List<Cuenta>> getBuscar(@Query("search") String nombre);

    @GET("cuentas")
    Call<List<Cuenta>> getCuentasBySincro(@Query("sincronizadoCuenta") boolean sincronizar);

    @GET("cuentas/{id}")
    Call<Cuenta> findUser(@Path("id") int id);

    // users
    @POST("cuentas")
    Call<Cuenta> create(@Body Cuenta user);

    @PUT("cuentas/{id}")
    Call<Cuenta> update(@Path("id") int id, @Body Cuenta user);

    @DELETE("cuentas/{id}")
    Call<Void> delete(@Path("id") int id);
}
