package com.upn.chuquilin.practica_martes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upn.chuquilin.practica_martes.Adapters.CuentaAdapter;
import com.upn.chuquilin.practica_martes.Adapters.MovimientoAdapter;
import com.upn.chuquilin.practica_martes.db.AppDatabase;
import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.entities.Movimientos;
import com.upn.chuquilin.practica_martes.repositories.CuentaRepository;
import com.upn.chuquilin.practica_martes.repositories.MovimientoRepository;

import java.util.List;

public class ListMovimientoActivity extends AppCompatActivity {

    RecyclerView rvListaMovimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movimiento);

        Log.d("MAIN_APP", "ListasaaaaMovimieeentos");

        int idObtener;
        idObtener = getIntent().getIntExtra("id2",0);
        Log.d("APP_MAIN: idListM", String.valueOf(idObtener));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListaMovimiento = findViewById(R.id.rvListaMovimiento);
        rvListaMovimiento.setLayoutManager(layoutManager);

        //**LISTAR***
        LoadingDBMovimiento(idObtener);
    }

    private void LoadingDBMovimiento(int idObtenido) {
        AppDatabase db = AppDatabase.getInstance(this);
        MovimientoRepository repository = db.movimientoRepository();
        List<Movimientos> mdataMovimiento = repository.searchMovimientos(idObtenido);

        MovimientoAdapter mAdapter = new MovimientoAdapter(mdataMovimiento);
        rvListaMovimiento.setAdapter(mAdapter);
        Log.i("MAIN_APP: DBMovi", new Gson().toJson(mdataMovimiento));
        Toast.makeText(getBaseContext(), "MOSTRANDO DATOS", Toast.LENGTH_SHORT).show();

    }
}