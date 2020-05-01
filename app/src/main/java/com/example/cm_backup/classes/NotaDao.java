package com.example.cm_backup.classes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Nota nota);

    @Query("DELETE FROM nota")
    void deleteAll();

    @Query("SELECT * FROM nota ORDER BY titulo ASC")
    LiveData<List<Nota>> getAllNotas();


    //verifica se existe pelo menos 1 nota
    @Query("SELECT * from nota LIMIT 1")
    Nota[] getAnyNota();

    @Delete
    void deleteNota(Nota nota);

    @Update
    void update(Nota nota);

    @Query("SELECT * FROM nota WHERE id = :id")
    LiveData<Nota> getById(int id);
}
