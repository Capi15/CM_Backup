package com.example.cm_backup;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cm_backup.Nota;

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
}
