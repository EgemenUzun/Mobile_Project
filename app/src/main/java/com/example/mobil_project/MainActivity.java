package com.example.mobil_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mobil_project.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import Data.ilceler;
import Data.iller;
import DataBase.SehirRepository;
import DataBase.İlceRepository;

public class MainActivity extends AppCompatActivity {

    public static final String connectionString="jdbc:postgresql://containers-us-west-143.railway.app:7315/railway";
    public static final String user ="postgres";
    public static final String password="cxwwuFBGZ25P4LDb6uGO";
    Integer il=0,ilce=0;
    ActivityMainBinding binding;

    List<ilceler> selectedCityIlceler = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        new Task().execute();
    }

    public void Search(View view) {
        if(il!=0 && ilce!=0) {
            Intent intent = new Intent(this, CardActivity.class);
            intent.putExtra("ilId", il);
            intent.putExtra("ilceId", ilce);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, "İl Veya İlçe Seçilmedi", Toast.LENGTH_SHORT).show();
    }

    class Task extends AsyncTask<Void,Void,List<iller>> {
        String error="";
        @Override
        protected List<iller> doInBackground(Void... voids) {
            try {
                SehirRepository sehirRepository = new SehirRepository(connectionString,user,password);
                return sehirRepository.getAllSehir();
            } catch (Exception e) {
                e.printStackTrace();
                error = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            binding.loadingProgress.setVisibility(View.VISIBLE);
            binding.spinnerIl.setEnabled(false);
            binding.spinnerIlce.setEnabled(false);
        }

        @Override
        protected void onPostExecute(List<iller> cityList) {
            if (cityList != null) {
                binding.loadingProgress.setVisibility(View.GONE);
                binding.spinnerIl.setEnabled(true);
                binding.spinnerIlce.setEnabled(true);
                ArrayAdapter<iller> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, cityList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerIl.setAdapter(adapter);
                binding.spinnerIl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        iller selectedCity = (iller) parent.getItemAtPosition(position);
                        new IlceTask().execute(selectedCity.id);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } else {
                Log.e("Task", error);
            }
        }
        class IlceTask extends AsyncTask<Integer, Void, List<ilceler>> {
            String error = "";

            @Override
            protected List<ilceler> doInBackground(Integer... params) {
                il = params[0];
                try {
                    İlceRepository ilce = new İlceRepository(connectionString, user, password);
                    return ilce.getIlceList(il);
                } catch (Exception e) {
                    e.printStackTrace();
                    error = e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                binding.loadingProgress.setVisibility(View.VISIBLE);
                binding.spinnerIlce.setEnabled(false);
            }

            @Override
            protected void onPostExecute(List<ilceler> ilceList) {
                if (ilceList != null) {
                    binding.loadingProgress.setVisibility(View.GONE);
                    binding.spinnerIlce.setEnabled(true);
                    ArrayAdapter<ilceler> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, ilceList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerIlce.setAdapter(adapter);
                    binding.spinnerIlce.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ilceler selectedCounty = (ilceler) adapterView.getItemAtPosition(i);
                            ilce = selectedCounty.id;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    Log.e("IlceTask", error);
                }
            }
        }


    }
}