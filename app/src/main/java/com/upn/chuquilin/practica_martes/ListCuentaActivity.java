package com.upn.chuquilin.practica_martes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upn.chuquilin.practica_martes.Adapters.CuentaAdapter;
import com.upn.chuquilin.practica_martes.db.AppDatabase;
import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.repositories.CuentaRepository;

import java.util.ArrayList;
import java.util.List;

public class ListCuentaActivity extends AppCompatActivity {

    RecyclerView rvListaCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cuenta);

        Log.d("APP_MAIN", "ListasaaaassCuenta");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListaCuenta = findViewById(R.id.rvListaCuenta);
        rvListaCuenta.setLayoutManager(layoutManager);

        //**LISTAR***
        LoadingDBCuenta();
        //***********
    }

    private void LoadingDBCuenta() {
        AppDatabase db = AppDatabase.getInstance(this);
        CuentaRepository repository = db.cuentaRepository();
        List<Cuenta> mdataCuenta = repository.getAllCuenta();

        CuentaAdapter mAdapter = new CuentaAdapter(mdataCuenta);
        rvListaCuenta.setAdapter(mAdapter);
        Log.i("MAIN_APP: DB", new Gson().toJson(mdataCuenta));
        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();

    }
}