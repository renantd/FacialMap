package br.sofex.com.facialmap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.Util.Util;

public class Apresentacao1 extends AppCompatActivity {

    Util util = new Util(Apresentacao1.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao1);
        //callIntent();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               callIntent();
            }
        }, 2500);

        util.setTitleBarApp("Sofex - Facial Map",getSupportActionBar(),Apresentacao1.this);

    }

    public void callIntent() {
        //Intent intent = new Intent(Apresentacao1.this,Apresentacao2.class);
        Intent intent = new Intent(Apresentacao1.this, Apresentacao2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
