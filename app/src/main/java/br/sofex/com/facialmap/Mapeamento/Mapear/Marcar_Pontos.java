package br.sofex.com.facialmap.Mapeamento.Mapear;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.Preview.DesenharPonto;
import br.sofex.com.facialmap.Preview.PreviewCamera;
import br.sofex.com.facialmap.R;


public class Marcar_Pontos extends AppCompatActivity {

    RelativeLayout Rl1;
    Button BtnProximo_MapaProximo;
    Bitmap bitmap;
    Paint paint = new Paint();
    Button BtnDeletePoint,BtnDeleteAllPoint,BtnBackFotografar;
    Spinner SpinnerPontos;
    String selectedItem;
    List<String> list1;
    List<String> list2;
    List<String> list3;
    private AlertDialog alerta;
    Boolean Excluir = false;
    LinearLayout LinNested,LinNestedChild,LinCustom;
    ImageView img;
    DesenharPonto ImgMarcarPontos;
    //ImageView ImgMarcarPontos;
    Mensagem mensagem = new Mensagem(Marcar_Pontos.this);
    Integer PontoAux;
    Integer CoordXResult1 = 0;
    Integer CoordYResult1 = 0;
    List<Integer> ListPonto = new ArrayList<>();
    RelativeLayout RLDelPoint,RL_SelectPonto;
    String Foto_Path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar__pontos);

        //LinCustom = findViewById(R.id.LinCustom);
        BtnProximo_MapaProximo = findViewById(R.id.BtnProximo_MapaProximo);
        //LinNestedChild = findViewById(R.id.LinNestedChild);
        //LinNested = findViewById(R.id.LinNested);
        //RLDelPoint = findViewById(R.id.RLDelPoint);
        //RLDelPoint.setVisibility(View.GONE);
        img = findViewById(R.id.img);
        //BtnDeletePoint = findViewById(R.id.BtnDeletePoint);
        BtnDeleteAllPoint = findViewById(R.id.BtnDeleteAllPoint);
        ImgMarcarPontos = findViewById(R.id.ImgMarcarPontos);
        BtnBackFotografar = findViewById(R.id.BtnBackFotografar);
        RL_SelectPonto = findViewById(R.id.RL_SelectPonto);
        RL_SelectPonto.setVisibility(View.GONE);
        /*String Foto = getIntent().getStringExtra("FotoCorreta");
        Toast.makeText(MarcarPontos.this, "Foto : "+Foto, Toast.LENGTH_SHORT).show();

        if(Foto != null){
            Bitmap bit = BitmapFactory.decodeFile(Foto);
            ImgMarcarPontos.setImageBitmap(bit);
        }*/
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.antes);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);


        BtnBackFotografar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(Marcar_Pontos.this, PreviewCamera.class);
           startActivity(intent);
            }
        });
        SpinnerPontos = findViewById(R.id.SpinnerPontos);
        if(ImgMarcarPontos.listPontos() != null){
          list1 = ImgMarcarPontos.listPontos();
          list2 = ImgMarcarPontos.UpdatelistPontos();
        }

        Foto_Path = getIntent().getExtras().getString("FotoSalva");
        Log.e("App1","FotoSalva Path :"+Foto_Path);
        Log.e("App1","FotoRetorno Path :"+getIntent().getExtras().getString("FotoRetorno"));



        //String data2 = getIntent().getExtras().getString("FotoSalva");
        if(Foto_Path != null){
          Bitmap Foto = BitmapFactory.decodeFile(Foto_Path);
          img.setImageBitmap(Foto);
        }else{
          String Data1 =  getIntent().getExtras().getString("FotoRetorno");
          Bitmap bit1 = BitmapFactory.decodeFile(Data1);
          //Bitmap bit1 = BitmapFactory.decodeResource(getResources(),R.drawable.logotipo);
          img.setImageBitmap(bit1);
        }


        list1.add("Selecione um ponto");
        //BtnDeletePoint = findViewById(R.id.BtnDeletePoint);
        /*BtnDeletePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                int position = SpinnerPontos.getSelectedItemPosition();
                    if(position != 0){
                      //MsgApagarPontos();
                      MsgDelPoint();
                    }else{
                      mensagem.ErrorMsg("Ponto Inválido !");
                      SpinnerPontos.setSelection(0);
                    }
                }catch(IndexOutOfBoundsException  e){
              Toast.makeText(MarcarPontos.this, "O Ponto Selecionado não existe mais", Toast.LENGTH_SHORT).show();
            }
            }
        });*/
        BtnDeleteAllPoint = findViewById(R.id.BtnDeleteAllPoint);
        BtnDeleteAllPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MsgApagarTodosPontos();
            }
        });
        BtnProximo_MapaProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.e("App1","Lista Size : "+ ImgMarcarPontos.ListaPontosMarcados.size());
            if(ImgMarcarPontos.ListaPontosMarcados.size() <= 1){
                 mensagem.ErrorMsg("Nenhum ponto marcado"); LoadLista();
                }else{
                String data = getIntent().getExtras().getString("FotoSalva");
                String dataName = getIntent().getExtras().getString("FotoSalvaNome");
                if(data != null){
                   if(ImgMarcarPontos.listPontos() != null){
                      Intent intent = new Intent(Marcar_Pontos.this, Preencher_Mapa_Part2.class);
                      intent.putStringArrayListExtra("PontoReturn",(ArrayList<String>) ImgMarcarPontos.ListaPontosMarcados);
                      intent.putExtra("FotoSalva",data);
                      intent.putExtra("FotoSalvaNome",dataName);
                      intent.putExtra("TotalPontosMarcados",ImgMarcarPontos.CountPontos);
                      //Log.e("App1","ImgMarcarPontos.CountPontos :"+ImgMarcarPontos.CountPontos);
                      startActivity(intent);
                   }else{ mensagem.ErrorMsg("Nenhum ponto selecionado");}
                 }
             }
           /*String[] DadosLista = new String[LoadLista().size()];
                DadosLista = LoadLista().toArray(DadosLista);

                final String[] ArrayTextoToxina = new String[]{"Nome da Toxina :","Fabricante da Toxina :","Distribuidora da Toxina :","Conteúdo do Frasco:","Soro Fisiológico:","Data de Validade :","Lote :","Tipo da Agulha :","Tamanho\nda Agulha :"};
                LinCustom.addView(InsertLinearTitulo("Ponto 1"));
                LinCustom.addView(CreateRegistroDePonto(ArrayTextoToxina,DadosLista));*/
            }
        });

        /*String[] DadosLista = new String[LoadLista().size()];
        DadosLista = LoadLista().toArray(DadosLista);

        if(LoadLista() != null){
            final String[] ArrayTextoToxina = new String[]{"Nome da Toxina :","Fabricante da Toxina :","Distribuidora da Toxina :","Conteúdo do Frasco:","Soro Fisiológico:","Data de Validade :","Lote :","Tipo da Agulha :","Tamanho\nda Agulha :"};
            LinCustom.addView(InsertLinearTitulo("Ponto 1"));
            LinCustom.addView(CreateRegistroDePonto(ArrayTextoToxina,DadosLista));
        }*/

        ArrayAdapter<String> AdapterTipo = new ArrayAdapter<>(Marcar_Pontos.this,R.layout.center_item,R.id.center_item,list1);
        AdapterTipo.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        SpinnerPontos.setAdapter(AdapterTipo);

        SpinnerPontos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        SpinnerPontos.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        SpinnerPontos.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        SpinnerPontos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              if(SpinnerPontos.getSelectedItem().toString() != null){
                    if(!SpinnerPontos.getSelectedItem().toString().equalsIgnoreCase("Selecione um ponto")){
                      selectedItem = SpinnerPontos.getSelectedItem().toString();
                    }

                }else{ Log.e("Error", "null ");}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


    }

    public Integer RefactorCoordX(String str){
        String str4 = "";
        try{
            String Pont_Cont = str.substring(0, str.indexOf("X")+1);
            String requiredString = str.replaceAll(Pont_Cont, "");
            str4 = requiredString.substring(0, requiredString.indexOf(Pont_Cont)+4);
        }
        catch(NullPointerException e){
            Log.e("Error :","Error :"+e);
        }
        return Integer.parseInt(str4);
    }
    public Integer RefactorCoordY(String str){
        Integer x = Integer.parseInt(str.substring(str.lastIndexOf("Y") + 1));
        //Log.e("App123"," Y "+str);
        return x;
    }
    public  void  LoadListCoord(){
        list1 = ImgMarcarPontos.listPontos();
    }

    @Override
    public void onResume(){
        super.onResume();

        String NomeToxina = getIntent().getStringExtra("NomeToxina");
        String MarcaToxina = getIntent().getStringExtra("MarcaToxina");
        String DistribuidoraToxina = getIntent().getStringExtra("DistribuidoraToxina");
        String ConteudoFrascoToxina = getIntent().getStringExtra("ConteudoFrascoToxina");
        String DiametroAgulha = getIntent().getStringExtra("DiametroAgulha");
        String ComprimentoAgulha = getIntent().getStringExtra("ComprimentoAgulha");
        String DataDeValidade = getIntent().getStringExtra("DataDeValidade");
        String LoteToxina = getIntent().getStringExtra("LoteToxina");

        //list1 = ImgMarcarPontos.listPontos();

        /*if(ValidDadosMarcação(NomeToxina,MarcaToxina,DistribuidoraToxina,ConteudoFrascoToxina,DiametroAgulha,ComprimentoAgulha,DataDeValidade,LoteToxina) == true){

            final String[] ArrayTextoToxina = new String[]{"Nome da Toxina :","Fabricante da Toxina :","Distribuidora da Toxina :","Conteúdo do Frasco:","Soro Fisiológico:","Data de Validade :","Lote :","Tipo da Agulha :","Tamanho da Agulha :"};
            String[] ArrayToxinaValores = new String[]{NomeToxina,MarcaToxina,DistribuidoraToxina,ConteudoFrascoToxina,"Solução Salina 0.9%",DataDeValidade,LoteToxina,DiametroAgulha,ComprimentoAgulha,};

            Intent iin= getIntent();
            Bundle Ponto = iin.getExtras();
            Integer PontoResult1 = 0;

            Intent iin2= getIntent();
            Bundle CoordenadaX = iin2.getExtras();
            if(CoordenadaX!=null){
                Integer Result = (Integer) CoordenadaX.get("CoordenadaX1");
                CoordXResult1 = Result;
            }

            Intent iin3= getIntent();
            Bundle CoordenadaY = iin3.getExtras();
            if(CoordenadaY!=null){
                Integer Result = (Integer) CoordenadaY.get("CoordenadaY1");
                CoordYResult1 = Result;
            }


            Log.e("App1","CoordXResilt "+CoordXResult1);
            Log.e("App1","CoordYResilt "+CoordYResult1);

            ImgMarcarPontos.DrawnPoint(CoordXResult1,CoordYResult1);
            ListPonto.add(PontoResult1);
            PontoAux = PontoResult1;
            if(Ponto!=null){Integer PontoResult = (Integer) Ponto.get("PontoReturn");PontoResult1 = PontoResult;}
            LinCustom.addView(InsertLinearTitulo("Ponto "+PontoResult1));
            LinCustom.addView(CreateRegistroDePonto(ArrayTextoToxina,ArrayToxinaValores));

        }else{
            LinearLayout.LayoutParams layoutParamsTextView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // TODO: Criação de layouts
            TextView TituloText = new TextView(this);
            TituloText.setText("Nunhum Ponto Marcado !");
            TituloText.setGravity(Gravity.CENTER);
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            TituloText.setTypeface(boldTypeface);
            TituloText.setTextSize(18);
            TituloText.setTextColor(Color.parseColor("#000000"));
            TituloText.setLayoutParams(layoutParamsTextView);

            LinCustom.addView(TituloText);
        }*/

    }
    @Override
    public void onPause(){
        super.onPause();
        Log.e("Pause"," Pause");
    }
    @Override
    public void onStop(){

        Log.e("Pause"," Stop");
        ListPonto.add(PontoAux);
        Log.e("Pause1"," Result ListPonto : "+PontoAux);
        super.onStop();
    }
    @Override
    public void onDestroy(){super.onDestroy();Log.e("Pause"," Destroy");}

    private void MsgApagarTodosPontos(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.layout_apagar_todos_pontos, null);
        //definimos para o botão do layout um clickListener

        Button Btn_DelAllPointMapClose = view.findViewById(R.id.Btn_DelAllPointMapClose);
        Btn_DelAllPointMapClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button Btn_DelAllPointMapSave = view.findViewById(R.id.Btn_DelAllPointMapSave);
        Btn_DelAllPointMapSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImgMarcarPontos.DeleteAllCanvas();
                ImgMarcarPontos.ClearLista();
                ImgMarcarPontos.count = 0;
                List<String> listAux = new ArrayList<>();
                listAux.add("Selecione um ponto");
                /*SpinnerPontos.setSelection(0);
                List<String> listAux = new ArrayList<>();
                listAux.add("Selecione um ponto");
                list1 = null;
                list1 = listAux;
                Log.e("App1","Lista A :"+list1);*/

                ArrayAdapter<String> AdapterTipo = new ArrayAdapter<>(Marcar_Pontos.this,R.layout.center_item,R.id.center_item,listAux);
                AdapterTipo.setDropDownViewResource(R.layout.center_item);
                // Data bind the spinner with array adapter items
                SpinnerPontos.setAdapter(AdapterTipo);

                SpinnerPontos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                SpinnerPontos.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
                SpinnerPontos.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                SpinnerPontos.setSelection(0);
                alerta.dismiss();
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

    //TODO: Não implementado
    public void MsgDelPoint(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.layout_apagar_ponto, null);
        //definimos para o botão do layout um clickListener

        Button Btn_DelPointMapClose = view.findViewById(R.id.Btn_DelPointMapClose);
        Btn_DelPointMapClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });
        Button Btn_DelPointMapSave = view.findViewById(R.id.Btn_DelPointMapSave);
        Btn_DelPointMapSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImgMarcarPontos.DeletePoint(RefactorCoordX(selectedItem),RefactorCoordY(selectedItem));
                ImgMarcarPontos.UpdatelistPontos();
                SpinnerPontos.setSelection(0);

                /*ArrayAdapter<String> AdapterTipo = new ArrayAdapter<>(MarcarPontos.this,R.layout.center_item,R.id.center_item,ImgMarcarPontos.list3);
                AdapterTipo.setDropDownViewResource(R.layout.center_item);
                SpinnerPontos.setAdapter(AdapterTipo);*/
                list3 = null;
                ArrayAdapter<String> AdapterTipo = new ArrayAdapter<>(Marcar_Pontos.this,R.layout.center_item,R.id.center_item,ImgMarcarPontos.listPontos());
                SpinnerPontos.setAdapter(AdapterTipo);
                AdapterTipo.setNotifyOnChange(true);

                alerta.dismiss();
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }
    public void inserirPonto(Context context){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.inserir_ponto_rosto, null);
        //definimos para o botão do layout um clickListener

        Button Btn_InserirPontoMapClose = view.findViewById(R.id.Btn_InserirPontoMapClose);
        Btn_InserirPontoMapClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {alerta.dismiss();}
        });
        Button Btn_InserirPontoMapSave = view.findViewById(R.id.Btn_InserirPontoMapSave);
        Btn_InserirPontoMapSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }

    public RelativeLayout InsertLinearTitulo(String titulo){
        RelativeLayout TituloPonto = new RelativeLayout(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(10, 20, 10, 20);
        layoutParams.weight = 1f;
        TituloPonto.setLayoutParams(layoutParams);
        TituloPonto.setBackgroundColor(Color.parseColor("#23A4CA"));

        //TODO : Layout com margin customizada para o Relative layout1
        LinearLayout.LayoutParams RelativeParamsMargins = new LinearLayout.LayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        RelativeParamsMargins.setMargins(10, 40, 10, 40);

        TituloPonto.addView(TextoDinamico(titulo,"#FFFFFF",LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER | Gravity.CENTER_HORIZONTAL));
        return  TituloPonto;
    }
    public TextView TextoDinamico(String Texto , String ColorText, int LarguraLinha, int AlturaLinha, int TextoAlinhamento){
        LinearLayout.LayoutParams layoutParamsTextView = new LinearLayout.LayoutParams(LarguraLinha,AlturaLinha);
        // TODO: Criação de layouts
        TextView TituloText = new TextView(this);
        TituloText.setText(Texto);
        TituloText.setGravity(TextoAlinhamento);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        TituloText.setTypeface(boldTypeface);
        TituloText.setTextSize(18);
        TituloText.setTextColor(Color.parseColor(ColorText));
        TituloText.setLayoutParams(layoutParamsTextView);
        return TituloText;
    }
    public LinearLayout CreateRegistroDinamico(String Texto ,String TextoValor){

        /*LinearLayout LinearPontoBody = new LinearLayout(MarcarPontos.this);
        LinearLayout.LayoutParams layoutParamsPontoBody = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsPontoBody.setMargins(20, 40, 20, 10);
        LinearPontoBody.setLayoutParams(layoutParamsPontoBody);
        //LinearPontoBody.setBackgroundColor(Color.YELLOW);
        LinearPontoBody.setOrientation(LinearLayout.VERTICAL);*/

        ////
        LinearLayout LinearPontoBodyPart1 = new LinearLayout(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParamsBodyPart1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsBodyPart1.setMargins(40, 10, 40, 10);
        //LinearPontoBody.setBackgroundColor(Color.RED);
        //LinearPontoBody.setBackground(ContextCompat.getDrawable(MarcarPontos.this, R.drawable.bottom_border));
        layoutParamsBodyPart1.weight = 1f;
        LinearPontoBodyPart1.setLayoutParams(layoutParamsBodyPart1);
        LinearPontoBodyPart1.setOrientation(LinearLayout.VERTICAL);


        RelativeLayout RelativeNomeToxina = new RelativeLayout(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParamsNomeToxina = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsNomeToxina.weight = 1f;
        layoutParamsNomeToxina.setMargins(5, 15, 5, 10);
        //RelativeNomeToxina.setBackgroundColor(Color.GRAY);
        RelativeNomeToxina.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        RelativeNomeToxina.setLayoutParams(layoutParamsNomeToxina);


        RelativeLayout RelativeValorToxina = new RelativeLayout(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParamsValorToxina = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsValorToxina.weight = 1f;
        RelativeValorToxina.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        layoutParamsValorToxina.setMargins(5, 15, 5, 10);
        // RelativeValorToxina.setBackgroundColor(Color.GRAY);
        RelativeValorToxina.setLayoutParams(layoutParamsValorToxina);


        RelativeLayout RelBotaoToxinaAlterar = new RelativeLayout(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParamsRelBotaoToxinaAlterar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsRelBotaoToxinaAlterar.weight = 1.45f;
        RelBotaoToxinaAlterar.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        //layoutParamsRelBotaoToxinaAlterar.setMargins(5, 15, 5, 10);
        // RelativeValorToxina.setBackgroundColor(Color.GRAY);
        RelBotaoToxinaAlterar.setLayoutParams(layoutParamsRelBotaoToxinaAlterar);

        Button BotaoToxinaAlterar = new Button(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParamsBotaoToxinaAlterar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsBotaoToxinaAlterar.setMargins(0, 15, 0, 0);
        BotaoToxinaAlterar.setLayoutParams(layoutParamsBotaoToxinaAlterar);
        layoutParamsBodyPart1.setMargins(40, 10, 40, 10);
        BotaoToxinaAlterar.setText("Alterar");
        BotaoToxinaAlterar.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
        BotaoToxinaAlterar.setTypeface(boldTypeface);
        BotaoToxinaAlterar.setTextSize(16);
        BotaoToxinaAlterar.setTextColor(Color.parseColor("#FFFFFF"));
        BotaoToxinaAlterar.setBackground(ContextCompat.getDrawable(Marcar_Pontos.this, R.drawable.btn_rounded_bluefacial));
        RelBotaoToxinaAlterar.addView(BotaoToxinaAlterar);


        RelativeNomeToxina.addView(TextoDinamico(Texto,"#000000",LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0));
        RelativeValorToxina.addView(TextoDinamico(TextoValor,"#000000",LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER | Gravity.CENTER_VERTICAL));
        LinearPontoBodyPart1.addView(RelativeNomeToxina);
        LinearPontoBodyPart1.addView(RelativeValorToxina);

        if(Texto != "Soro Fisiológico:"){
            LinearPontoBodyPart1.addView(RelBotaoToxinaAlterar);
        }

        return LinearPontoBodyPart1;
    }
    public RelativeLayout CreateRegistroDePonto(String[] Texto ,String[] TextoValor){

        RelativeLayout RelativePontoBody = new RelativeLayout(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 30, 20, 5);
        RelativePontoBody.setGravity(Gravity.CENTER);
        layoutParams.weight = 1f;
        RelativePontoBody.setLayoutParams(layoutParams);
        RelativePontoBody.setBackground(ContextCompat.getDrawable(Marcar_Pontos.this, R.drawable.shape_border_blue_rounded));

        //TODO: Linear Layout para Relativelayout
        LinearLayout LinearPontoBody = new LinearLayout(Marcar_Pontos.this);
        LinearLayout.LayoutParams layoutParamsPontoBody = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsPontoBody.setMargins(20, 40, 20, 60);
        LinearPontoBody.setLayoutParams(layoutParamsPontoBody);
        //LinearPontoBody.setBackgroundColor(Color.YELLOW);
        LinearPontoBody.setOrientation(LinearLayout.VERTICAL);

        //String[] ArrayTextoToxina = new String[]{"Nome da Toxina :","Fabricante da Toxina :","Distribuidora da Toxina :","Conteúdo do Frasco :","Soro Fisiológico :","Data de Validade :","Lote :","Tipo da Agulha :","Tamanho da Agulha :"};
        //String[] ArrayToxinaValores = new String[]{"Botulinum Toxin Type A","Botox","Allegran","100U","Soro Fisiológio 0,9%","06/09/2020","C3214D1","30G 4mm","4mm"};

        for(int i = 0; i <= 8; i++){LinearPontoBody.addView(CreateRegistroDinamico(Texto[i],TextoValor[i]));}

        RelativePontoBody.addView(LinearPontoBody);
        //CreateRegistroDinamico("Nome da Toxina :","Botulinum Toxin Type A","#000000",LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0));


        return RelativePontoBody;
    }
    public void SetToxinas(String PontoNome,String[] Texto ,String[] TextoValor){
        LinCustom.addView(InsertLinearTitulo(PontoNome));
        LinCustom.addView(CreateRegistroDePonto(Texto,TextoValor));
    }
    public List<String> LoadLista(){return ImgMarcarPontos.ListToxinaValores;}
    public List<String> LoadLista2(){return list1;}
    public Boolean ValidDadosMarcação(String NomeToxina,String MarcaToxina,String DistribuidoraToxina,String ConteudoFrascoToxina,String DiametroAgulha,String ComprimentoAgulha,String DataDeNascimento,String LoteToxina){

        if(NomeToxina != null && MarcaToxina != "Fabricante do Botox" && DistribuidoraToxina != "Distribuidora" && ConteudoFrascoToxina != "Conteúdo do Frasco" &&
                DiametroAgulha != "Diametro" && DiametroAgulha != "Comprimento" && NomeToxina != null && MarcaToxina != null){return true;
        }else{return false;}

    }


}
