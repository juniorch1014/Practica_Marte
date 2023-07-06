package com.upn.chuquilin.practica_martes.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movimientos")
public class Movimientos {
    public int idM;
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "cuentaID")
    public int cuentaID;
    @ColumnInfo(name = "tipoMovimiento")
    public String tipoMovimiento;
    @ColumnInfo(name = "monto")
    public int monto;
    @ColumnInfo(name = "motivo")
    public String motivo;
    @ColumnInfo(name = "latitud")
    public String latitud;
    @ColumnInfo(name = "longitud")
    public String longitud;
    @ColumnInfo(name = "urlimagen")
    public String urlimagen;
    @ColumnInfo(name = "sincronizadoMovimientos")
    public boolean sincronizadoMovimientos;
    @ColumnInfo(name = "imagenBase64")
    public String imagenBase64;

}
