package app_promod.application_promod;


import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {

    private Handler threadHandler = new Handler();
    final MediaPlayer mediaPlayer = new MediaPlayer ();
    private static int SPLASH_TIME_OUT =4000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AssetManager assetManager = getAssets();
        AssetFileDescriptor descriptor=null;

        try {
            // Chargement du fichier musique.mp3 qui se trouve sous assets de notre projet
            descriptor = assetManager.openFd("tigre2.mp3");
            Log.d("testMediaPlayer", "setDataSource du mediaPlayer" + descriptor);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor());
                // Listener afin de lancer la musique lors le mediaPlayer est prêt
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        Log.d("testMediaPlayer", "etat:prepared");
                        Log.d("testMediaPlayer", "Zou, jouons la musique !");
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {

                    @Override
                    // Libérons les ressources lorsque la musique est terminée
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        // TODO Auto-generated method stub
                        Log.d("testMediaPlayer", "etat:completed");
                        Log.d("testMediaPlayer", "release en cours...");
                        mediaPlayer.release();
                    }
                });
                Log.d("testMediaPlayer", "préparation du mediaPlayer");
                mediaPlayer.prepare(); // Lancement de la préparation du mediaPlayer...

        }catch(IOException e1){ // TODO Auto-generated catch block
            e1.printStackTrace();}


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();

                try{
                    mediaPlayer.stop();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {

                        @Override
                        // Libérons les ressources lorsque la musique est terminée
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // TODO Auto-generated method stub
                            Log.d("testMediaPlayer", "etat:completed");
                            Log.d("testMediaPlayer", "release en cours...");
                            mediaPlayer.release();
                        }
                    });
                }catch(Error d){System.out.println(d);}
            }
        }, SPLASH_TIME_OUT);
    }
}






