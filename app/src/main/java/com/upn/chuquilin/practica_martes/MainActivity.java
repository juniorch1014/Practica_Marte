package com.upn.chuquilin.practica_martes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upn.chuquilin.practica_martes.db.AppDatabase;
import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.repositories.CuentaRepository;
import com.upn.chuquilin.practica_martes.services.CuentaService;
import com.upn.chuquilin.practica_martes.utils.RetrofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    EditText nameCuenta;
    Button btRegistrarCuenta;
    Button btListarCuenta;
    Button btSincronizarCuenta;

    Retrofit mRetrofit;

    int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRetrofit = RetrofitBuilder.build();

        nameCuenta = findViewById(R.id.nameCuenta);
        btRegistrarCuenta = findViewById(R.id.btRegistrarCuenta);
        btListarCuenta = findViewById(R.id.btListarCuentas);
        btSincronizarCuenta = findViewById(R.id.btSincronizarCuenta);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        CuentaRepository repositoryC = db.cuentaRepository();

        btRegistrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameCuenta.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Llenar Datos", Toast.LENGTH_SHORT).show();
                } else {
                    Cuenta cuenta = new Cuenta();
                    cuenta.nameCuenta = nameCuenta.getText().toString();
                    cuenta.sincronizadoCuenta = false;
                    repositoryC.createCuenta(cuenta);
                    nameCuenta.setText("");
                    Log.i("MAIN_APP: Guarda en DB", new Gson().toJson(cuenta));
                }

            }
        });

        btListarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListCuentaActivity.class);
                startActivity(intent);
            }
        });

        CuentaService serviceC = mRetrofit.create(CuentaService.class);

        btSincronizarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()) {
                    List<Cuenta> SinSicroCuenta = repositoryC.searchCuentas(false);
                    for (Cuenta cuenta :SinSicroCuenta) {
                        Log.d("MAIN_APP: DB SSincro", new Gson().toJson(cuenta));
                        cuenta.sincronizadoCuenta = true;
                        repositoryC.updateCuenta(cuenta);
                        //*****SINCRO*************************
                        SincronizacionCuenta(serviceC,cuenta);

                    }
                    List<Cuenta> EliminarBDCuenta = repositoryC.getAllCuenta();
                    downloadingMockAPICuenta(serviceC,repositoryC,EliminarBDCuenta,db);

                    Toast.makeText(getBaseContext(), "SINCRONIZADO", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getBaseContext(), "NO HAY INTERNET", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }

    private void SincronizacionCuenta(CuentaService cuentaService, Cuenta cuenta){
            Call<Cuenta> call= cuentaService.create(cuenta);
            call.enqueue(new Callback<Cuenta>() {
                @Override
                public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                    if (response.isSuccessful()) {
                        Cuenta data = response.body();
                        Log.i("MAIN_APP: MockAPI", new Gson().toJson(data));
                    }
                }

                @Override
                public void onFailure(Call<Cuenta> call, Throwable t) {

                }
            });


    }
    private void downloadingMockAPICuenta(CuentaService cuentaService,CuentaRepository cuentaRepository , List<Cuenta> EliminarCuenta, AppDatabase database){
                    //***Eleminar datos de BD
                     cuentaRepository.deleteList(EliminarCuenta);
                    //Cargar datos de MockAPI
//
//            Call<List<Cuenta>> call = cuentaService.getAllUser();
//            call.enqueue(new Callback<List<Cuenta>>() {
//                @Override
//                public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
//                    List<Cuenta> data = response.body();
//                    Log.i("MAIN_APP", new Gson().toJson(data));
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Cuenta>> call, Throwable t) {
//
//                }
//            });
            //Call<List<Cuenta>> call = cuentaService.getCuentasBySincro(true);
            Call<List<Cuenta>> call = cuentaService.getAllUser();
            call.enqueue(new Callback<List<Cuenta>>() {
                @Override
                public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                    List<Cuenta> data = response.body();
                    Log.i("MAIN_APP", new Gson().toJson(data));

                    for (Cuenta cuenta : data) {
                        cuentaRepository.createCuenta(cuenta);
                        
                    }
//                    database.runInTransaction(new Runnable() {
//                        @Override
//                        public void run() {
//                            cuentaRepository.AgregarList(data);
//                        }
//                    });


                }

                @Override
                public void onFailure(Call<List<Cuenta>> call, Throwable t) {

                }
            });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}