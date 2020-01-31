package br.sofex.com.facialmap.Estatisticas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.R;

public class Estatisticas extends AppCompatActivity {

    Button BtnVoltarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        BtnVoltarMain = findViewById(R.id.BtnVoltarMain);
        BtnVoltarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Estatisticas.this, MainActivity.class);
            startActivity(intent);
            }
        });

    }

}
