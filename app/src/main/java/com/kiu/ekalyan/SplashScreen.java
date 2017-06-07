package com.kiu.ekalyan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity {

    long delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        Timer runSplash=new Timer();


        TimerTask showSplash=new TimerTask() {
            @Override
            public void run() {
                finish();

                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
            }
        };

        runSplash.schedule(showSplash,delay);


    }
}
