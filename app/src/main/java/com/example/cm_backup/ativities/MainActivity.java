package com.example.cm_backup.ativities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cm_backup.R;
import com.example.cm_backup.adapters.CustomArrayAdapter;
import com.example.cm_backup.entities.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Nota> arrayNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerForContextMenu((ListView) findViewById(R.id.id_lista));
        arrayNota = new ArrayList<>();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        Nota nota = arrayNota.get(index);

        switch (item.getItemId()){
            case R.id.editar:
                Toast.makeText(this, "editado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.remover:
                Toast.makeText(this, "removido", Toast.LENGTH_SHORT).show();
                return true;
            default:
            return super.onContextItemSelected(item);
        }


    }


}
