package com.example.cm_backup.ativities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cm_backup.R;
import com.example.cm_backup.adapters.CustomCursorAdapter;
import com.example.cm_backup.db.Contrato;
import com.example.cm_backup.db.DB;
import com.example.cm_backup.entities.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DB mDbHelper;
    SQLiteDatabase db;
    Cursor c, c_notas;
    Spinner spin;
    ListView lista;
    CustomCursorAdapter cca;
    private int REQUEST_CODE_OP_1 = 1;


    ArrayList<Nota> arrayNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DB(this);
        db = mDbHelper.getReadableDatabase();

        lista = (ListView) findViewById(R.id.id_lista);
        registerForContextMenu(lista);
        spin = ((Spinner) findViewById(R.id.id_spinner));
        preencheLista();

        //preencheSpinner();
    }

    public void refresh(){
        getCursor();
        cca.swapCursor(c);
    }

    /*private void getCursor() {
        String sql = "select "
                + Contrato.Nota.COLUMN_TITULO + ", " + Contrato.Nota.COLUMN_DESCRICAO
                + ", " + Contrato.Nota.COLUMN_DATA + ", " + Contrato.Details.COLUMN_PRIORIDADE
                + ", " + Contrato.Details.COLUMN_COMPLETO + " FROM "
                + Contrato.Nota.TABLE_NAME + ", " + Contrato.Details.TABLE_NAME
                + " WHERE " + Contrato.Nota.COLUMN_ID_DETAILS
                + "=" + Contrato.Details.TABLE_NAME + "." + Contrato.Details._ID;

        c = db.rawQuery(sql, null);
    }*/


    private void getCursor() {
        String sql = "select " + Contrato.Nota.TABLE_NAME + "."
                + Contrato.Nota._ID + "," + Contrato.Details.TABLE_NAME
                + " FROM " + Contrato.Nota.COLUMN_TITULO + ", " + Contrato.Nota.COLUMN_DESCRICAO
                + ", " + Contrato.Nota.COLUMN_DATA + ", " + Contrato.Details.COLUMN_PRIORIDADE
                + ", " + Contrato.Details.COLUMN_COMPLETO + " WHERE " + Contrato.Nota.COLUMN_ID_DETAILS
                + "=" + Contrato.Details.TABLE_NAME + "." + Contrato.Details._ID;

        c = db.rawQuery(sql, null);
    }

    public void goToNotes (View view, int re){
        Intent intent = new Intent (this, NotasActivity.class);
        startActivityForResult(intent, REQUEST_CODE_OP_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_OP_1) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "sucesso", Toast.LENGTH_SHORT).show();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
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
        //getCursor();

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
        int itemPosition = info.position;
        c.moveToPosition(itemPosition);
        int id_nota = c.getInt(c.getColumnIndex(Contrato.Nota._ID));

        switch (item.getItemId()){
            case R.id.editar:
                finish();
                Intent intent = new Intent (this, NotasActivity.class);
                startActivity(intent);
                return true;
            case R.id.remover:
                deleteFromBD(id_nota);
                return true;
            default:
            return super.onContextItemSelected(item);
        }
    }

    public void deleteFromBD(int id){
        db.delete(Contrato.Nota.TABLE_NAME, Contrato.Nota._ID + " = ?", new String[]{id+""});
        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(!c.isClosed()){
            c.close();
            c = null;
        }
        if(!c_notas.isClosed()){
            c_notas.close();
            c_notas = null;
        }
        if(db.isOpen()){
            db.close();
            db = null;
        }
    }
}
