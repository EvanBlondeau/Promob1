package app_promod.application_promod;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;

public class Test_question extends AppCompatActivity {
    //*range + 1
    int i = (int)((Math.random()*6));
    ArrayList<Integer> alist =new ArrayList<Integer>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final Button button_3 = findViewById(R.id.button_test);


        ImageView im = findViewById(R.id.imageView2);
        im.setImageResource(R.mipmap.clair);
        final TextView text = findViewById(R.id.textView2);
        final TextView text_1 = findViewById(R.id.textView3);
        final TextView text_2 = findViewById(R.id.textView4);
        final TextView text_3 = findViewById(R.id.textView5);
        final TextView text_4 = findViewById(R.id.textView6);


        button_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                text_4.setText("");
                text.setText("");
                text_1.setText("");
                text_2.setText("");
                text_3.setText("");
                try {
                    InputStream is =  getAssets().open("questionnaires.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String myJson = new String(buffer, "UTF-8");

                    alist.add(i);
                    System.out.println(i);
                    JSONObject json_complet = new JSONObject(myJson);
                    JSONArray array = new JSONArray(json_complet.getString("Questions"));
                    JSONObject obj = new JSONObject(array.getString(i));

                    text_4.setText(obj.getString("question"));
                    text.setText(obj.getString("reponse_1"));
                    text_1.setText(obj.getString("reponse_2"));
                    text_2.setText(obj.getString("reponse_3"));
                    text_3.setText(obj.getString("reponse_4"));

                    } catch (Exception e) {
                     e.printStackTrace();
                     System.out.println(e);
                }
                int k =0;
                do{
                    k++;
                    i = (int)((Math.random()*5));
                }while(quest_diff(alist,i)==false && k<50);


            }
        });

    }

    public boolean quest_diff(ArrayList<Integer> tab,int k)
    {
        for(int n =0;n<tab.size();n++)
        {
            System.out.println(k+" : "+tab.get(n));
            if(k==tab.get(n))
            {
                return false;
            }
        }
        return true;
    }


}


