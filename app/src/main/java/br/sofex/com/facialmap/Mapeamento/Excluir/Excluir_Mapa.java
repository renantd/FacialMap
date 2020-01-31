package br.sofex.com.facialmap.Mapeamento.Excluir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.sofex.com.facialmap.Mapeamento.Menu_Mapeamento;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Excluir_Mapa extends AppCompatActivity {

    LinearLayout lin1,lin2,lin3,lin4,lin5;
    Spinner sp_data_procura,sp_tipo_procura,sp_periodo_alt1,sp_periodo_tipo_alt;
    ImageView img_logo_mapa;
    ListView lv_mapas;
    String tipo,data_tipo_alt;
    String dataprocura;
    Button Btn_Pesquisar,BtnVoltarDelMap;
    private AlertDialog alerta;

    int imgpaciente[] = {R.drawable.antes,R.drawable.antes_ponto,R.drawable.depois};
    int imgpacienteDefault[] = {R.drawable.logotipo,R.drawable.logotipo,R.drawable.logotipo};
    int imgpaciente1[] = {R.drawable.antes,R.drawable.antes,R.drawable.antes};
    int imgpaciente2[] = {R.drawable.antes_ponto,R.drawable.antes_ponto,R.drawable.antes_ponto};
    int imgpaciente3[] = {R.drawable.depois,R.drawable.depois,R.drawable.depois};

    int imgfolder[] = {R.drawable.folderblue,R.drawable.folderblue,R.drawable.folderblue};
    int imgarrow[] = {R.drawable.arrow_dow_36x36,R.drawable.arrow_dow_36x36,R.drawable.arrow_dow_36x36};

    String[] data1 = new String[]{"Selecione uma opção","01/01/2018","01/06/2018","01/10/2018"};
    String[] periodos = new String[]{"Selecione uma opção","Semana","Mês","Bimestre","Trimestre","Semestre"};
    String[] semana = new String[]{"Selecione uma opção","01/01/2018 - 08/01/2018","09/01/2018 - 16/01/2018","17/01/2018 - 24/01/2018","25/01/2018 - 31/01/2018"};
    String[] Mes = new String[]{"Selecione uma opção","01/01/2018 - 01/02/2018","01/03/2018 - 01/04/2018","01/05/2018 - 01/06/2018","01/07/2018 - 01/08/2018","01/09/2018 - 01/10/2018","01/11/2018 - 31/12/2018"};
    String[] bimestre = new String[]{"Selecione uma opção","01/01/2018 - 01/03/2018","01/04/2018 - 01/06/2018","01/07/2018 - 01/09/2018","01/10/2018 - 01/12/2018"};
    String[] Trimestre = new String[]{"Selecione uma opção","01/01/2018 - 01/03/2018","01/04/2018 - 01/06/2018","01/07/2018 - 01/09/2018","01/10/2018 - 01/12/2018"};
    String[] Semestre = new String[]{"Selecione uma opção","01/01/2018 - 01/06/2018","02/06/2018 - 31/12/2018"};

    Util util = new Util(Excluir_Mapa.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir__mapa);

        util.setTitleBarApp("Facial Map - Excluir Mapa",getSupportActionBar(),Excluir_Mapa.this);

        String[] tipoExclusao = new String[]{"Selecione uma opção","Exculir por data","Exculir por período","Excluir Tudo"};
        String[] data = new String[]{"Selecione uma opção","01/01/2018","01/06/2018","01/10/2018"};

        BtnVoltarDelMap = findViewById(R.id.BtnVoltarDelMap);
        BtnVoltarDelMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Excluir_Mapa.this, Menu_Mapeamento.class);
                startActivity(intent);
            }
        });

    }

    public void SpinnerCustom(Context context, Spinner spin, String[] itens, int layout_center, int id_textview, int viewAligment, int colorArrow){

        // Initialize an array adapter
        ArrayAdapter<String> AdapterTipo = new ArrayAdapter<>(context,layout_center,id_textview,itens);
        AdapterTipo.setDropDownViewResource(layout_center);

        // Data bind the spinner with array adapter items
        spin.setAdapter(AdapterTipo);
        spin.setTextAlignment(viewAligment);
        spin.setGravity(viewAligment);//Gravity setting for positioning the currently selected item.
        spin.getBackground().setColorFilter(getResources().getColor(colorArrow), PorterDuff.Mode.SRC_ATOP);

    }

    public void MsgDeleteConf(String str){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Excluir_Mapa.this);

        LayoutInflater factory = LayoutInflater.from(Excluir_Mapa.this);
        View view = factory.inflate(R.layout.error, null);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        TextView textdialog = view.findViewById(R.id.tv_error);
        textdialog.setText(str);
        textdialog.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textdialog.setTextSize(16);
        textdialog.setTextColor(Color.BLACK);

        builder.setView(view);
        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Mapa Facial - Alerta \t");

        title.setBackgroundColor(Color.parseColor("#FFFF0000"));
        title.setPadding(15, 15, 15, 15);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(18);
        builder.setCustomTitle(title);


        alerta = builder.create();
        alerta.show();
        alerta.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }
}
