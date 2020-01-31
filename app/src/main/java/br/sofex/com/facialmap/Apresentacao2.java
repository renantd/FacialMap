package br.sofex.com.facialmap;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.Login.Login;

public class Apresentacao2 extends AppCompatActivity {

    ProgressBar progressBar;
    TextView progressLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao2);

        progressLoading = findViewById(R.id.ProgressLoading);
        progressLoading.setVisibility(View.VISIBLE);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

         //Block Comment hold all three: Ctrl + Shift + /

       /* new Timer().schedule(
           new TimerTask() {
             @Override
                public void run() {
              progressBar.setVisibility(View.INVISIBLE);
              progressLoading.setVisibility(View.INVISIBLE);
              callIntent();
                }
           },
          4000
        );*/

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                progressLoading.setVisibility(View.INVISIBLE);
                callIntent();
            }
        }, 3000);

        setTitle("Facial Map");
    }

    public void callIntent() {
        Intent intent = new Intent(Apresentacao2.this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void setTitle(String title){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

}
