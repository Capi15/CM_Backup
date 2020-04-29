package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_nome, et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.login);
        et_nome = findViewById(R.id.nome);
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);


    }

    public void login(View view) {
        //Cria uma instancia da interface do retrofit
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        User newUser = new User(et_nome.getText().toString(), et_username.getText().toString(), et_password.getText().toString());
        Call<User> call = service.login(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                if(response.body() != null){
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Dados não reconhecidos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*public void login(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://cm-notas-app.herokuapp.com/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Dados incorretos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", et_username.getText().toString());
                params.put("password", et_password.getText().toString());
                return params;

            }
        };
        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_LONG).show();
    }*/

    public void goMyNotes(View view) {
        Intent intent = new Intent(LoginActivity.this, NotasStartActivity.class);
        startActivity(intent);
    }

    public void goToRegistar(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
