package com.upn.chuquilin.practica_martes.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.entities.Movimientos;
import com.upn.chuquilin.practica_martes.repositories.CuentaRepository;
import com.upn.chuquilin.practica_martes.repositories.MovimientoRepository;

@Database(entities = {Cuenta.class, Movimientos.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CuentaRepository cuentaRepository();
    public abstract MovimientoRepository movimientoRepository();

    public static AppDatabase getInstance(Context context){
        return Room.databaseBuilder(context,AppDatabase.class, "practica_martes")
                .allowMainThreadQueries()
                .build();
    }
}
