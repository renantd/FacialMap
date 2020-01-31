package br.sofex.com.facialmap.Sofex;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class AboutFacialMap extends AppCompatActivity {

    Util util = new Util(AboutFacialMap.this);
    Button BtnVoltarTelaPrincipal;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_map);

        util.setTitleBarApp(" Facial Map - Sobre o Aplicativo",getSupportActionBar(),AboutFacialMap.this);
        BtnVoltarTelaPrincipal = findViewById(R.id.BtnVoltarTelaPrincipal);
        util.ClickCallIntent(BtnVoltarTelaPrincipal,AboutFacialMap.this, MainActivity.class);

    }

}
