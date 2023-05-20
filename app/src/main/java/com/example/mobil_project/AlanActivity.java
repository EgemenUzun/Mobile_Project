package com.example.mobil_project;

import static com.example.mobil_project.MainActivity.connectionString;
import static com.example.mobil_project.MainActivity.password;
import static com.example.mobil_project.MainActivity.user;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Data.alan;
import Data.card;
import DataBase.AlanRepository;

public class AlanActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap googleMap;
    TextView title;
    ProgressBar manP,foodP,clothesP ,loadingProgress;
    int il,ilce,alanid;
    double longtitude,latitude;
    String baslik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alan);
        loadingProgress = findViewById(R.id.loading_progress);
        Intent intent = getIntent();
        if (intent != null) {
            il = intent.getIntExtra("ilId", -1); // -1, değer bulunamazsa varsayılan değerdir.
            ilce = intent.getIntExtra("ilceId", -1);
            alanid = intent.getIntExtra("alanid",-1);
            baslik = intent.getStringExtra("title");
        }
        title = findViewById(R.id.title_txt);
        title.setText(baslik);
        manP = findViewById(R.id.manpower);
        foodP = findViewById(R.id.food);
        clothesP = findViewById(R.id.clothes);
        mapView = findViewById(R.id.mapView);
        AlanTask task = new AlanTask();
        task.execute();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        LatLng location = new LatLng(latitude, longtitude);
        googleMap.addMarker(new MarkerOptions().position(location).title("Marker Başlığı"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
    }
    public void GoBack(View view)
    {
        Intent intent = new Intent(AlanActivity.this, CardActivity.class);
        intent.putExtra("ilId", il);
        intent.putExtra("ilceId", ilce);
        startActivity(intent);
        finish();
    }
    class AlanTask extends AsyncTask<Void, Void, alan> {
        @Override
        protected alan doInBackground(Void... voids) {
            AlanRepository _alanRepository = new AlanRepository(connectionString,user,password);
            return _alanRepository.getAlanById(alanid);
        }

        @Override
        protected void onPreExecute() {
            loadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(alan alan) {
            if (alan != null) {
                loadingProgress.setVisibility(View.GONE);
                manP.setProgress(alan.insangucu);
                foodP.setProgress(alan.yiyecek);
                clothesP.setProgress(alan.giysi);
                longtitude = alan.longtitude;
                latitude = alan.latitude;
                if (googleMap != null) {
                    LatLng location = new LatLng(latitude, longtitude);
                    googleMap.addMarker(new MarkerOptions().position(location).title("Marker Başlığı"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                }

            }
            else {
                Toast.makeText(AlanActivity.this, "Alan bilgileri alınamadı.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}