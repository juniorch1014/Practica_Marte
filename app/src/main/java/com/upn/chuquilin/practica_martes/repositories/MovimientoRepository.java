package com.upn.chuquilin.practica_martes.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.entities.Movimientos;

import java.util.List;

@Dao
public interface MovimientoRepository {

    @Query("SELECT * FROM movimientos")
    List<Movimientos> getAllMovimientos();

    @Query("SELECT * FROM movimientos WHERE sincronizadoMovimientos LIKE :searchSincro")
    List<Movimientos> searchMovimiento(boolean searchSincro);

    @Query("SELECT * FROM movimientos WHERE cuentaID LIKE :id")
    List<Movimientos> searchMovimientos(int id);

    @Insert
    void createMovimientos(Movimientos movimientos);

    @Update
    void  updateMovimiento(Movimientos movimientos);

    @Delete
    void delete(Movimientos movimientos);
    @Delete
    void deleteList(List<Movimientos> movimientos);
}
