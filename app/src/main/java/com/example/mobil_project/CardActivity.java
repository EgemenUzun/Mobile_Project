package com.example.mobil_project;

import static com.example.mobil_project.MainActivity.connectionString;
import static com.example.mobil_project.MainActivity.password;
import static com.example.mobil_project.MainActivity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mobil_project.databinding.ActivityMainBinding;

import java.util.List;

import Data.card;
import DataBase.CardRepository;

public class CardActivity extends AppCompatActivity {
    int il, ilce;
    ActivityMainBinding binding;
    ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        loadingProgress = findViewById(R.id.loading_progress);
        Intent intent = getIntent();
        if (intent != null) {
            il = intent.getIntExtra("ilId", -1); // -1, değer bulunamazsa varsayılan değerdir.
            ilce = intent.getIntExtra("ilceId", -1);

        }
        LoadCardsTask loadCardsTask = new LoadCardsTask();
        loadCardsTask.execute();

    }

    class LoadCardsTask extends AsyncTask<Void, Void, List<card>> {

        @Override
        protected List<card> doInBackground(Void... voids) {
            CardRepository cardRepository = new CardRepository(connectionString, user, password);
            return cardRepository.getCards(il, ilce);
        }

        @Override
        protected void onPreExecute() {
            loadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<card> cards) {
            if (cards != null && !cards.isEmpty()) {
                loadingProgress.setVisibility(View.GONE);
                LinearLayout buttonContainer = findViewById(R.id.button_container);

                for (final card card : cards) {
                    Button button = new Button(CardActivity.this);
                    button.setText(card.title);
                    button.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    button.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CardActivity.this, AlanActivity.class);
                            intent.putExtra("alanid", card.alanid);
                            intent.putExtra("ilId", il);
                            intent.putExtra("ilceId", ilce);
                            intent.putExtra("title", card.title);
                            startActivity(intent);
                            finish();
                        }
                    });

                    buttonContainer.addView(button);
                }
            }
            else
            {
                Toast.makeText(CardActivity.this, "Toplanma alanı bulunamadı", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
    public void GoBack(View view)
    {
        Intent intent = new Intent(CardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}