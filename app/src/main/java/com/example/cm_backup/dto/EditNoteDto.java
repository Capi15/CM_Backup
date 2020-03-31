package com.example.cm_backup.dto;

import java.io.Serializable;

public class EditNoteDto implements Serializable {

    private static final long serialversionUID = 86459306873450L;

    public final int id;
    public final String titulo;
    public final String descricao;
    public final String data;

    public EditNoteDto(int id, String titulo, String descricao, String data){
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }
}
