package com.example.mobil_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.mobil_project.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import Data.ilceler;
import Data.iller;
import DataBase.SehirRepository;
import DataBase.İlceRepository;

public class MainActivity extends AppCompatActivity {

    static final String connectionString="jdbc:postgresql://10.0.2.2:5432/Mobile_Project";
    static final String user ="postgres";
    static final String password="1234";
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
        class IlceTask extends AsyncTask<Integer, Void, List<ilceler>> {
            String error = "";

            @Override
            protected List<ilceler> doInBackground(Integer... params) {
                int selectedCityId = params[0];
                try {
                    İlceRepository ilce = new İlceRepository(connectionString, user, password);
                    return ilce.getIlceList(selectedCityId);
                } catch (Exception e) {
                    e.printStackTrace();
                    error = e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<ilceler> ilceList) {
                if (ilceList != null) {
                    ArrayAdapter<ilceler> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, ilceList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerIlce.setAdapter(adapter);
                } else {
                    Log.e("IlceTask", error);
                }
            }
        }

        @Override
        protected void onPostExecute(List<iller> cityList) {
            if (cityList != null) {
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
    }
}