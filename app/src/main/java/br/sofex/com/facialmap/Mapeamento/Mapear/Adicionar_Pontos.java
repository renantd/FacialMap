package br.sofex.com.facialmap.Mapeamento.Mapear;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Adicionar_Pontos extends AppCompatActivity {

    Button BtnBackFoto,BtnToCheck;
    Util util = new Util(Adicionar_Pontos.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar__pontos);

        util.setTitleBarApp("Facial Map - Mapear Foto",getSupportActionBar(),Adicionar_Pontos.this);

        BtnBackFoto = findViewById(R.id.BtnBackFoto);
        BtnToCheck = findViewById(R.id.BtnToCheck);


        BtnToCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(util.CallActivity(Adicionar_Pontos.this,Mapeamento_Checagem.class));
            }
        });

    }
}
