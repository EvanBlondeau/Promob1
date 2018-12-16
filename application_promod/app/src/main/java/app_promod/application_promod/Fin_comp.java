package app_promod.application_promod;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Fin_comp extends AppCompatActivity {


    private MediaPlayer mPlayer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_3_game);

        Bundle score_1 = getIntent().getExtras();
        String TaString = score_1.getString("score_1");

        Bundle score_2 = getIntent().getExtras();
        String TaString_2 = score_2.getString("score_2");

        Bundle score_3 = getIntent().getExtras();
        String TaString_3 = score_3.getString("score_3");



        System.out.println(TaString);
        System.out.println(TaString_2);
        System.out.println(TaString_3);

        TextView sc1 = findViewById(R.id.textView_score_1er_game);
        TextView sc2 = findViewById(R.id.textView_score_2emegame);
        TextView sc3 = findViewById(R.id.textView_score_3eme_game);




        sc1.setText(TaString);
        sc2.setText(TaString_2);
        sc3.setText(TaString_3);

        playSound(R.raw.appl5);

        Button but_menu = findViewById(R.id.but_suivant);

        but_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Fin_comp.this, MainActivity.class);
                startActivity(intent);
            }
        });
        }

    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }
}

