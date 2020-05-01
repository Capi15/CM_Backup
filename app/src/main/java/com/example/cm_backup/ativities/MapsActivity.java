package com.example.cm_backup.ativities;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cm_backup.R;
import com.example.cm_backup.retrofit.GetDataService;
import com.example.cm_backup.retrofit.Problema;
import com.example.cm_backup.retrofit.RetrofitClientInstance;
import com.example.cm_backup.retrofit.User;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import static com.example.cm_backup.ativities.LoginActivity.MyPREFERENCES;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean isClick = false;
    public static final String EXTRA_LAT = "latitude";
    public static final String EXTRA_LONG = "longitude";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        SharedPreferences getPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String token = getPreferences.getString("TOKEN", "");
        if (token != null) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Problema>> call = service.getAllProblemas();
        }

    }

    public void guardaLocalizacao(View view) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            Intent intent = new Intent(MapsActivity.this, GuardaLocActivity.class);
                            intent.putExtra(EXTRA_LAT, latitude);
                            intent.putExtra(EXTRA_LONG, longitude);
                            startActivity(intent);
                        }
                    }
                }, Looper.getMainLooper());
        //intent.putExtra(EXTRA_LAT, longitude);
        //intent.putExtra(EXTRA_LONG, latitude);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.isClick = true;
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng forjaes = new LatLng(41.6045637, -8.7515034);
        mMap.addMarker(new MarkerOptions().position(forjaes).title("Forjães"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(forjaes, 10));
        setMapLongClick(mMap);
    }


    private void setMapLongClick(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);
                map.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.marco))
                        .snippet(snippet));
                Intent intent = new Intent(MapsActivity.this, GuardaLocActivity.class);
                intent.putExtra(EXTRA_LAT, latLng.latitude);
                intent.putExtra(EXTRA_LONG, latLng.longitude);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toobar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goLogout(View view) {
        SharedPreferences getPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String token = getPreferences.getString("TOKEN", "");
        if (token != null) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<User> call = service.logout(token);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    //Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                    if(response != null){
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Não foi possivel fazer o logout", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Dados não reconhecidos", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
