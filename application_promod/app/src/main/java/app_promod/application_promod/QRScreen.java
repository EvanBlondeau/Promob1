package app_promod.application_promod;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class QRScreen extends AppCompatActivity {

    //def de la progress bar
    final BigCalcul calcul = new BigCalcul();
    private ProgressBar mProgressBar;
    int progress=0;
    int reponse_pose=0;
    int nb_question_a_pose = 10;
    int nb_question_en_tout =18;

    MediaPlayer mPlayer=null;

    //def des variable neccessaire au question
    protected Boolean response_valide=false;
    private String bonne_reponse;
    private int nomb_bonne_rep = 0;
    ArrayList<Integer> list_num_quest = new ArrayList<Integer>();

    Button but_pro;
    public final static String EXTRA_MESS = "competition";


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String param1 =  this.getIntent().getStringExtra("key");

        setContentView(R.layout.activiy_qr);

        //def boution en gmobal car utiliser dans diff fonction
        final TextView quest = findViewById(R.id.Textview_quest);
        final Button but_A = findViewById(R.id.but_rep_A);
        final Button but_B = findViewById(R.id.but_rep_B);
        final Button but_C = findViewById(R.id.but_rep_C);
        final Button but_D = findViewById(R.id.but_rep_D);
        but_pro = findViewById(R.id.button_qpro);
        but_pro.setClickable(false);

        but_A.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (response_valide) {
                    response_valide = false;
                    but_pro.setClickable(true);
                    String rep = String.valueOf(but_A.getText());
                    if(bonne_reponse.compareTo(rep)==0)
                    {
                        but_A.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        nomb_bonne_rep++;
                        playSound(R.raw.gameboy);
                    }
                    else {
                        but_A.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        playSound(R.raw.rire2);
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Too late ;)", Toast.LENGTH_SHORT).show();
            }
        });

        but_B.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (response_valide) {
                    response_valide = false;

                    but_pro.setClickable(true);
                    String rep = String.valueOf(but_B.getText());
                    if(bonne_reponse.compareTo(rep)==0)
                    {
                        but_B.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        nomb_bonne_rep++;
                        playSound(R.raw.gameboy);
                    }
                    else {
                        but_B.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        playSound(R.raw.rire2);
                    }


                } else
                    Toast.makeText(getApplicationContext(), "Too late ;)", Toast.LENGTH_SHORT).show();
            }
        });

        but_C.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (response_valide) {
                    response_valide = false;

                    but_pro.setClickable(true);
                    String rep = String.valueOf(but_C.getText());
                    if(bonne_reponse.compareTo(rep)==0)
                    {
                        but_C.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        nomb_bonne_rep++;
                        playSound(R.raw.gameboy);
                    }
                    else {
                        but_C.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        playSound(R.raw.rire2);
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Too late ;)", Toast.LENGTH_SHORT).show();
            }
        });

        but_D.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (response_valide) {
                    response_valide = false;

                    but_pro.setClickable(true);
                    String rep = String.valueOf(but_D.getText());
                    if(bonne_reponse.compareTo(rep)==0)
                    {
                        but_D.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        nomb_bonne_rep++;
                        playSound(R.raw.gameboy);
                    }
                    else {
                        but_D.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        playSound(R.raw.rire2);
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Too late ;)", Toast.LENGTH_SHORT).show();
            }
        });

        but_pro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!response_valide) {

                    new BigCalcul().execute();
                    if(reponse_pose<nb_question_a_pose) {
                        reponse_pose++;
                        but_pro.setText("prochaine question");
                        but_A.setText("");
                        but_B.setText("");
                        but_C.setText("");
                        but_D.setText("");
                        quest.setText("");
                        but_pro.setClickable(false);

                        but_A.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                        but_B.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                        but_C.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                        but_D.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));

                        progress=0;
                        mProgressBar = findViewById(R.id.barre_prog);
                        mProgressBar.setProgress(0);
                        mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.RED));

                        //*range + 1
                        int i;
                        do {
                            i = (int) ((Math.random() * nb_question_en_tout));

                        } while (!quest_diff(list_num_quest, i));
                        response_valide = true;


                        //extrait les données du json et les mettre aux bons endroits
                        try {
                            InputStream is = getAssets().open("questionnaires.json");
                            int size = is.available();
                            byte[] buffer = new byte[size];
                            is.read(buffer);
                            is.close();
                            String myJson = new String(buffer, "UTF-8");

                            list_num_quest.add(i);
                            System.out.println(i);

                            JSONObject json_complet = new JSONObject(myJson);
                            JSONArray array = new JSONArray(json_complet.getString("Questions"));
                            JSONObject obj = new JSONObject(array.getString(i));

                            quest.setText(obj.getString("question"));

                            but_A.setText(obj.getString("reponse_1"));
                            but_B.setText(obj.getString("reponse_2"));
                            but_C.setText(obj.getString("reponse_3"));
                            but_D.setText(obj.getString("reponse_4"));
                            bonne_reponse = obj.getString(("bonne_rep"));

                            is.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e);
                        }
                    }
                    else{
                        setContentView(R.layout.activity_fin_qr);
                        Button but_menu = findViewById(R.id.but_suivant);
                        TextView score = findViewById(R.id.textView_score);
                        TextView com_score = findViewById(R.id.textView_com_score);

                        score.setText(String.valueOf(nomb_bonne_rep) +" bonne réponse(s)");

                        if (nomb_bonne_rep <= 2) {
                            com_score.setText("c'est de la grosse merde !! ");
                            playSound(R.raw.lose);
                        } else if (nomb_bonne_rep > 2 && nomb_bonne_rep < 5) {
                            com_score.setText("apprend à ouvrir un livre putain !! ");
                            playSound(R.raw.lose);
                        } else if (nomb_bonne_rep == 5) {
                            com_score.setText("tu as sauvé ton honneur on va dire !");
                            playSound(R.raw.appl5);
                        } else if (nomb_bonne_rep > 5 && nomb_bonne_rep <= 7) {
                            com_score.setText("ça va tu commences a envoyer!");
                            playSound(R.raw.appl5);
                        } else if (nomb_bonne_rep > 7 && nomb_bonne_rep <= 9) {
                            com_score.setText("Tu es un génie!!");
                            playSound(R.raw.appla10);
                        } else if (nomb_bonne_rep == 10) {
                            com_score.setText("pffff tu n'es qu'un tricheur!!");
                            playSound(R.raw.appla10);
                        }


                        if(param1.compareTo("entrainement")==0) {


                            but_menu.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent(QRScreen.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        else{
                            but_menu.setText("Jeu suivant");


                            final String ss  = String.valueOf(nomb_bonne_rep)  +" bonne réponse(s)";
                            but_menu.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent(QRScreen.this, Move_game.class);
                                    intent.putExtra("score_1",ss);
                                    intent.putExtra("key",EXTRA_MESS);
                                    startActivity(intent);
                                }
                            });

                        }
                    }

                   // execution_question( but_A, but_B, but_C, but_D, but_pro, quest);
                }
            }
        });
    }



    private void execution_question(Button but_A,Button but_B,Button but_C,Button but_D,Button but_pro,EditText quest) {
        but_A.setText("");
        but_B.setText("");
        but_C.setText("");
        but_D.setText("");
        quest.setText("");
        mProgressBar = findViewById(R.id.barre_prog);
        mProgressBar.setProgress(0);
        //*range + 1
        int i;
        do {
            i = (int) ((Math.random() * 5));

        } while (!quest_diff(list_num_quest, i));
        response_valide = true;
        calcul.execute();

        //extrait les données du json et les mettre aux bons endroits
        try {
            InputStream is = getAssets().open("questionnaires.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String myJson = new String(buffer, "UTF-8");

            list_num_quest.add(i);
            System.out.println(i);

            JSONObject json_complet = new JSONObject(myJson);
            JSONArray array = new JSONArray(json_complet.getString("Questions"));
            JSONObject obj = new JSONObject(array.getString(i));

            quest.setText(obj.getString("question"));
            but_A.setText(obj.getString("reponse_1"));
            but_B.setText(obj.getString("reponse_2"));
            but_C.setText(obj.getString("reponse_3"));
            but_D.setText(obj.getString("reponse_4"));
            bonne_reponse = obj.getString(("bonne_rep"));

            is.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }

    // la table ou il y a touts les num des question et k le nouveau numero de question choisi
    //verifie que le nouveau numero de question n'a pas déja été tiré
    public boolean quest_diff (ArrayList < Integer > tab,int k)
    {
        for (int n = 0; n < tab.size(); n++) {
            System.out.println(k + " : " + tab.get(n));
            if (k == tab.get(n)) {
                return false;
            }
        }
        return true;
    }

    private class BigCalcul extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            response_valide = true;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Mise à jour de la ProgressBar
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            do {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Problèmes", Toast.LENGTH_SHORT).show();
                }
                if(isCancelled())
                    break;
                //la méthode publishProgress met à jour l'interface en invoquant la méthode onProgressUpdate
                publishProgress(progress);
                progress++;
            } while (response_valide && (progress <= 100));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            response_valide = false;
            but_pro.setClickable(true);
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
