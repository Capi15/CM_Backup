package com.example.cm_backup.classes;

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

    public void deleteAll() {
        new deleteAllNotasAsyncTask(notaDao).execute();
    }

    public void deleteNota(Nota word)  {
        new deleteWordAsyncTask(notaDao).execute(word);
    }

    public void update(Nota nota)  {
        new updateNotaAsyncTask(notaDao).execute(nota);
    }

    public LiveData<Nota> getById(int id) {
        return notaDao.getById(id);
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

    private static class deleteAllNotasAsyncTask extends AsyncTask<Void, Void, Void>{
        private NotaDao mAsyncTaskDao;

        deleteAllNotasAsyncTask(NotaDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Nota, Void, Void> {
        private NotaDao mAsyncTaskDao;

        deleteWordAsyncTask(NotaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Nota... params) {
            mAsyncTaskDao.deleteNota(params[0]);
            return null;
        }
    }

    private class updateNotaAsyncTask extends AsyncTask<Nota, Void, Void>{
        private NotaDao mAsyncTaskDao;

        updateNotaAsyncTask(NotaDao notaDao) {
            mAsyncTaskDao = notaDao;
        }

        @Override
        protected Void doInBackground(final Nota... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
