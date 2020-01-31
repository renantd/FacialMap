package br.sofex.com.facialmap.Sofex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class SofexSolucoes  extends AppCompatActivity {

    WebView web_about_sofex;
    TextView textSterilink,textOdonto,textMira,textiwg2,tv1;
    ListView lv_solucoes;
    Button BtnVoltar;
    Util util = new Util(SofexSolucoes.this);

    private final String htmlbegin = "<div style=\"text-align:justify; line-height: 1; color:#2E6E9E ;  \">";
    private final String htmlBody = "<p>Possuímos produtos a pronta entrega que ajudarão a agregar maior valor ao seu negócio , " +
                                    "buscamos a excelência tanto em venda como no pós-venda.</p>";
    private final String htmlend = " </div>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sofex_solucoes);

        util.setTitleBarApp(" Facial Map - Soluções da Sofex",getSupportActionBar(),SofexSolucoes.this);

        web_about_sofex = findViewById(R.id.web_about_sofex);
        lv_solucoes = findViewById(R.id.lv_solucoes);
        BtnVoltar = findViewById(R.id.BtnVoltar);
        //textSterilink = findViewById(R.id.textSterilink);
        //textOdonto = findViewById(R.id.textOdonto);
        //textMira = findViewById(R.id.textMira);
        //textiwg2 = findViewById(R.id.textiwg2);

        //web_about_sofex.loadData(summary, "text/html; charset=utf-8", null);
        web_about_sofex.loadDataWithBaseURL("", htmlbegin + htmlBody + htmlend,"text/html", "utf-8", "");
        web_about_sofex.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);


        int flags[] = {R.drawable.sterilink_logo, R.drawable.odontoweb_logo1, R.drawable.mira_logo, R.drawable.iwg2_logo};

        List<String> Nomes1;
        ArrayAdapter<String> adaptador;

        // Array of strings for ListView Title

        String[] desc = new String[]{

                "\tSistema de controle para centrais de esterilização\n\n  Permite a rastreabilidade de equipamentos submetidos a esterilização\n ",
                "\tProntuário odontológico completo\n\n  Possui odontograma de fácil manuseio\n\n  Uso online compátivel com diversos dispositivos\n\n  Versões profissionais e para uso acadêmico\n",
                "\tVisualizador remoto para imagens médicas.\n\n Permite a emissão de laudos médicos juntamente com as imagens obtidas\n",
                "\tSistema PACS(Picture Archiving and Communication System).\n\n Armazena imagens médicas no padrão DICOM\n"};

        String[] solucao_nome = new String[]{"Sterilink","OdontoWeb","Mira","IWG2"};


        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 4; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("desc_solucoes", desc[i]);
            hm.put("solucao_nome", solucao_nome[i]);
            hm.put("imglist_solucoes", Integer.toString(flags[i]));
            aList.add(hm);
        }

        String[] from = {"desc_solucoes","solucao_nome", "imglist_solucoes"};
        int[] to = {R.id.desc_solucoes,R.id.solucao_nome, R.id.imglist_solucoes};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_solucoes, from, to);
        ListView androidListView = findViewById(R.id.lv_solucoes);
        androidListView.setAdapter(simpleAdapter);

        BtnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SofexSolucoes.this , MainActivity.class);
                startActivity(intent);
            }
        });

        /*textSterilink.setText("Sterilink : Sistema de controle para centrais de esterilização\n" +
                "Permite a rastreabilidade de equipamentos submetidos a esterilização\n" +
                "Clique aqui e saiba mais");
        textSterilink.setTextColor(Color.parseColor("#000000"));

        textOdonto.setText("OdontoWeb : Prontuário odontológico completo\n" +
                "Possui odontograma de fácil manuseio\n " +
                "Uso online compátivel com diversos dispositivos\n Versões profissionais e para uso acadêmico\n" +
                "Clique aqui e saiba mais");
        textOdonto.setTextColor(Color.parseColor("#000000"));

        textMira.setText("Mira : Visualizador remoto para imagens médicas.\n" +
                         "Permite a emissão de laudos médicos juntamente com as imagens obtidas\n" +
                "Clique aqui e saiba mais");
        textMira.setTextColor(Color.parseColor("#000000"));

        textiwg2.setText("IWG2 :Sistema PACS(Picture Archiving and Communication System).\n Armazena imagens médicas no padrão DICOM\n" +
                "Clique aqui e saiba mais");
        textiwg2.setTextColor(Color.parseColor("#000000"));*/
    }

}
