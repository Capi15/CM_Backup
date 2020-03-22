package com.example.cm_backup.entities;

import java.util.Date;

public class Nota {

    private String titulo;
    private String descricao;
    private String data;
    //private Date data;

    public Nota(String titulo, String descricao, String data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }
}
