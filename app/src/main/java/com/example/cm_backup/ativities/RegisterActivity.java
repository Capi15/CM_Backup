package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.cm_backup.R;
import com.example.cm_backup.retrofit.GetDataService;
import com.example.cm_backup.retrofit.RetrofitClientInstance;
import com.example.cm_backup.retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    EditText et_nome, et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.registar_registar);
        et_nome = findViewById(R.id.nome_registar);
        et_username = findViewById(R.id.email_registar);
        et_password = findViewById(R.id.password_registar);
    }

    public void registar(View view) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        User newUser = new User(et_nome.getText().toString(), et_username.getText().toString(), et_password.getText().toString());
        Call<User> call = service.register(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                if(response.body() != null){
                    Toast.makeText(getApplicationContext(), "Registo efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Dados não reconhecidos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
