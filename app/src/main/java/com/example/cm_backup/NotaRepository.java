package com.example.cm_backup;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotaRepository {
    private NotaDao notaDao;
    private LiveData<List<Nota>> allNotas;

    public NotaRepository(Application application){
        NotaRoomDatabase db = NotaRoomDatabase.getDatabase(application);
        notaDao = db.notaDao();
        allNotas = notaDao.getAllNotas();

    }

    LiveData<List<Nota>> getAllNotas(){
        return allNotas;
    }

    public void insert (Nota nota){
        new insertAsyncTask(notaDao).execute(nota);
    }

    private static class insertAsyncTask extends AsyncTask<Nota, Void, Void> {

        private NotaDao mAsyncTaskDao;

        insertAsyncTask(NotaDao notaDao) {
            mAsyncTaskDao = notaDao;
        }

        @Override
        protected Void doInBackground(final Nota... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
