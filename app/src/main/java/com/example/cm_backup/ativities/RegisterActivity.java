package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import com.example.cm_backup.R;


public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password;
    private Button btn_regist;
    private static String URL_REGIST = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.nome_registar);
        email = findViewById(R.id.email_registar);
        password = findViewById(R.id.email_registar);
        c_password = findViewById(R.id.comfirma_password_registar);
    }
}
