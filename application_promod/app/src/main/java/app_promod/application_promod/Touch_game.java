package app_promod.application_promod;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class Touch_game extends Activity implements View.OnTouchListener{

    private static final String TAG_LOG = "TouchActivity";
    private static final boolean LOG = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        log("Activity: " + stringValue(event));
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        log("Activity: " + stringValue(event));
        return true;
    }

    private String stringValue(MotionEvent event) {

        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
        }

        return "";
    }

    private static void log(String message){
        if (LOG) {

            Log.d(TAG_LOG, message);
        }
    }
}
