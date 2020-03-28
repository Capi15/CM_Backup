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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cm_backup.utils.Utils;
import com.example.cm_backup.R;
import com.example.cm_backup.adapters.CustomCursorAdapter;
import com.example.cm_backup.db.Contrato;
import com.example.cm_backup.db.DB;
import com.example.cm_backup.entities.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView editTitulo;

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

        /*ContentValues cv = new ContentValues();
        cv.put(Contrato.Details.COLUMN_PRIORIDADE, "baixo");
        cv.put(Contrato.Details.COLUMN_COMPLETO, 0);
        db.insert(Contrato.Details.TABLE_NAME, null,cv);

        cv = new ContentValues();
        cv.put(Contrato.Nota._ID, NotasActivity.count);
        cv.put(Contrato.Nota.COLUMN_TITULO ,"outra nota");
        cv.put(Contrato.Nota.COLUMN_DESCRICAO, "ihfvh");
        cv.put(Contrato.Nota.COLUMN_DATA, "22-03-2017");
        db.insert(Contrato.Nota.TABLE_NAME, null,cv);*/

        lista = (ListView) findViewById(R.id.id_lista);
        registerForContextMenu(lista);
        spin = ((Spinner) findViewById(R.id.id_spinner));
        preencheLista();

        editTitulo = (TextView)findViewById(R.id.titulo_id) ;

        //preencheSpinner();
    }

    public void refresh(){
        getCursor();
        cca.swapCursor(c);
    }

    private void getCursor() {
        String sql = "select "
                + " * " +  " FROM "
                + Contrato.Nota.TABLE_NAME + ", " + Contrato.Details.TABLE_NAME
                + " WHERE " + Contrato.Nota.COLUMN_ID_DETAILS
                + "=" + Contrato.Details.TABLE_NAME + "." + Contrato.Details._ID;

        c = db.rawQuery(sql, null);
    }

    public void goToNotes (View view){
        Intent intent = new Intent (this, NotasActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, REQUEST_CODE_OP_1);
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
        String id_titulo = Contrato.Nota.COLUMN_TITULO;

        switch (item.getItemId()){
            case R.id.editar:
                finish();
                Intent intent = new Intent (this, NotasActivity.class);
                String[] notaParams = {String.valueOf(Nota.class)};
                intent.putExtra("notaParams",notaParams);
                startActivityForResult(intent, REQUEST_CODE_OP_1);
                return true;
            case R.id.remover:
                //Toast.makeText(MainActivity.this, id_nota, Toast.LENGTH_SHORT).show();
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
