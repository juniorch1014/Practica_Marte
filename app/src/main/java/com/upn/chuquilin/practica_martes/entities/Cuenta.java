package com.upn.chuquilin.practica_martes.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cuentas")
public class Cuenta {

    public  int idC;
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "nameCuenta")
    public String nameCuenta;
    @ColumnInfo(name = "sincronizadoCuenta")
    public boolean sincronizadoCuenta;


}
