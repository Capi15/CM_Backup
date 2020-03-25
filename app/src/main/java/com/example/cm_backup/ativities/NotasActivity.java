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

public class NotasActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText mTextTitulo = (EditText)findViewById(R.id.titulo_view_text);
    EditText mTextDescricao = (EditText)findViewById(R.id.descricao_view_text);
    EditText mTextData = (EditText)findViewById(R.id.data_view_text);
    EditText mTextPrioridade = (EditText)findViewById(R.id.prioridade_view_text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

    }

    public void goToList (View view){
        ContentValues cvNota = new ContentValues();
        ContentValues cvDetails = new ContentValues();
        cvNota.put(Contrato.Nota.COLUMN_TITULO, String.valueOf(mTextTitulo));
        cvNota.put(Contrato.Nota.COLUMN_DESCRICAO, String.valueOf(mTextDescricao));
        cvNota.put(Contrato.Nota.COLUMN_DATA, String.valueOf(mTextData));
        cvDetails.put(Contrato.Details.COLUMN_PRIORIDADE, String.valueOf(mTextPrioridade));
        cvDetails.put(Contrato.Details.COLUMN_COMPLETO, 0);
        db.insert(Contrato.Details.TABLE_NAME, null, cvDetails);
        db.insert(Contrato.Details.TABLE_NAME, null, cvNota);

        Intent output = new Intent();
        output.putExtra("FIRS_KEY", cvNota);
        output.putExtra("SECOND_KEY", cvDetails);
        setResult(RESULT_OK, output);
        finish();
    }

    public void updateList (){
        ContentValues cvNota = new ContentValues();
        ContentValues cvDetails = new ContentValues();
        cvNota.put(Contrato.Nota.COLUMN_TITULO, String.valueOf(mTextTitulo));
        cvNota.put(Contrato.Nota.COLUMN_DESCRICAO, String.valueOf(mTextDescricao));
        cvNota.put(Contrato.Nota.COLUMN_DATA, String.valueOf(mTextData));
        cvDetails.put(Contrato.Details.COLUMN_PRIORIDADE, String.valueOf(mTextPrioridade));
        cvDetails.put(Contrato.Details.COLUMN_COMPLETO, 0);
        db.insert(Contrato.Details.TABLE_NAME, null, cvDetails);
        db.insert(Contrato.Details.TABLE_NAME, null, cvNota);
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
