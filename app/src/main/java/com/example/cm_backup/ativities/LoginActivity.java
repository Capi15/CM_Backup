package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cm_backup.R;
import com.example.cm_backup.retrofit.GetDataService;

import com.example.cm_backup.retrofit.RetrofitClientInstance;
import com.example.cm_backup.retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_nome, et_username, et_password;
    ProgressBar progressBar;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String ID = "userId";
    public static final String TOKEN = "api_token";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.login);
        et_nome = findViewById(R.id.nome);
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void login(View view) {
        //Cria uma instancia da interface do retrofit
        btn_login.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        User newUser = new User(et_nome.getText().toString(), et_username.getText().toString(), et_password.getText().toString());
        Call<User> call = service.login(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null){
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("ID", response.body().getId().toString());
                    editor.putString("TOKEN", response.body().getApi_token());
                    editor.commit();
                    //Toast.makeText(LoginActivity.this, valueOf(sharedpreferences.getString("ID", "")),Toast.LENGTH_LONG).show();
                    Toast.makeText(LoginActivity.this, valueOf(sharedpreferences.getString("TOKEN", "")),Toast.LENGTH_LONG).show();
                    Toast.makeText(LoginActivity.this, "Bem Vindo" ,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Os dados introduzidos estão incorretos", Toast.LENGTH_SHORT).show();
                    btn_login.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Dados não reconhecidos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goMyNotes(View view) {
        Intent intent = new Intent(LoginActivity.this, NotasStartActivity.class);
        startActivity(intent);
    }

    public void goToRegistar(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
