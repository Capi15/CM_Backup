package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cm_backup.R;

import static com.example.cm_backup.ativities.MainActivity.NOTAS_ACTIVITY_REQUEST_CODE;

public class NotasActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.cm_backup.ativities.REPLY";

    private EditText mEditTituloView;
    private EditText mEditDescricaoView;
    private EditText mEditDataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        mEditTituloView = findViewById(R.id.titulo_view_text);
        mEditDescricaoView = findViewById(R.id.descricao_view_text);
        mEditDataView = findViewById(R.id.data_view_text);

    }


    public void goToList(View view) {
        Intent replyIntent = new Intent();

        if (TextUtils.isEmpty(mEditTituloView.getText()) && TextUtils.isEmpty(mEditDescricaoView.getText()) && TextUtils.isEmpty(mEditDataView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String titulo = mEditTituloView.getText().toString();
            String descricao = mEditDescricaoView.getText().toString();
            String data = mEditDataView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, titulo);
            replyIntent.putExtra(EXTRA_REPLY, descricao);
            replyIntent.putExtra(EXTRA_REPLY, data);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
