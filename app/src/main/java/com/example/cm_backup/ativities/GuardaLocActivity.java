package com.example.cm_backup.ativities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm_backup.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import static com.example.cm_backup.ativities.MapsActivity.EXTRA_LAT;
import static com.example.cm_backup.ativities.MapsActivity.EXTRA_LONG;

public class GuardaLocActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int REQUEST_IMAGE_ACCESS = 1001;
    private TextView textLatLong;
    private ImageView imageView;
    private Button button;
    private EditText et_titulo, et_descricao;
    private Boolean isClick;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarda_loc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra(EXTRA_LAT, 0.0);
        longitude = intent.getDoubleExtra(EXTRA_LONG, 0.0);

        textLatLong = findViewById(R.id.textLatLong);
        imageView = findViewById(R.id.image_guarda);
        button = findViewById(R.id.button_guarda);
        et_titulo = findViewById(R.id.tit_guarda_text);
        et_descricao = findViewById(R.id.desc_guarda_text);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GuardaLocActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            getCurrentLocation();
            return;
        }
    }

    //trata dos resultados das permissões necessárias
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length > 0) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case REQUEST_IMAGE_ACCESS: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //a permissão foi concedida
                    pickImageFromGallery();
                }else{
                    //quando a permissão foi negada
                    Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }

    }

    public void pickImages(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //permissão ainda não aceite, é pedida
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //mostra o popup da permissão
                requestPermissions(permissions, REQUEST_IMAGE_ACCESS);
            } else {
                //permissão aceite
                pickImageFromGallery();
            }
        } else {
            //caso o android seja inferior ao Marshmallow
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //define a imagem para a imageView
            imageView.setImageURI(data.getData());
        }
    }

    public void getCurrentLocation() {
            textLatLong.setText(
                    String.format(
                            "Latitude: %s\nLongitude: %s",
                            latitude,
                            longitude
                    )
            );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.adiciona_problema) {
            Intent intent = new Intent(GuardaLocActivity.this, MapsActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Problema criado com sucesso", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
