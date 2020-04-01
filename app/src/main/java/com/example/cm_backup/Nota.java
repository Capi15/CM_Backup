package com.example.cm_backup;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Nota")
public class Nota {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

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



    /*public Nota(@NonNull String titulo, @NonNull String descricao, @NonNull String data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }*/


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


    public void setTitulo(@NonNull String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    public void setData(@NonNull String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
