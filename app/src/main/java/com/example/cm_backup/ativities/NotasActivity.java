package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.cm_backup.R;
import com.example.cm_backup.db.Contrato;
import com.example.cm_backup.db.DB;
import com.example.cm_backup.utils.Utils;

public class NotasActivity extends AppCompatActivity {
    DB mDbHelper;
    SQLiteDatabase db;


    EditText mTextTitulo;
    EditText mTextDescricao;
    EditText mTextData;
    EditText mTextPrioridade;
    MainActivity main = new MainActivity();
    public static int count = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        mDbHelper = new DB(this);
        db = mDbHelper.getReadableDatabase();
        count++;

        String valor = getIntent().getStringExtra(Utils.PARAM_TITULO);
        EditText tituloText = (EditText) findViewById(R.id.data_view_text);
        tituloText.setText(valor);

        mTextTitulo = (EditText)findViewById(R.id.titulo_view_text);
        mTextDescricao = (EditText)findViewById(R.id.descricao_view_text);
        mTextData = (EditText)findViewById(R.id.data_view_text);
        mTextPrioridade = (EditText)findViewById(R.id.prioridade_view_text);
    }

    public void goToList (View view){

        Intent output = new Intent();

        ContentValues cv = new ContentValues();
        cv.put(Contrato.Details.COLUMN_PRIORIDADE, "baixo");
        cv.put(Contrato.Details.COLUMN_COMPLETO, 0);
        db.insert(Contrato.Details.TABLE_NAME, null,cv);

        cv = new ContentValues();
        cv.put(Contrato.Nota._ID, count);
        cv.put(Contrato.Nota.COLUMN_TITULO ,"outra nota");
        cv.put(Contrato.Nota.COLUMN_DESCRICAO, "ihfvh");
        cv.put(Contrato.Nota.COLUMN_DATA, "22-03-2017");
        db.insert(Contrato.Nota.TABLE_NAME, null,cv);
        main.refresh();
        //setResult(RESULT_OK, output);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(db.isOpen()){
            db.close();
            db = null;
        }
    }

}
