package com.smartwater.mouvement;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private int countTocuh;
    private String modele = "";
    private String essai = "";
    private ArrayList correspondaceAlphaMorse = new ArrayList();

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList motAlpha = new ArrayList();
        motAlpha.add("BONJOUR");
        motAlpha.add("INTERNET");
        motAlpha.add("ANDROID");
        ArrayList motMorse = new ArrayList();
        motMorse.add("_...____..______.._._.");
        motMorse.add(".._._.._._.._");
        motMorse.add(".__._..._.___.._..");
        correspondaceAlphaMorse.add(motAlpha);
        correspondaceAlphaMorse.add(motMorse);

        // Génération de la chaîne de caractères aléatoire
        TextView Label = findViewById(R.id.Label);

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

        Label.append(modele);

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
            mText.append((CharSequence) ".");
            essai+='.';
            countTocuh++;
            mText2.setText(Integer.toString(countTocuh));
            fin();
        }
        return true;
    }

    public void fin() {
        if (countTocuh==10) {
            TextView mText3 = findViewById(R.id.textView3);
            if (essai.compareTo(modele)==0) {
                mText3.setText("GAGNÉ");
            }
            else {
                mText3.setText("PERDU");
            }
        }
    }
}