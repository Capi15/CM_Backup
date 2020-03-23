package com.example.cm_backup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "notas.db";

    public DB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contrato.Details.SQL_CREATE_ENTRIES);
        db.execSQL(Contrato.Nota.SQL_CREATE_ENTRIES);

        db.execSQL("insert into " + Contrato.Details.TABLE_NAME + " values ('Baixa', false)");
        db.execSQL("insert into " + Contrato.Details.TABLE_NAME + " values ('Baixa', false)");
        db.execSQL("insert into " + Contrato.Details.TABLE_NAME + " values ('Baixa', false)");

        db.execSQL("insert into " + Contrato.Nota.TABLE_NAME + " values ('Titulo1', 'Descrição1', 'Data1')");
        db.execSQL("insert into " + Contrato.Nota.TABLE_NAME + " values ('Titulo2', 'Descrição2', 'Data2')");
        db.execSQL("insert into " + Contrato.Nota.TABLE_NAME + " values ('Titulo3', 'Descrição3', 'Data3')");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(Contrato.Nota.SQL_DROP_ENTRIES);
        db.execSQL(Contrato.Details.SQL_DROP_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}