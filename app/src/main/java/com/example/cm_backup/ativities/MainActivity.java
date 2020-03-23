package com.example.cm_backup.ativities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cm_backup.R;
import com.example.cm_backup.adapters.CustomArrayAdapter;
import com.example.cm_backup.adapters.CustomCursorAdapter;
import com.example.cm_backup.db.Contrato;
import com.example.cm_backup.db.DB;
import com.example.cm_backup.entities.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DB mDbHelper;
    SQLiteDatabase db;
    Cursor c, c_notas;
    ListView lista;
    CustomCursorAdapter cca;

    ArrayList<Nota> arrayNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DB(this);
        db = mDbHelper.getReadableDatabase();

        lista = (ListView) findViewById(R.id.id_lista);
        registerForContextMenu(lista);
        preencheLista();
        //preencheSpinner();
    }

    public void goToNotes (View view){
        Intent intent = new Intent (this, NotasActivity.class);
        startActivity(intent);
    }

    /*private void preencheSpinner(){
        c_pessoas = db.rawQuery("select " + Contrato.Details._ID + ", "
        + Contrato.Details.COLUMN_PRIORIDADE + " FROM "
        + Contrato.Details.TABLE_NAME, null);

        Spinner s = (Spinner) findViewById(R.id.spinner1);
        String[] adapterCols = new String[]{Contrato.Nota.COLUMN_TITULO};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                c_pessoas,
                adapterCols,
                adapterRowViews, 0
        );
    }*/


    private void preencheLista(){
        c = db.query(false,
                Contrato.Nota.TABLE_NAME,
                Contrato.Nota.PROJECTION,
                null,
                null,
                null,
                null,
                null,
                null);

        cca = new CustomCursorAdapter(MainActivity.this, c);
        lista.setAdapter(cca);
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
