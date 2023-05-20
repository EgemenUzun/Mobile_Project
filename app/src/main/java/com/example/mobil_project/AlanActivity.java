package com.example.mobil_project;

import static com.example.mobil_project.MainActivity.connectionString;
import static com.example.mobil_project.MainActivity.password;
import static com.example.mobil_project.MainActivity.user;

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

public class AlanActivity extends AppCompatActivity {

    TextView title;
    ProgressBar manP,foodP,clothesP;
    int il,ilce,alanid;
    String baslik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alan);
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
        AlanTask task = new AlanTask();
        task.execute();
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
        protected void onPostExecute(alan alan) {
            if (alan != null) {
                manP.setProgress(alan.insangucu);
                foodP.setProgress(alan.yiyecek);
                clothesP.setProgress(alan.giysi);
            }
            else {
                Toast.makeText(AlanActivity.this, "Alan bilgileri alınamadı.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}