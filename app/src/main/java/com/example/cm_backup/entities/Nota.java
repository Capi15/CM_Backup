package com.example.cm_backup.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Nota")
public class Nota {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "titulo")
    private String titulo;

    @NonNull
    @ColumnInfo(name = "descricao")
    private String descricao;

    @NonNull
    @ColumnInfo(name = "data")
    private String data;
    //private Date data;



    public Nota(@NonNull String titulo, @NonNull String descricao, @NonNull String data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getTitulo() {
        return this.titulo;
    }
}
