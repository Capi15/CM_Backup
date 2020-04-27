package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cm_backup.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goMyNotes(View view) {
        Intent intent = new Intent(LoginActivity.this, NotasStartActivity.class);
        startActivity(intent);
    }


    public void goToMap(View view) {
        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}
