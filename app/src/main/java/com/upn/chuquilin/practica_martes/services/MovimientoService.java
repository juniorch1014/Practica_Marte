package com.upn.chuquilin.practica_martes.services;

import com.google.gson.annotations.SerializedName;
import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.entities.Movimientos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovimientoService {
    @GET("movimientos")
    Call<List<Movimientos>> getAllUser();

    @GET("movimientos")
    Call<List<Movimientos>> getAllUser(@Query("limit") int limit, @Query("page") int page);

    @GET("movimientos")
    Call<List<Movimientos>> getBuscar(@Query("search") String nombre);

    @GET("movimientos")
    Call<List<Movimientos>> getCuentasBySincro(@Query("sincronizadoMovimientos") boolean sincronizar);

    @GET("movimientos/{id}")
    Call<Movimientos> findUser(@Path("id") int id);

    // users
    @POST("movimientos")
    Call<Movimientos> create(@Body Movimientos user);

    @PUT("movimientos/{id}")
    Call<Movimientos> update(@Path("id") int id, @Body Movimientos user);

    @DELETE("movimientos/{id}")
    Call<Void> delete(@Path("id") int id);

    @POST ("image")
    Call<ImagenResponse> guardarImage (@Body ImagenToSave imagen);


    class  ImagenResponse{
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
    }
    class ImagenToSave{
        String base64Image;

        public ImagenToSave(String base64Image){
            this.base64Image = base64Image;
        }
    }
}
