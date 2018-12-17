package app_promod.application_promod;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button_3 = findViewById(R.id.button_3);
        final Button button_2 = findViewById(R.id.button_2);
        final Button button_1 = findViewById(R.id.button_1);

        button_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Choose_Solo.class);
                    startActivity(intent);
            }

        });

        button_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Multiplayeur.class);
                startActivity(intent);
            }

        });

        button_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Credits.class);
                startActivity(intent);

            }

        });
    }
}
