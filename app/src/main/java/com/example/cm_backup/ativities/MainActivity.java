package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cm_backup.R;
import com.example.cm_backup.adapters.CustomArrayAdapter;
import com.example.cm_backup.entities.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillLista();
    }


    private void fillLista(){
        ArrayList<Nota> arrayItems = new ArrayList<>();
        arrayItems.add(new Nota("Nota1", "descricao2", "data"));
        arrayItems.add(new Nota("Nota1", "descricao2", "data"));
        arrayItems.add(new Nota("Nota1", "descricao2", "data"));

        CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(this, arrayItems);
        ((ListView) findViewById(R.id.id_lista)).setAdapter(itemsAdapter);
    }
}
