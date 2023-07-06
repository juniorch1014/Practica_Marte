package com.upn.chuquilin.practica_martes.repositories;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.upn.chuquilin.practica_martes.entities.Cuenta;

import java.util.List;

@Dao
public interface CuentaRepository {


    @Query("SELECT * FROM cuentas")
    List<Cuenta> getAllCuenta();

    @Query("SELECT * FROM cuentas WHERE sincronizadoCuenta LIKE :searchSincro")
    List<Cuenta> searchCuentas(boolean searchSincro);

    @Query("SELECT * FROM cuentas WHERE id LIKE :id")
    Cuenta searchCuentaID(int id);

    @Insert
    void createCuenta(Cuenta cuenta);
    @Insert
    void AgregarList(List<Cuenta> cuentas);
    @Update
    void  updateCuenta(Cuenta cuenta);

    @Delete
    void delete(Cuenta cuenta);

    @Delete
    void deleteList(List<Cuenta> cuentas);

}
