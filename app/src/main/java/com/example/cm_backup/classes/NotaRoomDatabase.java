package com.example.cm_backup.classes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Nota.class}, version = 2, exportSchema = false)
public abstract class NotaRoomDatabase extends RoomDatabase {

    public abstract NotaDao notaDao();
    private static NotaRoomDatabase INSTANCE;



    //para que a classe seja do tipo singleton, ou seja, só possa ser instanciada uma vez
    public static NotaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotaRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Criação da base de dados
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotaRoomDatabase.class, "nota_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }

            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final NotaDao mDao;
        String[] titulo = {"Titulo1", "Titulo2", "Titulo3"};
        String[] descricoes ={"descricao1","descricao2","descricao3"};
        String[] data = {"data1","data2","data3"};


        PopulateDbAsync(NotaRoomDatabase db) {
            mDao = db.notaDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if(mDao.getAnyNota().length < 1 ){
                for (int i = 0; i <= titulo.length - 1; i++) {
                    Nota nota = new Nota(titulo[i], descricoes[i], data[i]);
                    mDao.insert(nota);
                }
            }
            return null;
        }
    }
}


