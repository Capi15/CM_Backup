package com.example.cm_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillLista();
    }


    private void fillLista(){
        ArrayList<String> arrayItems = new ArrayList<>();
        arrayItems.add("Nota1");
        arrayItems.add("Nota2");
        arrayItems.add("Nota3");

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayItems);
        ((ListView) findViewById(R.id.id_lista)).setAdapter(itemsAdapter);
    }
}
