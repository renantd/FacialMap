package br.sofex.com.facialmap.Mapeamento.Mapear;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Mapeamento_Checagem extends AppCompatActivity {

    Mensagem mensagem = new Mensagem(Mapeamento_Checagem.this);
    Button BtnSalvar,BtnVoltar;
    Util util = new Util(Mapeamento_Checagem.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapeamento__checagem);
        util.setTitleBarApp("Facial Map - Checagem de Mapeamento",getSupportActionBar(),Mapeamento_Checagem.this);

        BtnVoltar = findViewById(R.id.BtnChekVoltar);
        BtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(util.CallActivity(Mapeamento_Checagem.this, Adicionar_Pontos.class));
            }
        });

        BtnSalvar = findViewById(R.id.BtnChekSalvar);
        BtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensagem.SucessMsg("Mapeamento salvo com sucesso !");
            }
        });

    }

}
