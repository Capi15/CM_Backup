package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.cm_backup.R;
import com.example.cm_backup.dto.EditNoteDto;

import static com.example.cm_backup.ativities.NotasStartActivity.EXTRA_DATA_FOR_UPDATE;

public class NotasActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.cm_backup.ativities.REPLY";

    private EditText mEditTituloView;
    private EditText mEditDescricaoView;
    private EditText mEditDataView;
    private static int id = -1;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        mEditTituloView = findViewById(R.id.titulo_view_text);
        mEditDescricaoView = findViewById(R.id.descricao_view_text);
        mEditDataView = findViewById(R.id.data_view_text);

        extras = getIntent().getExtras();

        // permite passar os valores da atividade aterior para os campos desta atividade
        if (extras != null) {
            EditNoteDto oiNote = (EditNoteDto) extras.getSerializable(EXTRA_DATA_FOR_UPDATE);
            if (oiNote != null) {
                id = oiNote.id;
                mEditTituloView.setText(oiNote.titulo);
                mEditDescricaoView.setText(oiNote.descricao);
                mEditDataView.setText(oiNote.data);
                mEditTituloView.requestFocus();
                mEditDescricaoView.requestFocus();
                mEditDataView.requestFocus();
            }
        } // Otherwise, start with empty fields.
    }


    public void goToList(View view) {
        Intent replyIntent = new Intent();

        if (TextUtils.isEmpty(mEditTituloView.getText()) && TextUtils.isEmpty(mEditDescricaoView.getText()) && TextUtils.isEmpty(mEditDataView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String titulo = mEditTituloView.getText().toString();
            String descricao = mEditDescricaoView.getText().toString();
            String data = mEditDataView.getText().toString();
            EditNoteDto novaNota = new EditNoteDto(id, titulo, descricao, data);
            replyIntent.putExtra(EXTRA_REPLY, novaNota);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
