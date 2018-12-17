package app_promod.application_promod;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class Morse extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private int countTocuh;
    private String modele = "";
    private String essai = "";
    private ArrayList correspondaceAlphaMorse = new ArrayList();

    private MediaPlayer mPlayer = null;

    public double timer_start,timer_end;

    String param1, score_1,score_2;
    double time;
    double score_time;

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);
        timer_start = new Date().getTime();
        param1 =  this.getIntent().getStringExtra("key");
        score_1 = this.getIntent().getStringExtra("score_1");
        score_2 = this.getIntent().getStringExtra("score_2");

        ArrayList motAlpha = new ArrayList();
       /* motAlpha.add("BONJOUR");
        motAlpha.add("INTERNET");
        motAlpha.add("ANDROID");*/
        motAlpha.add("SOS");
        motAlpha.add("OK");
        motAlpha.add("NON");
        motAlpha.add("OUI");
        motAlpha.add("LIT");
        ArrayList motMorse = new ArrayList();
      /*  motMorse.add("_...____..______.._._.");
        motMorse.add(".._._.._._.._");
        motMorse.add(".__._..._.___.._..");*/
        motMorse.add("...___...");
        motMorse.add("____._");
        motMorse.add("_.____.");
        motMorse.add("___.._..");
        motMorse.add("._.. .._");
        correspondaceAlphaMorse.add(motAlpha);
        correspondaceAlphaMorse.add(motMorse);

        // Génération de la chaîne de caractères aléatoire
        TextView Label = findViewById(R.id.Label);

        int mot_courant = (int) ((Math.random() * 5));
        System.out.println(mot_courant);

        modele = (String)motMorse.get(mot_courant);
        String mot = (String)motAlpha.get(mot_courant);

        /* MODE FACILE
        for(int i=0;i<10;i++) {
            Random r = new Random();
            int indice = r.nextInt(2);
            if (indice==0) {
                modele+='.';
            }
            else {
                modele+='_';
            }
        }
        */

        Label.append(mot);

        // Préciser ce qu'on a fait, ce qu'on voulait faire mais qu'on a pas réussi, une démo (préparée), ce qui nous a plu, déplu

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        System.out.println("# onDown:");
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        System.out.println("# onFling: ");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        System.out.println("# onLongPress: ");
        TextView mText = findViewById(R.id.textView);
        TextView mText2 = findViewById(R.id.textView2);
        if (countTocuh<10) {
            playSound(R.raw.morselong);
            mText.append((CharSequence) "_");
            essai+='_';
            countTocuh++;
            mText2.setText(Integer.toString(countTocuh));
            fin();
        }
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        System.out.println("# onScroll: ");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        System.out.println("# onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        System.out.println("# onSingleTapUp: " );
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        System.out.println("# onDoubleTap: ");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        System.out.println("# onDoubleTapEvent: ");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        System.out.println("# onSingleTapConfirmed: ");
        TextView mText = findViewById(R.id.textView);
        TextView mText2 = findViewById(R.id.textView2);
        // TextView mText3 = findViewById(R.id.textView3);
        //mText2.setText((CharSequence) Float.toString(event.getX()));
        //mText3.setText((CharSequence) Float.toString(event.getY()));
        if (countTocuh<10) {
            playSound(R.raw.morsecourt);
            mText.append((CharSequence) ".");
            essai+='.';
            countTocuh++;
            mText2.setText(Integer.toString(countTocuh));
            fin();
        }
        return true;
    }

    public void fin() {


        if (countTocuh==10 || (essai.compareTo(modele)==0)) {

            timer_end = new Date().getTime();
            time = timer_end - timer_start;
            System.out.println(time);
            time = time / 1000;

            TextView mText3 = findViewById(R.id.textView3);

            if (essai.compareTo(modele)==0) {
                mText3.setText("GAGNÉ");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       changement_fin();
                    }
                }, 5000);
                score_time=time;
            }
            else {
                mText3.setText("PERDU => +30secondes");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changement_fin();
                    }
                }, 5000);
                score_time=time+(double)30;
            }

        }

    }

    public void changement_fin()
    {
        final String score_str = String.valueOf(score_time) + " secondes";

        setContentView(R.layout.activity_fin_qr);

        TextView score = findViewById(R.id.textView_score);
        TextView com_score = findViewById(R.id.textView_com_score);
        if(essai.compareTo(modele)==1)
        {
            com_score.setText("tu ne sais pas recopier?");
            playSound(R.raw.lose);
        }
        else if (score_time > 30) {
            com_score.setText("tu es pas très doué");
            playSound(R.raw.lose);
        } else if(score_time<30 && score_time >15)
        {
            com_score.setText("tu te débrouille bien ");
            playSound(R.raw.appl5);
        }else if(score_time<15)
        {
            com_score.setText("tu est un monstre");
            playSound(R.raw.appla10);
        }

        score.setText(score_str);

        if (param1.compareTo("entrainement") == 0) {

            Button button_menu = findViewById(R.id.but_suivant);
            button_menu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Morse.this, MainActivity.class);
                    startActivity(intent);
                }

            });

        }else if(param1.compareTo("competition") == 0)
        {
            Button button_suivant = findViewById(R.id.but_suivant);
            button_suivant.setText("Résultat des scores");

            button_suivant.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Morse.this, Fin_comp.class);
                    intent.putExtra("score_1",score_1);
                    intent.putExtra("score_2",score_2);
                    intent.putExtra("score_3",score_str);
                    startActivity(intent);
                }

            });
        }
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
