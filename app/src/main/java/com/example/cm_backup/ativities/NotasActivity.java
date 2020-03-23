package com.example.cm_backup.ativities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cm_backup.R;

public class NotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);


    }

    public void goToList (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
