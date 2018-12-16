package app_promod.application_promod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Choose_Solo extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "entrainement";
    public final static String EXTRA_MESS = "competition";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entrainement_competition);

        final Button button_compettion = findViewById(R.id.button_comp);
        final Button button_entraitement = findViewById(R.id.button_entrain);

        button_entraitement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_choix_solo);

                final Button button_question = findViewById(R.id.button_quest);
                final Button button_laby = findViewById(R.id.button_laby);
                final Button button_morpion = findViewById(R.id.button_lancer);
                final Button button_morse = findViewById(R.id.button_morse);

                button_question.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Choose_Solo.this, QRScreen.class);
                        intent.putExtra("key",EXTRA_MESSAGE);
                        startActivity(intent);
                    }
                });

                button_laby.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Choose_Solo.this, Move_game.class);
                        intent.putExtra("key",EXTRA_MESSAGE);
                        startActivity(intent);
                    }
                });

                button_morpion.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Choose_Solo.this, Morpion.class);
                        intent.putExtra("key",EXTRA_MESSAGE);
                        startActivity(intent);
                    }
                });

                button_morse.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Choose_Solo.this, Morse.class);
                        intent.putExtra("key",EXTRA_MESSAGE);
                        startActivity(intent);
                    }
                });
            }
        });


        button_compettion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose_Solo.this, QRScreen.class);
                intent.putExtra("key",EXTRA_MESS);
                startActivity(intent);
            }
        });

    }
}
