package promob.morpion;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Morpion extends AppCompatActivity implements
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private int tour = 0;
    private ArrayList<Character> symboles = new ArrayList();
    private boolean isEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion);

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);

        // Ajout des deux possibilités de caractères à la liste
        symboles.add('×');
        symboles.add('○');
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
        // On récupère les coordonnées du SingleTap
        float X = event.getX();
        float Y = event.getY();

        // Recherche de tous les TextView
        TextView CoinGS = findViewById(R.id.CoinGS);
        TextView Haut = findViewById(R.id.Haut);
        TextView CoinDS = findViewById(R.id.CoinDS);
        TextView Gauche = findViewById(R.id.Gauche);
        TextView Centre = findViewById(R.id.Centre);
        TextView Droite = findViewById(R.id.Droite);
        TextView CoinGI = findViewById(R.id.CoinGI);
        TextView Bas = findViewById(R.id.Bas);
        TextView CoinDI = findViewById(R.id.CoinDI);


        if (isEnded==false) {
            int numJoueur = tour%2;
            Character ch = symboles.get(numJoueur);

            // Le code ci-dessous n'est pas du tout mais alors PAS DU TOUT optimisé ! Mais ça marche :3
            if (Y-160<=((getScreenY()-160)/3)) {
                if (X<=(getScreenX()/3)) {
                    if (CoinGS.getText()=="") {
                        CoinGS.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }

                }
                else if ((X<=(2*getScreenX()/3)) && (X>(getScreenX()/3))) {
                    if (Haut.getText()=="") {
                        Haut.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
                else {
                    if (CoinDS.getText()=="") {
                        CoinDS.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
            }
            else if ((Y-160<=((2*(getScreenY()-160)/3))) && (Y-160>((getScreenY()-160)/3))) {
                if (X<=(getScreenX()/3)) {
                    if (Gauche.getText()=="") {
                        Gauche.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
                else if ((X<=(2*getScreenX()/3)) && (X>(getScreenX()/3))) {
                    if (Centre.getText()=="") {
                        Centre.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
                else {
                    if (Droite.getText()=="") {
                        Droite.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
            }
            else {
                if (X<=(getScreenX()/3)) {
                    if (CoinGI.getText()=="") {
                        CoinGI.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
                else if ((X<=(2*getScreenX()/3)) && (X>(getScreenX()/3))) {
                    if (Bas.getText()=="") {
                        Bas.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
                else {
                    if (CoinDI.getText()=="") {
                        CoinDI.setText(String.valueOf(ch));
                        isWon();
                        tour++;
                    }
                }
            }
            IAcall();
        }
        return true;
    }

    public int getScreenX() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int getScreenY() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public boolean isWon() {
        TextView CoinGS = findViewById(R.id.CoinGS);
        TextView Haut = findViewById(R.id.Haut);
        TextView CoinDS = findViewById(R.id.CoinDS);
        TextView Gauche = findViewById(R.id.Gauche);
        TextView Centre = findViewById(R.id.Centre);
        TextView Droite = findViewById(R.id.Droite);
        TextView CoinGI = findViewById(R.id.CoinGI);
        TextView Bas = findViewById(R.id.Bas);
        TextView CoinDI = findViewById(R.id.CoinDI);

        if ((((CoinGS.getText().toString().compareTo(Haut.getText().toString())==0) && (Haut.getText().toString().compareTo(CoinDS.getText().toString())==0) && CoinGS.getText()!="") ||
                ((Gauche.getText().toString().compareTo(Centre.getText().toString())==0) && (Centre.getText().toString().compareTo(Droite.getText().toString())==0) &&  Gauche.getText()!="") ||
                ((CoinGI.getText().toString().compareTo(Bas.getText().toString())==0) && (Bas.getText().toString().compareTo(CoinDI.getText().toString())==0) && CoinGI.getText()!="") ||
                ((CoinGS.getText().toString().compareTo(Gauche.getText().toString())==0) && (Gauche.getText().toString().compareTo(CoinGI.getText().toString())==0) && CoinGS.getText()!="") ||
                ((Haut.getText().toString().compareTo(Centre.getText().toString())==0) && (Centre.getText().toString().compareTo(Bas.getText().toString())==0) && Haut.getText()!="") ||
                ((CoinDS.getText().toString().compareTo(Droite.getText().toString())==0) && (Droite.getText().toString().compareTo(CoinDI.getText().toString())==0) && CoinDS.getText()!="") ||
                ((CoinGS.getText().toString().compareTo(Centre.getText().toString())==0) && (Centre.getText().toString().compareTo(CoinDI.getText().toString())==0) && CoinGS.getText()!="") ||
                ((CoinDS.getText().toString().compareTo(Centre.getText().toString())==0) && (Centre.getText().toString().compareTo(CoinGI.getText().toString())==0)) && CoinDS.getText()!="")) {

            Context context = getApplicationContext();
            int numJoueur = tour%2;
            Character ch = symboles.get(numJoueur);

            CharSequence text = ch+" a gagné !";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            isEnded=true;

            return true;
        }
        else {
            return false;
        }
    }

    /* Fonction d'IA rudimentaire pour jeu en solo */
    public void IAcall() {
        TextView CoinGS = findViewById(R.id.CoinGS);
        TextView Haut = findViewById(R.id.Haut);
        TextView CoinDS = findViewById(R.id.CoinDS);
        TextView Gauche = findViewById(R.id.Gauche);
        TextView Centre = findViewById(R.id.Centre);
        TextView Droite = findViewById(R.id.Droite);
        TextView CoinGI = findViewById(R.id.CoinGI);
        TextView Bas = findViewById(R.id.Bas);
        TextView CoinDI = findViewById(R.id.CoinDI);

        ArrayList<TextView> Liste = new ArrayList<TextView>();
        Liste.add(CoinGS);
        Liste.add(Haut);
        Liste.add(CoinDS);
        Liste.add(Gauche);
        Liste.add(Centre);
        Liste.add(Droite);
        Liste.add(CoinGI);
        Liste.add(Bas);
        Liste.add(CoinDI);

        Boolean CoupJoué = false;
        int numJoueur = tour%2;
        Character ch = symboles.get(numJoueur);

        while (CoupJoué == false) {
            Random random = new Random();
            int index = random.nextInt(Liste.size());
            TextView Element = Liste.get(index);
            if (Element.getText()=="") {
                Element.setText(String.valueOf(ch));
                CoupJoué=true;
            }
        }
        tour++;
    }
}
