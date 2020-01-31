package br.sofex.com.facialmap.Mapeamento;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Mapeamento.Alterar.Alterar_Mapa;
import br.sofex.com.facialmap.Mapeamento.Carregar.Upload_Foto_Mapa;
import br.sofex.com.facialmap.Mapeamento.Excluir.Excluir_Mapa;
import br.sofex.com.facialmap.Mapeamento.Mapear.Fotografar;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Menu_Mapeamento extends AppCompatActivity {

    Button BtnCadMapa,BtnUpdateMapa,BtnExcluirMapa,
    BtnListarMapas,BtnTelaMenuPrincipal,BtnAlterarMapaMarcar;
    Context mContext;
    Util util = new Util(Menu_Mapeamento.this);
    TextView PacienteMenuLogado;
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs" ;
    String Selectedpaciente = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__mapeamento);
        util.setTitleBarApp("Facial Map - Menu De Mapeamento",getSupportActionBar(),Menu_Mapeamento.this);


        BtnCadMapa = findViewById(R.id.BtnCadMapa);
        BtnUpdateMapa = findViewById(R.id.BtnAlterarMapa);
        BtnExcluirMapa = findViewById(R.id.BtnDeletarMapa);
        BtnListarMapas = findViewById(R.id.BtnCarregarFoto);
        BtnTelaMenuPrincipal = findViewById(R.id.BtnTelaMenuPrincipal);

        PacienteMenuLogado = findViewById(R.id.PacienteMenuLogado);
        sharedPreferences = getSharedPreferences(MyPREFERENCES,0);
        Selectedpaciente = sharedPreferences.getString("PacienteSelecionado",null);
        PacienteMenuLogado.setText(Selectedpaciente);

        util.ClickCallIntent(BtnCadMapa,Menu_Mapeamento.this,Fotografar.class);
        util.ClickCallIntent(BtnUpdateMapa,Menu_Mapeamento.this, Alterar_Mapa.class);
        util.ClickCallIntent(BtnExcluirMapa,Menu_Mapeamento.this, Excluir_Mapa.class);
        util.ClickCallIntent(BtnListarMapas,Menu_Mapeamento.this, Upload_Foto_Mapa.class);
        util.ClickCallIntent(BtnTelaMenuPrincipal,Menu_Mapeamento.this,MainActivity.class);
        util.ClickCallIntent(BtnTelaMenuPrincipal,Menu_Mapeamento.this,MainActivity.class);
        //util.ClickCallIntent(BtnAlterarMapaMarcar,Menu_Mapeamento.this, Marcar_Pontos.class);



    }


}
