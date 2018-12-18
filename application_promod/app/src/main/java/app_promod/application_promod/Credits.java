package app_promod.application_promod;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;

public class Credits extends AppCompatActivity implements
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    private GestureDetectorCompat mDetector;
    private ArrayList<String> evan_list = new ArrayList();
    private ArrayList<String> tristan_list = new ArrayList();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);

        evan_list.add("Evan Blondeau");
        evan_list.add("エバン　ブロンド－");
        evan_list.add("4556414E 424C4F4E44454155");
        tristan_list.add("Tristan Lavalou");
        tristan_list.add("トリスタ　ラバル");
        tristan_list.add("5452495354414E 4C4156414C4F55");
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
        TextView evan = findViewById(R.id.textView11);
        TextView tristan = findViewById(R.id.textView12);
        i++;
        i=i%evan_list.size();
        evan.setText(evan_list.get(i));
        tristan.setText(tristan_list.get(i));
        return true;
    }
}
