package br.sofex.com.facialmap.Mapeamento.Alterar.Remapear;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.Mapeamento.Alterar.Alterar_Mapa;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Remap_Reupload_Foto extends AppCompatActivity {

    Util util = new Util(Remap_Reupload_Foto.this);
    Button BtnRemapVoltar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remap__reupload__foto);

        BtnRemapVoltar = findViewById(R.id.BtnRemapVoltar);
        BtnRemapVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.CallActivity(Remap_Reupload_Foto.this, Alterar_Mapa.class);
            }
        });

    }
}
