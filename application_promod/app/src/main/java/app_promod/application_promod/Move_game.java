package app_promod.application_promod;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import java.util.List;

public class Move_game extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager_param;

    SensorManager sensorManager;
    private ProgressBar mProgressBar;
    ImageView image;

    float light_numb;

    public double timer_start,timer_end,timer_gagne,timer_gagne2;

    float x_magn2, y_magn2, z_magn2;

    int bonne_reponse=1;
    int enigme_courante;
    int niveau_dans_lenigme = 0;

    // L'accéléromètre
    Sensor accelerometer,magnet,light;

    String param1, score_1;

    private MediaPlayer mPlayer = null;

    public final static String EXTRA_MESS = "competition";
    boolean cest_bon=false;

    //test accelero et magn
    // Attribut de la classe pour calculer  l'orientation
    float[] acceleromterVector=new float[3];
    float[] magneticVector=new float[3];
    float[] resultMatrix=new float[9];
    float[] values=new float[3];
    int nb_enigme_possible=2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        param1 =  this.getIntent().getStringExtra("key");
        score_1 = this.getIntent().getStringExtra("score_1");

        //final Button button_par = findViewById(R.id.button_param);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setProgress(0);

        timer_start = new Date().getTime();

        System.out.println(timer_start);
        image = findViewById(R.id.image_door);

        enigme_courante = (int) ((Math.random() * nb_enigme_possible));
        System.out.println(enigme_courante);

        //liste des images ou l'on selectionne une image qui correspond a un niveau
        switch(enigme_courante)
        {
            case 0 :
                image.setImageResource(R.drawable.archdebd);//jeu_tourne_telephone
                break;
            case 1 :
                image.setImageResource(R.mipmap.noir);//jeu_lumiere
                break;
            case 2 :
                image.setImageResource(R.mipmap.test);
                break;

        }



     /* button_par.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setContentView(R.layout.activity_param_move);
                TextView text_param = findViewById(R.id.textView);

                // Instanicer le SensorManager
                sensorManager_param = (SensorManager) getSystemService(SENSOR_SERVICE);
                // Faire la liste des capteurs de l'appareil
                String param =listSensor();
                text_param.setText(param);
            }

        });*/

        // Gérer les capteurs&#160;:
        // Instancier le gestionnaire des capteurs, le SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Instancier l'accéléromètre
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        magnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);



    }

    @Override
    protected void onPause() {
        // unregister the sensor (désenregistrer le capteur)
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magnet);
        super.onPause();

        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }


    @Override
    protected void onResume() {
        /* Ce qu'en dit Google&#160;dans le cas de l'accéléromètre :
         * «&#160; Ce n'est pas nécessaire d'avoir les évènements des capteurs à un rythme trop rapide.
         * En utilisant un rythme moins rapide (SENSOR_DELAY_UI), nous obtenons un filtre
         * automatique de bas-niveau qui "extrait" la gravité  de l'accélération.
         * Un autre bénéfice étant que l'on utilise moins d'énergie et de CPU.&#160;»
         */
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnet, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

/********************************************************************/
/** SensorEventListener*************************************************/
    /********************************************************************/

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Rien à faire la plupart du temps
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x,y,z,x_2,z_2,y_2;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acceleromterVector=event.values;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticVector=event.values;
        }

        // Demander au sensorManager la matric de Rotation (resultMatric)
        SensorManager.getRotationMatrix(resultMatrix, null, acceleromterVector, magneticVector);

        // Demander au SensorManager le vecteur d'orientation associé (values)
        SensorManager.getOrientation(resultMatrix, values);

        DecimalFormat df = new DecimalFormat("##");
        df.setRoundingMode(RoundingMode.DOWN);
        // l'azimuth
        x_2 = (float) Math.toDegrees(values[0]);
        // le pitch
        y_2 = (float) Math.toDegrees(values[1]);
        // le roll
        z_2 = (float) Math.toDegrees(values[2]);

        x= Float.valueOf(df.format(x_2));
        y= Float.valueOf(df.format(y_2));
        z= Float.valueOf(df.format(z_2));

        System.out.println("x : "+x);
        System.out.println("y : "+y);
        System.out.println("z : "+z);

       /*if(enigme_courante==2 && (x==(float)0)&&(z==(float)0))
        {
            System.out.println("pas mal ;)");
            mProgressBar.setProgress(75);
            image.setImageResource(R.mipmap.tigre);
            end_move();
        }*/


        if(enigme_courante==0 && niveau_dans_lenigme== 4 && cest_bon)
        {
            //arrete le timer
            if(bonne_reponse == 1)
            {
                end_move();
            }
        }
        //jeux tourner le telephone
        //position initiale

        if(enigme_courante==0 && niveau_dans_lenigme==0 &&((x>(float)-95) && (x<(float)-85))&&((y>(float)-10) &&(y<(float)10) )&&(z<(float)10&&(z>-10))  )
        {
            System.out.println("pas mal ;)");

            //musique courte pour lui dire qu'il avance ;)

            niveau_dans_lenigme = 1;

            mProgressBar.setProgress(25);
            playSound(R.raw.gameboy);

        }

        if(enigme_courante==0 && niveau_dans_lenigme==1 &&((x>(float)-140) && (x<(float)-135))&&((y>(float)-10) &&(y<(float)10) )&&((z<(float)10) &&(z>-10)))
        {
            System.out.println("pas mal 2 ;)");

            //musique courte pour lui dire qu'il avance ;)

            niveau_dans_lenigme = 2;

            mProgressBar.setProgress(50);
            playSound(R.raw.gameboy);

        }

        if(enigme_courante==0 && niveau_dans_lenigme==2 && ((x>(float)-5) && (x<(float)5))&&((y>(float)-10) &&(y<(float)10) )&&(z<(float)10&&(z>-10)) )
        {
            System.out.println("pas mal ;)");

            //musique courte pour lui dire qu'il avance ;)

            niveau_dans_lenigme = 3;

            mProgressBar.setProgress(75);
            playSound(R.raw.gameboy);

        }

        if(enigme_courante==0 && niveau_dans_lenigme==3 &&((x>(float)-95) && (x<(float)-85))&&((y>(float)-10) &&(y<(float)10) )&&(z<(float)10&&(z>-10)))
        {
            System.out.println("pas mal ;)");

            //musique courte pour lui dire qu'il avance ;)
            playSound(R.raw.gameboy);
            niveau_dans_lenigme = 4;
            mProgressBar.setProgress(100);
            image.setImageResource(R.drawable.archdebd);
            mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.GREEN));
            timer_end = new Date().getTime();
            //timer de 1s pour voir la porte ouverte
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    if(bonne_reponse == 1)
                    {
                        end_move();
                    }
                }
            }, 5000);
            //lancement de la nouvelle activité
        }




        // Récupérer les valeurs du capteur
       /* float x_acc, y_acc, z_acc;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
           x_acc = event.values[0];
            y_acc = event.values[1];
            z_acc = event.values[2];


            // si le téléphone est retourné
            if(((x_acc>6)&&(y_acc<(float)4)) || ((x_acc<-6)&&(y_acc<(float)4)))
            {
                System.out.println("accélérometre");
                mProgressBar.setProgress(75);
                image.setImageResource(R.mipmap.tigre);
            }

            if(x_acc>(float)6 && y_acc>(float)4)
            {
                System.out.println("accélérometre");
                mProgressBar.setProgress(10);
                image.setImageResource(R.mipmap.ic_launcher_round);
            }

           System.out.println("x_acc: "+x_acc);
            System.out.println("y_acc: "+y_acc);
            System.out.println("z_acc: "+z_acc);

            //Magnetometer (μT)-22.00 5.9 -43.10

        }*/


      /*  if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            x_magn2 = event.values[0];
            y_magn2 = event.values[1];
            z_magn2 = event.values[2];

            DecimalFormat df = new DecimalFormat("##");
            df.setRoundingMode(RoundingMode.DOWN);
            float x_magn,y_magn,z_magn;

            //on tronque les valeur car trop precise et dur à avoir sinon don on tronque a 0 chiffre apres la virgule
            x_magn= Float.valueOf(df.format(x_magn2));
            y_magn= Float.valueOf(df.format(y_magn2));
            z_magn= Float.valueOf(df.format(z_magn2));

           /* System.out.println("x_magn: "+x_magn);
            System.out.println("y_magn: "+y_magn);
            System.out.println("z_magn: "+z_magn);

            // si le téléphone est retourné
            if((x_magn==(float)-22) && (y_magn==(float)5) && (z_magn==(float)-43))
            {
                System.out.println("c'est gagné 1");
                mProgressBar.setProgress(100);
                //changer la couleur de la barre
                mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.GREEN));
                image.setImageResource(R.mipmap.sunset);
            }
            //ecran debout (initial)
            if((x_magn==(float)22) && (y_magn==(float)5) && (z_magn==(float)43))
            {
                System.out.println("gagné");
                mProgressBar.setProgress(0);
                image.setImageResource(R.mipmap.sky);
            }

            //faut que je transtipe les chiffre reçu pour perdre en précision sinon ça marche pas.
            //incliner de 45% environ 19.73 -11.38 43.10
            // ou 11.38 19.73 43.10
            if((x_magn==(float)19) && (y_magn==(float)-11) && (z_magn==(float)43))
            {
                System.out.println("gagné222");
                mProgressBar.setProgress(42);
                image.setImageResource(R.mipmap.test);
            }
            //incliner de 45% environ 19.73 -11.38 43.10
            // ou 11.38 19.73 43.10

            if(((x_magn==(float) 11) && (y_magn==(float)19) && (z_magn==(float)43)))
            {
                System.out.println("gagné111");
                mProgressBar.setProgress(42);
                image.setImageResource(R.mipmap.sunset);
            }

            //telephone sur une surface droite face (ecran) vers le haut
            // coordonnée :22.00 -43.10 5.90
            if((x_magn==(float)22) && (y_magn==(float)-43) && (z_magn==(float)5))
            {
                System.out.println("c'est gagné 43.1 -22.0");
                mProgressBar.setProgress(50);
                image.setImageResource(R.mipmap.ic_launcher);
            }



        }*/
        boolean valide_rep=false;
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            light_numb = event.values[0];

          // System.out.println("light: " + light_numb);
            if(light_numb>(float)100 && light_numb<(float)400 && (enigme_courante==1))
            {
                image.setImageResource(R.mipmap.noir);
                mProgressBar.setProgress(25);
                mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.RED));
            }
            if(light_numb>(float)400 && light_numb<(float)1000 && (enigme_courante==1))
            {
                image.setImageResource(R.mipmap.moyen);
                mProgressBar.setProgress(25);
                mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.RED));
            }
            if(light_numb>(float)1000 && light_numb<(float)2000 && (enigme_courante==1))
            {
                image.setImageResource(R.mipmap.clair);
                mProgressBar.setProgress(50);
                mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.YELLOW));
            }
            if(light_numb>(float)2000 && light_numb<(float)4000 && (enigme_courante==1))
            {
                image.setImageResource(R.mipmap.clair2);
                mProgressBar.setProgress(75);
                mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.CYAN));
            }
            if(light_numb>(float)5500 && (enigme_courante==1) && bonne_reponse==1)
            {
                bonne_reponse=2;
                image.setImageResource(R.mipmap.fin);
                mProgressBar.setProgress(100);
                mProgressBar.setProgressTintList( ColorStateList.valueOf(Color.GREEN));
                final Handler handler = new Handler();
                timer_end = new Date().getTime();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                            end_move();

                    }
                }, 5000);
            }


        }

    }



    /**
     * Lister les capteurs existant
     * <p>
     * Trouve la liste de tous les capteurs existants, trouve un capteur spécifique ou l'ensemble des capteurs d'un type fixé.
     */
    private String listSensor() {
        // Trouver tous les capteurs de l'appareil :
        List<Sensor> sensors = sensorManager_param.getSensorList(Sensor.TYPE_ALL);
        // La chaîne descriptive de chaque capteur
        StringBuffer sensorDesc = new StringBuffer();
        // pour chaque capteur trouvé, construire sa chaîne descriptive
        for (Sensor sensor : sensors) {
            sensorDesc.append("New sensor detected : \r\n");
            sensorDesc.append("\tName: " + sensor.getName() + "\r\n");
            sensorDesc.append("\tType: " + getType(sensor.getType()) + "\r\n");
            sensorDesc.append("Version: " + sensor.getVersion() + "\r\n");
            sensorDesc.append("Resolution (in the sensor unit): " + sensor.getResolution() + "\r\n");
            sensorDesc.append("Power in mA used by this sensor while in use" + sensor.getPower() + "\r\n");
            sensorDesc.append("Vendor: " + sensor.getVendor() + "\r\n");
            sensorDesc.append("Maximum range of the sensor in the sensor's unit." + sensor.getMaximumRange() + "\r\n");
            sensorDesc.append("Minimum delay allowed between two events in microsecond" + " or zero if this sensor only returns a value when the data it's measuring changes" + sensor.getMinDelay() + "\r\n");
        }


        // Pour trouver un capteur spécifique&#160;:
        Sensor gyroscopeDefault = sensorManager_param.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // Pour trouver tous les capteurs d'un type fixé :
        List<Sensor> gyroscopes = sensorManager_param.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);

        System.out.println("magnetometre default : "+gyroscopeDefault);
        System.out.println("magnetometre  : "+gyroscopes);


        // Faire quelque chose de cette liste
        return sensorDesc.toString();
    }

    private String getType(int type) {
        String strType;
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                strType = "TYPE_ACCELEROMETER";
                break;
            case Sensor.TYPE_GRAVITY:
                strType = "TYPE_GRAVITY";
                break;
            case Sensor.TYPE_GYROSCOPE:
                strType = "TYPE_GYROSCOPE";
                break;
            case Sensor.TYPE_LIGHT:
                strType = "TYPE_LIGHT";
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                strType = "TYPE_LINEAR_ACCELERATION";
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                strType = "TYPE_MAGNETIC_FIELD";
                break;
            case Sensor.TYPE_PRESSURE:
                strType = "TYPE_PRESSURE";
                break;
            case Sensor.TYPE_PROXIMITY:
                strType = "TYPE_PROXIMITY";
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                strType = "TYPE_ROTATION_VECTOR";
                break;
            default:
                strType = "TYPE_UNKNOW";
                break;
        }
        return strType;
    }


    private void end_move() {
        bonne_reponse = 2;

        double time = timer_end - timer_start;
        System.out.println(time);
        time = time -(double)5000;

        setContentView(R.layout.activity_fin_qr);
        TextView text_score = findViewById(R.id.textView_score);
        TextView text_score_com = findViewById(R.id.textView_com_score);
        if (time > 25000) {
            text_score_com.setText("tu es pas très doué");
            playSound(R.raw.lose);
        } else if(time<25000 && time >10000)
        {
            text_score_com.setText("tu te débrouille bien ");
            playSound(R.raw.appl5);
        }else if(time<10000)
        {
            text_score_com.setText("tu est un monstre");
            playSound(R.raw.appla10);
        }


        time = time / 1000;
        final String score = String.valueOf(time) + " secondes";
        text_score.setText(score);

        if (param1.compareTo("entrainement") == 0) {

            Button button_menu = findViewById(R.id.but_suivant);
            button_menu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Move_game.this, MainActivity.class);
                    startActivity(intent);
                }

            });

        }else if(param1.compareTo("competition") == 0)
        {
            Button button_suivant = findViewById(R.id.but_suivant);
            button_suivant.setText("Jeu suivant");

            button_suivant.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Move_game.this, Morse.class);
                    intent.putExtra("score_1",score_1);
                    intent.putExtra("score_2",score);
                    intent.putExtra("key",EXTRA_MESS);
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
