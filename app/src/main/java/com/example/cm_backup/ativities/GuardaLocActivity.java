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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm_backup.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GuardaLocActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int REQUEST_IMAGE_ACCESS = 1001;
    private TextView textLatLong;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarda_loc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textLatLong = findViewById(R.id.textLatLong);
        imageView = findViewById(R.id.image_guarda);
        button = findViewById(R.id.button_guarda);

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
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(GuardaLocActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(GuardaLocActivity.this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            textLatLong.setText(
                                    String.format(
                                            "Latitude: %s\nLongitude: %s",
                                            latitude,
                                            longitude
                                    )
                            );
                        }
                    }
                }, Looper.getMainLooper());
    }

}
