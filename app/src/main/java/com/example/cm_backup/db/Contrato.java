package com.example.cm_backup.db;

import android.provider.BaseColumns;
import android.telecom.Call;

public class Contrato {
    public static final String TEXT_TYPE = " TEXT ";
    public static final String INT_TYPE = " INT ";
    public static final String BOOLEAN_TYPE = " BOOLEAN ";
    public static final String DATE_TYPE = " DATE ";

    public Contrato(){

    }

    public static abstract class Nota implements BaseColumns{
        public static final String TABLE_NAME = "nota";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_ID_DETAILS = "id_details";

        public static final String[] PROJECTION = {
                Nota._ID, Nota.COLUMN_TITULO, Nota.COLUMN_DESCRICAO, Nota.COLUMN_DATA
        };

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Nota.TABLE_NAME + "(" + Nota._ID
                + INT_TYPE + " PRIMARY KEY," + Nota.COLUMN_TITULO
                + TEXT_TYPE + "," +  Nota.COLUMN_DESCRICAO + TEXT_TYPE
                + "," + Nota.COLUMN_DATA + TEXT_TYPE + "," + Nota.COLUMN_ID_DETAILS
                + INT_TYPE + " REFERENCES " + Details.TABLE_NAME + "("
                + Details._ID + "));";

        public static final String SQL_DROP_ENTRIES =
                "DROP TABLE " + Nota.TABLE_NAME + ";";

    }

    public static abstract class Details implements BaseColumns{

        public static final String TABLE_NAME = "details";
        public static final String COLUMN_PRIORIDADE = "prioridade";
        public static final String COLUMN_COMPLETO = "completo";

        public static final String[] PROJECTION = {
                Details._ID, Details.COLUMN_PRIORIDADE, Details.COLUMN_COMPLETO
        };

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Details.TABLE_NAME + "(" + Details._ID
                 + INT_TYPE + " PRIMARY KEY," + Details.COLUMN_PRIORIDADE
                 + TEXT_TYPE + "," +  Details.COLUMN_COMPLETO + BOOLEAN_TYPE
                 + ");";

        public static final String SQL_DROP_ENTRIES =
                "DROP TABLE " + Details.TABLE_NAME + ";";
    }
}
