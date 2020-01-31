package br.sofex.com.facialmap.Mapeamento.Alterar.Remapear;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.Mapeamento.Alterar.Alterar_Mapa;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Remap_Mapeamento_Checagem extends AppCompatActivity {

    Util util = new Util(Remap_Mapeamento_Checagem.this);
    Button BtnRempCheckBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remap__mapeamento__checagem);

        BtnRempCheckBack = findViewById(R.id.BtnRempCheckBack);
        BtnRempCheckBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.CallActivity(Remap_Mapeamento_Checagem.this, Alterar_Mapa.class);
            }
        });

    }
}
