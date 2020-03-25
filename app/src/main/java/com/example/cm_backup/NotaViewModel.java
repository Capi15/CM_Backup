package com.example.cm_backup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotaViewModel extends AndroidViewModel {

    private NotaRepository mRepository;

    //Permite guardar em cache uma lista com as minhas notas
    private LiveData<List<Nota>> allNotas;

    public NotaViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NotaRepository(application);
        allNotas = mRepository.getAllNotas();
    }

    public void insert(Nota nota){
        mRepository.insert(nota);
    }

    public LiveData<List<Nota>> getAllNotas() {
        return allNotas;
    }
}
