package br.sofex.com.facialmap.Mapeamento.Alterar.Remapear;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.Mapeamento.Alterar.Alterar_Mapa;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Remap_Mapear_Foto extends AppCompatActivity {

    Util util = new Util(Remap_Mapear_Foto.this);
    Button BtnRempMapBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remap__mapear__foto);

        BtnRempMapBack.findViewById(R.id.BtnRempMapBack);
        BtnRempMapBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.CallActivity(Remap_Mapear_Foto.this, Alterar_Mapa.class);
            }
        });
    }
}
