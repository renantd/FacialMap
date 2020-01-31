package br.sofex.com.facialmap.Sofex;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class AboutSofex  extends AppCompatActivity {

    WebView web_about_sofex;
    Util util = new Util(AboutSofex.this);
    Button BtnSobSofexBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_sofex);

        BtnSobSofexBack = findViewById(R.id.BtnSobSofexBack);

        web_about_sofex = findViewById(R.id.web_about_sofex);
        String summary = "<html><body><p style='text-align:justify; line-height: 1.5;'>A Sofex surgiu da necessidade de se ter no mercado uma empresa altamente especializada em soluções de " +
         "processamentos de imagens e dados digitais, com grandes volumes de informação e nos padrões internacionais (DICOM, DICONDE), " +
         "tanto na área médica como industrial. Os seus fundadores atuam no mercado desde 1987 na área médica e hospitalar. " +
         "Atualmente toda a expertise acumulada ao longo de décadas esta disponível também para área acadêmica com soluções inovadoras " +
         "que são referência no mercado. Seja bem-vindo ao futuro. Nós somos a  Sofex.</p></body></html>";
        web_about_sofex.loadData(summary, "text/html; charset=utf-8", "utf-8");
        web_about_sofex.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);

        util.setTitleBarApp(" Facial Map - Sobre a Sofex",getSupportActionBar(),AboutSofex.this);
        util.ClickCallIntent(BtnSobSofexBack,AboutSofex.this, MainActivity.class);

    }

}
