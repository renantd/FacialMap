package br.sofex.com.facialmap.Mapeamento.Mapear;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskDeleteLRMapa;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskGetLastRecordMapa;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskInsertMapa;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskInsertPontoApli;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Mapeamento.Menu_Mapeamento;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.Preview.DesenharPontoPreenMapa;
import br.sofex.com.facialmap.Preview.DesenharPontoPreview;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.MaskEdit;
import br.sofex.com.facialmap.Util.ProportionalImageView;
import br.sofex.com.facialmap.Util.Util;

public class Preencher_Mapa_Part2 extends AppCompatActivity {

    /*TODO: Variáveis do tipo Android*/
    Activity                activity;
    FloatingActionButton    FAB1;
    DesenharPontoPreview    DesenharPP;
    DesenharPontoPreenMapa  DPP1;
    ProportionalImageView   pivPreview;
    Spinner                 Spinner_ResultPontos;
    Spinner                 SpinnerTeste;
    Button                  Btn_MapFotoPreview;
    Button                  Btn_MapFotoSave;
    Button                  Btn_MapFotoVoltar;
    Button                  Btn_MapFotoMenu;
    ListView                listViewAddPontos;
    Adapter                 AdapterTipo1;
    RelativeLayout          RL_SpinnerResPontos;



    /*TODO: Variáveis Globais*/
     private Integer   PontoCountAux;
     private String Edit_Nome_ToxinaAux;
     private String SpinnerMarcaToxinaAux;
     private String Spinner_DistribuidoraAux;
     private String Spinner_Conteudo_FrascoAux;
     private String txtViewSoroFisiAux;
     private String EditVolumeAplicadoAux;
     private String SpinnerDiamAgulhaAux;
     private String SpinnerCompAgulhaAux;
     private String Edit_DT_ValidadeAux;
     private String Edit_Lote_ToxinaAux;

    /*TODO: Variáveis do tipo AlertDialog*/
    private AlertDialog     alerta;

    /*TODO: Variáveis do tipo lista*/

    HashMap<String, String>        hmResult       =  new HashMap<>();
    List<HashMap<String, String>>  aListResult    =  new ArrayList<>();
    List<String>                   listViewPonto  =  new ArrayList<>();
    List<String>                   listPontoCount =  new ArrayList<>();
    List<Integer>                  CheckPontoAdd  =  new ArrayList<>();
    List<String>                   ListCoord1     =  new ArrayList<>();
    List<String>                   List2          =  new ArrayList<>();
    List<String>                   NomePontoFinal =  new ArrayList<>();


    /*TODO: Variáveis de Login*/
    SharedPreferences   sharedPreferences;
    String              Foto;
    String              FotoPath;
    String              FotoNome;
    String              MyPREFERENCES = "MyPrefs" ;
    String              Selectedpaciente = "";
    Integer             PontoSalvo = 0;


    /*TODO: Intanciamentos de classes*/
    Mensagem mensagem = new Mensagem(Preencher_Mapa_Part2.this);
    Util util = new Util(Preencher_Mapa_Part2.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preencher__mapa__part2);

        util.setTitleBarApp("Facial Map - Marcar Rosto Part 2",getSupportActionBar(),Preencher_Mapa_Part2.this);
        /*TODO: View*/
        Spinner_ResultPontos   = findViewById(R.id.Spinner_ResultPontos);
        listViewAddPontos      = findViewById(R.id.listViewAddPontos);
        //Btn_MapFotoPreview     = findViewById(R.id.Btn_MapFotoPreview);
        Btn_MapFotoSave        = findViewById(R.id.Btn_MapFotoSave);
        Btn_MapFotoVoltar      = findViewById(R.id.Btn_MapFotoVoltar);
        Btn_MapFotoMenu        = findViewById(R.id.Btn_MapFotoMenu);
        SpinnerTeste           = findViewById(R.id.spinnerTeste);
        RL_SpinnerResPontos    = findViewById(R.id.RL_SpinnerResPontos);
        pivPreview             = findViewById(R.id.pivPreview);
        DPP1                   = findViewById(R.id.DPP1);
        //RL_SpinnerResPontos.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(MyPREFERENCES,0);
        Selectedpaciente = sharedPreferences.getString("PacienteSelecionado",null);
        final String Foto = getIntent().getExtras().getString("FotoSalva");
        FotoPath = Foto;

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(d);
        Log.e("APP","Data: "+formattedDate);

        Date h = Calendar.getInstance().getTime();
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        final String formattedHora = df2.format(h);
        Log.e("APP","Hora: "+formattedHora);

        Boolean b = isTablet();
        String dispositivo = "";
        if(b == true){
            Log.e("APP","É tablet ? Sim");
            dispositivo = "Tablet";
        }else{
          Log.e("APP","É tablet ? Não");
          dispositivo = "Smartphone";
        }

         /*doTaskInsertMapa taskInsert = new doTaskInsertMapa(Preencher_Mapa_Part2.this,
           formattedDate,formattedHora,dispositivo,BitmapToFotoByteArray(Foto),"FI "+formattedDate+"_"+formattedHora,
           BitmapToFotoByteArray(Foto),  "FM "+formattedDate+"_"+formattedHora,
           BitmapDefaultToByteArray(),     "FF "+formattedDate+"_"+formattedHora,
           BitmapDefaultToByteArray(),   "FV "+formattedDate+"_"+formattedHora,
           BitmapDefaultToByteArray(), "FH "+formattedDate+"_"+formattedHora);
           taskInsert.execute();*/
        doTaskInsertMapa taskInsert = new doTaskInsertMapa(Preencher_Mapa_Part2.this,
        formattedDate,formattedHora,dispositivo,BitmapToFotoByteArray(Foto),"FI "+formattedDate+"_"+formattedHora,
        BitmapToFotoByteArray(Foto),  "FM "+formattedDate+"_"+formattedHora,
        BitmapDefaultToByteArray(),     "FF "+formattedDate+"_"+formattedHora,
        BitmapDefaultToByteArray(),   "FV "+formattedDate+"_"+formattedHora,
        BitmapDefaultToByteArray(), "FH "+formattedDate+"_"+formattedHora);
        taskInsert.execute();


        List2 = getIntent().getStringArrayListExtra("PontoReturn");
        Log.e("App1","TotalPontosMarcados :"+getIntent().getExtras().getInt("TotalPontosMarcados"));

        if(List2 !=  null) {
            int count = 0;
            ListCoord1 = List2;
            for(String str : List2){ Log.e("App1", "Lista Result : " + str); }

            ArrayAdapter<String> AdapterTipo = new ArrayAdapter<>(Preencher_Mapa_Part2.this, R.layout.center_item, R.id.center_item, List2);
            AdapterTipo.setDropDownViewResource(R.layout.center_item);
            // Data bind the spinner with array adapter items
            Spinner_ResultPontos.setAdapter(AdapterTipo);
            Spinner_ResultPontos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Spinner_ResultPontos.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
            Spinner_ResultPontos.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

            Bitmap bit1 = BitmapFactory.decodeFile(Foto);
            pivPreview.setImageBitmap(bit1);
            DPP1.ListCoord = List2;

            for (String x : List2) {
                if(!x.equalsIgnoreCase("Selecione um ponto")){
                  //Log.e("App1", "List2 : " + x);
                  //String strTeste = "P"+100+"X"+620+" P"+100+"Y"+526;
                  String strTeste2 =  x.substring(x.indexOf("P")+1);
                  String strTeste3 = strTeste2.substring(0, strTeste2.indexOf("X"));
                  //Log.e("App1"," Count : "+strTeste3);
                  listPontoCount.add(strTeste3);
                }
            }

            String[] CountArray = new String[listPontoCount.size()];
            CountArray = listPontoCount.toArray(CountArray);

            String[] NomesArray = new String[List2.size()];
            NomesArray = List2.toArray(NomesArray);

            listViewAddPontos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //Log.e("UPDATE","Selected : "+lv_itemsUpdate.getItemAtPosition(i));
              Log.e("UPDATE","Selected : "+listViewAddPontos.getItemAtPosition(i).toString());
              Intent in1 = new Intent(Preencher_Mapa_Part2.this, MainActivity.class);
              startActivity(in1);
                }
            });

            Spinner_ResultPontos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                  if(!Spinner_ResultPontos.getSelectedItem().toString().equalsIgnoreCase("Selecione um ponto")){
                       String strTeste2 =  Spinner_ResultPontos.getSelectedItem().toString().substring(Spinner_ResultPontos.getSelectedItem().toString().indexOf("P")+1);
                       String strTeste3 = strTeste2.substring(0, strTeste2.indexOf("X"));
                       //Toast.makeText(Preencher_Mapa_Part2.this, "strTeste3 "+strTeste3, Toast.LENGTH_LONG).show();

                     if (CheckPontoAdd.contains(Integer.parseInt(strTeste3))) {
                          //Toast.makeText(Preencher_Mapa_Part2.this, "Contem "+strTeste3, Toast.LENGTH_LONG).show();
                          mensagem.ErrorMsg("Não é possível adicionar os dados,\n para este ponto .\n\nO Ponto já foi adicionado !");
                        }else{
                         //Toast.makeText(Preencher_Mapa_Part2.this, "Não contem ", Toast.LENGTH_LONG).show();
                         /*String strTeste2 =  Spinner_ResultPontos.getSelectedItem().toString().substring(Spinner_ResultPontos.getSelectedItem().toString().indexOf("P")+1);
                            String strTeste3 = strTeste2.substring(0, strTeste2.indexOf("X"));*/
                         //listViewPonto = PreencherPonto(Integer.parseInt(strTeste3));

                         Date d = Calendar.getInstance().getTime();
                         SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                         String formattedDate = df.format(d);
                         Log.e("APP","Data: "+formattedDate);

                         Date h = Calendar.getInstance().getTime();
                         SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
                         final String formattedHora = df2.format(h);
                         Log.e("APP","Hora: "+formattedHora);

                         Boolean b = isTablet();
                         String dispositivo = "";
                         if(b == true){
                           Log.e("APP","É tablet ? Sim");
                           dispositivo = "Tablet";
                         }else{
                           Log.e("APP","É tablet ? Não");
                           dispositivo = "Smartphone";
                         }

                         //TODO: Foto Salva
                         Bitmap bit1 = BitmapFactory.decodeFile(Foto);
                         ByteArrayOutputStream stream = new ByteArrayOutputStream();
                         bit1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                         byte[] byteArray = stream.toByteArray();

                         //TODO: Foto Default(drawable to bitmap /Bitmap to byte array)
                         Bitmap BitDefault = BitmapFactory.decodeResource(Preencher_Mapa_Part2.this.getResources(),R.drawable.logotipo);
                         ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                         BitDefault.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                         byte[] byteArray2 = stream2.toByteArray();

                         String NomePontoAux = ""; Integer CoordXAux = 0; Integer CoordYAux = 0;
                         if(!Spinner_ResultPontos.getSelectedItem().toString().equalsIgnoreCase("Selecione um ponto")){
                           NomePontoAux = NomePonto(Spinner_ResultPontos.getSelectedItem().toString());
                           CoordXAux = RefactorCoordX(Spinner_ResultPontos.getSelectedItem().toString());
                           CoordYAux = RefactorCoordY(Spinner_ResultPontos.getSelectedItem().toString());
                         }
                          //Log.e("App1","NomePontoAux1 : "+NomePontoAux);
                          //Log.e("App1","CoordXAux1 : "+CoordXAux);
                          //Log.e("App1","CoordYAux1 : "+CoordYAux);

                         PreencherPonto(NomePontoAux,CoordXAux,CoordYAux,byteArray,"FotoSalva "+formattedDate+"_"+formattedHora,formattedDate,formattedHora,dispositivo,Integer.parseInt(strTeste3));
                        //PreencherPonto(Integer.parseInt(strTeste3));
                        /*List<HashMap<String, String>> aList = new ArrayList<>();

                        for(String x : listViewPonto){
                            hmResult.put("Edit_Nome_Toxina","123");
                            hmResult.put("SpinnerMarcaToxina","123");
                            hmResult.put("Spinner_Distribuidora","123");
                            hmResult.put("Spinner_Conteudo_Frasco","123");
                            hmResult.put("txtViewSoroFisi", "123");
                            //hm.put("imgBotox", DataNascArray[i]);
                            hmResult.put("SpinnerDiamAgulha", "123");
                            hmResult.put("SpinnerCompAgulha", "123");
                            hmResult.put("Edit_DT_Validade", "123");
                            hmResult.put("Edit_Lote_Toxina", "123");
                            aList.add(hmResult);

                            String[] from = {"Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                            "Edit_DT_Validade", "Edit_Lote_Toxina"};

                            int[] to = {R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                            R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.show_list_ponto_saved, from, to);
                            listViewAddPontos.setAdapter(simpleAdapter);
                        }*/
                     }
                  }
                }
                @Override public void onNothingSelected(AdapterView<?> adapterView) {}});


            //TODO: Teste
            List<String> ListaPontoFinal = new ArrayList<>();
            ListaPontoFinal.add("Selecione um ponto");
            SpinnerTeste.setSelection(0);
            for (String x : List2) {
              if(!x.equalsIgnoreCase("Selecione um ponto")){
                String strTeste4 =  x.substring(x.indexOf("P")+1);
                String strTeste5 = strTeste4.substring(0, strTeste4.indexOf("X"));
                ListaPontoFinal.add("Ponto P"+strTeste5);
              }
            }

            ArrayAdapter<String> AdapterTipo2 = new ArrayAdapter<>(Preencher_Mapa_Part2.this, R.layout.center_item, R.id.center_item, ListaPontoFinal);
            AdapterTipo2.setDropDownViewResource(R.layout.center_item);
            // Data bind the spinner with array adapter items
            SpinnerTeste.setAdapter(AdapterTipo2);
            SpinnerTeste.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            SpinnerTeste.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
            SpinnerTeste.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

            SpinnerTeste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(!SpinnerTeste.getSelectedItem().toString().equalsIgnoreCase("Selecione um ponto")){
                        String strTeste1 =  SpinnerTeste.getSelectedItem().toString().substring(SpinnerTeste.getSelectedItem().toString().indexOf(" P")+1); //TODO: Retorna P1
                        String strTeste2 =  SpinnerTeste.getSelectedItem().toString().substring(SpinnerTeste.getSelectedItem().toString().indexOf(" P")+2); //TODO: Retorna 1
                        if (CheckPontoAdd.contains(Integer.parseInt(strTeste2))) {
                            //Toast.makeText(Preencher_Mapa_Part2.this, "Contem "+strTeste3, Toast.LENGTH_LONG).show();
                            mensagem.ErrorMsg("Não é possível adicionar os dados,\n para este ponto .\n\nO Ponto já foi adicionado !");
                        }else{
                            Date d = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String formattedDate = df.format(d);
                            Log.e("APP","Data: "+formattedDate);

                            Date h = Calendar.getInstance().getTime();
                            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
                            final String formattedHora = df2.format(h);
                            Log.e("APP","Hora: "+formattedHora);

                            Boolean b = isTablet();
                            String dispositivo = "";
                            if(b == true){
                                Log.e("APP","É tablet ? Sim");
                                dispositivo = "Tablet";
                            }else{
                                Log.e("APP","É tablet ? Não");
                                dispositivo = "Smartphone";
                            }

                            //TODO: Foto Salva
                            Bitmap bit1 = BitmapFactory.decodeFile(Foto);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bit1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            //TODO: Foto Default(drawable to bitmap /Bitmap to byte array)
                            Bitmap BitDefault = BitmapFactory.decodeResource(Preencher_Mapa_Part2.this.getResources(),R.drawable.logotipo);
                            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                            BitDefault.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                            byte[] byteArray2 = stream2.toByteArray();

                            String NomePontoAux = ""; Integer CoordXAux = 0; Integer CoordYAux = 0;
                            if(!SpinnerTeste.getSelectedItem().toString().equalsIgnoreCase("Selecione um ponto")){
                                for(String str: List2){
                                    if(str.contains(strTeste1)){
                                        //Log.e("App1","SpinnerTeste : "+str);
                                        //Log.e("App1","SpinnerTesteAux : Ponto"+NomePonto(str)+ " -  X" +RefactorCoordX (str)+" Y"+RefactorCoordY (str));
                                        //Log.e("App1","SpinnerTeste1 : "+strTeste1);
                                        NomePontoAux   = NomePonto      (str);
                                        CoordXAux      = RefactorCoordX (str);
                                        CoordYAux      = RefactorCoordY (str);
                                    }
                                }

                            }
                            for(String str : List2){
                              if(str.contains(strTeste1)){
                                PreencherPonto(NomePontoAux,CoordXAux,CoordYAux,byteArray,"FotoSalva "+formattedDate+"_"+formattedHora,
                                formattedDate,formattedHora,dispositivo,Integer.parseInt(strTeste2)); PontoSalvo++;
                              }

                            } SpinnerTeste.setSelection(0);
                        }
                    }

                }

              @Override public void onNothingSelected(AdapterView<?> adapterView) {}});

            /*if(Spinner_ResultPontos.getSelectedItemPosition() != 0){
                String strTeste2 =  Spinner_ResultPontos.getSelectedItem().toString().substring(Spinner_ResultPontos.getSelectedItem().toString().indexOf("P")+1);
                String strTeste3 = strTeste2.substring(0, strTeste2.indexOf("X"));

                for (int i = 0; i < NomesArray.length; i++) {
                    hmResult.put("Txt_PontoNumber",strTeste3+"1231");
                    hmResult.put("Edit_Nome_Toxina","N/A");
                    hmResult.put("SpinnerMarcaToxina","N/A");
                    hmResult.put("Spinner_Distribuidora","N/A");
                    hmResult.put("Spinner_Conteudo_Frasco","N/A");
                    hmResult.put("txtViewSoroFisi", "N/A");
                    hmResult.put("SpinnerDiamAgulha", "N/A");
                    hmResult.put("SpinnerCompAgulha", "N/A");
                    hmResult.put("Edit_DT_Validade", "N/A");
                    hmResult.put("Edit_Lote_Toxina", "N/A");
                    aListResult.add(hmResult);
                }

                String[] from2 = {"Txt_PontoNumber","Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                "Edit_DT_Validade", "Edit_Lote_Toxina"};

                int[] to2 = {R.id.Txt_PontoNumber,R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                SimpleAdapter simpleAdapter2 = new SimpleAdapter(getBaseContext(), aListResult, R.layout.show_list_ponto_saved, from2, to2);
                listViewAddPontos.setAdapter(simpleAdapter2);
                simpleAdapter2.notifyDataSetChanged();
            }else{
                for (int i = 0; i < NomesArray.length; i++) {
                    hmResult.put("Edit_Nome_Toxina","N/A");
                    hmResult.put("SpinnerMarcaToxina","N/A");
                    hmResult.put("Spinner_Distribuidora","N/A");
                    hmResult.put("Spinner_Conteudo_Frasco","N/A");
                    hmResult.put("txtViewSoroFisi", "N/A");
                    hmResult.put("SpinnerDiamAgulha", "N/A");
                    hmResult.put("SpinnerCompAgulha", "N/A");
                    hmResult.put("Edit_DT_Validade", "N/A");
                    hmResult.put("Edit_Lote_Toxina", "N/A");
                    aListResult.add(hmResult);
                }

                String[] from2 = {"Txt_PontoNumber","Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                "Edit_DT_Validade", "Edit_Lote_Toxina"};

                int[] to2 = {R.id.Txt_PontoNumber,R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                SimpleAdapter simpleAdapter2 = new SimpleAdapter(getBaseContext(), aListResult, R.layout.show_list_ponto_saved, from2, to2);
                listViewAddPontos.setAdapter(simpleAdapter2);
                simpleAdapter2.notifyDataSetChanged();
           }*/
            //String strTeste2 =  Spinner_ResultPontos.getSelectedItem().toString().substring(Spinner_ResultPontos.getSelectedItem().toString().indexOf("P")+1);
            //String strTeste3 = strTeste2.substring(0, strTeste2.indexOf("X"));

            //hmResult.put("Txt_PontoNumber",strTeste3+"1231");
            /*hmResult.put("Edit_Nome_Toxina","N/A");
                hmResult.put("SpinnerMarcaToxina","N/A");
                hmResult.put("Spinner_Distribuidora","N/A");
                hmResult.put("Spinner_Conteudo_Frasco","N/A");
                hmResult.put("txtViewSoroFisi", "N/A");
                hmResult.put("SpinnerDiamAgulha", "N/A");
                hmResult.put("SpinnerCompAgulha", "N/A");
                hmResult.put("Edit_DT_Validade", "N/A");
                hmResult.put("Edit_Lote_Toxina", "N/A");
                aListResult.add(hmResult);


            String[] from2 = {"Txt_PontoNumber","Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
            "Edit_DT_Validade", "Edit_Lote_Toxina"};

            int[] to2 = {R.id.Txt_PontoNumber,R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
            R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

            SimpleAdapter simpleAdapter2 = new SimpleAdapter(getBaseContext(), aListResult, R.layout.show_list_ponto_saved, from2, to2);
            listViewAddPontos.setAdapter(simpleAdapter2);
            simpleAdapter2.notifyDataSetChanged();*/

        }
        listViewAddPontos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           String text = (String)listViewAddPontos.getItemAtPosition(i);
           Log.e("App1","text : "+text);
            }
        });
       /*   String[] NomesArray = new String[List2.size()];
            NomesArray = List2.toArray(NomesArray);

            if(List2.size() == 0){
                List<HashMap<String, String>> aList = new ArrayList<>();
                HashMap<String, String> hm = new HashMap<>();
                    hm.put("Edit_Nome_Toxina","N/A");
                    hm.put("SpinnerMarcaToxina","N/A");
                    hm.put("Spinner_Distribuidora","N/A");
                    hm.put("Spinner_Conteudo_Frasco","N/A");
                    hm.put("txtViewSoroFisi", "N/A");
                    //hm.put("imgBotox", DataNascArray[i]);
                    hm.put("SpinnerDiamAgulha", "N/A");
                    hm.put("SpinnerCompAgulha", "N/A");
                    hm.put("Edit_DT_Validade", "N/A");
                    hm.put("Edit_Lote_Toxina", "N/A");
                    aList.add(hm);

                String[] from = {"Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                        "Edit_DT_Validade", "Edit_Lote_Toxina"};

                int[] to = {R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                        R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.show_list_ponto_saved, from, to);
                listViewAddPontos.setAdapter(simpleAdapter);

            }else{
                List<HashMap<String, String>> aList2 = new ArrayList<>();
                HashMap<String, String> hm2 = new HashMap<>();
                for (int i = 0; i < NomesArray.length; i++) {
                    hm2.put("Edit_Nome_Toxina",listViewPonto.get(1));
                    hm2.put("SpinnerMarcaToxina", listViewPonto.get(1));
                    hm2.put("Spinner_Distribuidora", listViewPonto.get(2));
                    hm2.put("Spinner_Conteudo_Frasco", listViewPonto.get(3));
                    hm2.put("txtViewSoroFisi", listViewPonto.get(4));
                    //hm.put("imgBotox", DataNascArray[i]);
                    hm2.put("SpinnerDiamAgulha", listViewPonto.get(5));
                    hm2.put("SpinnerCompAgulha", listViewPonto.get(6));
                    hm2.put("Edit_DT_Validade", listViewPonto.get(7));
                    hm2.put("Edit_Lote_Toxina", listViewPonto.get(8));
                    aList2.add(hm2);
                }

                String[] from2 = {"Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                        "Edit_DT_Validade", "Edit_Lote_Toxina"};

                int[] to2 = {R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                        R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                SimpleAdapter simpleAdapter2 = new SimpleAdapter(getBaseContext(), aList2, R.layout.show_list_ponto_saved, from2, to2);
                listViewAddPontos.setAdapter(simpleAdapter2);
            }*/

        /*Btn_MapFotoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           */
        /*Intent intent = new Intent(Preencher_Mapa_Part2.this,PreviewMapa.class);
        ArrayList<HashMap<String, String>> arl = new ArrayList<>(); arl.add(hmResult);
        intent.putExtra("arraylist", arl);
        startActivity(intent);*/
        /*Bitmap bit1 = BitmapFactory.decodeFile(Foto);
        PreviewMap(aListResult,ListCoord1,bit1);}});*/
        Btn_MapFotoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          /*Date d = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(d);
                Log.e("APP","Data: "+formattedDate);

                Date h = Calendar.getInstance().getTime();
                SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
                final String formattedHora = df2.format(h);
                Log.e("APP","Hora: "+formattedHora);

                Boolean b = isTablet();
                String dispositivo = "";
                if(b == true){
                  Log.e("APP","É tablet ? Sim");
                  dispositivo = "Tablet";
                }else{
                  Log.e("APP","É tablet ? Não");
                  dispositivo = "Smartphone";
                }

                //TODO: Foto Salva
                Bitmap bit1 = BitmapFactory.decodeFile(Foto);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bit1.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                //TODO: Foto Default(drawable to bitmap /Bitmap to byte array)
                Bitmap BitDefault = BitmapFactory.decodeResource(Preencher_Mapa_Part2.this.getResources(),R.drawable.logotipo);
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                BitDefault.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                byte[] byteArray2 = stream2.toByteArray();

                try {

                  doTaskInsertMapa taskinsertmap = new doTaskInsertMapa(Preencher_Mapa_Part2.this,formattedDate,formattedHora,dispositivo,
                  byteArray,"FI_"+formattedDate,byteArray,"FM_"+formattedDate,byteArray2,"FF_"+formattedDate,
                  byteArray2,"FV_"+formattedDate,byteArray2,"FH_"+formattedDate,ListCoord1,Nome_Toxina,MarcaToxina,Distribuidora,
                  Integer.parseInt(Conteudo_Frasco.replace("U","")),
                  Integer.parseInt(Volume_Aplicado.replace(" ml","")),
                  Integer.parseInt(SoroFisiologico.replace("ml","")),
                  Integer.parseInt(DiamAgulha.replace("G","")),
                  Integer.parseInt(CompAgulha.replace("mm","")),
                  DT_Validade,Lote_Toxina);
                  taskinsertmap.execute().get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
           if(PontoSalvo < getIntent().getExtras().getInt("TotalPontosMarcados")){
              mensagem.ErrorMsg("Ainda a pontos a serem registrados\n Favor cadastrar todos os pontos,\n antes de finalizar o mapeamento !");
           }else{
              DPP1.ClearLista();
              mensagem.SucessMsgClickRedirect("Mapa Registrado com sucesso !");
              //Intent intent = new Intent(Preencher_Mapa_Part2.this, MainActivity.class);
              //startActivity(intent);
           }
            }
        });
        Btn_MapFotoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Long CodMapLR = 0l;

           try {CodMapLR = new doTaskGetLastRecordMapa(Preencher_Mapa_Part2.this).execute().get();}
           catch (ExecutionException e) {e.printStackTrace();}
           catch (InterruptedException e) {e.printStackTrace();}

           try {doTaskDeleteLRMapa taskDelete = new doTaskDeleteLRMapa(Preencher_Mapa_Part2.this,CodMapLR);
           taskDelete.execute().get();}
           catch (ExecutionException e) {e.printStackTrace();}
           catch (InterruptedException e) {e.printStackTrace();}


           Intent intent = new Intent(Preencher_Mapa_Part2.this, Marcar_Pontos.class);
           intent.putExtra("FotoRetorno", FotoPath);
           startActivity(intent);
            }
        });
        Btn_MapFotoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //TODO: Exclui o ultimo mapa criado , caso o usuario retorne para a tela principal
                //TODO: Sem salvar os pontos salvos
           Long CodMapLR = 0l;
           try {
                    CodMapLR = new doTaskGetLastRecordMapa(Preencher_Mapa_Part2.this).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           try {
                    doTaskDeleteLRMapa taskDelete = new doTaskDeleteLRMapa(Preencher_Mapa_Part2.this,CodMapLR);
                    taskDelete.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           Intent intent = new Intent(Preencher_Mapa_Part2.this, Menu_Mapeamento.class);
           startActivity(intent);
            }
        });

    }

    public String NomePonto(String ListaRow){
        return ListaRow.substring(0, ListaRow.indexOf("X"));
    }
    public String GetPontoCoordX(String ListaRow){
        String NomePonto = ListaRow.substring(0, ListaRow.indexOf("X"));
        return ListaRow.substring(NomePonto.length()+1, ListaRow.indexOf(" P"));
    }
    public String GetPontoCoordY(String ListaRow){
        String PontoYCut = ListaRow.substring(0, ListaRow.indexOf(" P")+4);
        return ListaRow.replace(PontoYCut,"");
    }

    public Integer RefactorCoordX(String str){
        String str4 = "";
        try{
            String Pont_Cont = str.substring(0, str.indexOf("X")+1);
            String requiredString = str.replaceAll(Pont_Cont, "");
            str4 = requiredString.substring(0,  requiredString.indexOf(" "));
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

    public  Boolean isTablet(){
        DisplayMetrics metrics = new DisplayMetrics();
        Preencher_Mapa_Part2.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            return true;
        }else{
            return false;
        }
    }

    /*TODO: preencher Passaporte*/
    private List<String> PreencherPonto(final String NomePontoAux, final Integer CoordXAux, final Integer CoordYAux , final byte[] Foto, final String FotoNome, final String Data, final String Hora, final String Dispositivo, final Integer PontoCount) {

        final List<String> listViewPonto = new ArrayList<>();
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preencher_dados_ponto, null);


        final EditText Edit_Nome_Toxina = view.findViewById(R.id.Edit_Nome_Toxina);
        final Spinner SpinnerMarcaToxina = view.findViewById(R.id.SpinnerMarcaToxina);
        final Spinner Spinner_Distribuidora = view.findViewById(R.id.Spinner_Distribuidora);
        final Spinner Spinner_Conteudo_Frasco = view.findViewById(R.id.Spinner_Conteudo_Frasco);
        final TextView txtViewSoroFisi = view.findViewById(R.id.txtViewSoroFisi);
        final EditText EditVolumeAplicado = view.findViewById(R.id.EditVolumeAplicado);
        final ImageView imgBotox = view.findViewById(R.id.imgBotox);
        final Spinner SpinnerDiamAgulha = view.findViewById(R.id.SpinnerDiamAgulha);
        final Spinner SpinnerCompAgulha = view.findViewById(R.id.SpinnerCompAgulha);
        final EditText Edit_DT_Validade = view.findViewById(R.id.Edit_DT_Validade);
        final EditText Edit_Lote_Toxina = view.findViewById(R.id.Edit_Lote_Toxina);

        //TODO: Arrays
        String[] listMarcaToxina = {"Fabricante","BOTOX","XEOMIN","PROSIGNE","DYSPORT","BOTULIFT","BOTULIM"};
        String[] listDistribuidora = {"Distribuidora","ALLERGAN","BIOLAB","CRISTÁLIA","IPSEN","BERGAMO","BLAU"};
        String[] listConteudo_Frasco = {"Conteúdo", "50U", "100U", "200U", "300U", "500U"};
        String[] listDiametroAgulhas = {"Diametro","30G","31G","32G","33G"};
        String[] listComprimentoAgulhas = {"Comprimento","1mm","2mm","3mm","4mm","5mm","6mm","7mm","8mm","9mm","10mm","11mm","12mm","13mm"};

        //TODO: Set Adpter dos Spinners
        ArrayAdapter<String> AdapterMarcaToxina = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item,listMarcaToxina);
        AdapterMarcaToxina.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        SpinnerMarcaToxina.setAdapter(AdapterMarcaToxina);


        SpinnerMarcaToxina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(SpinnerMarcaToxina.getSelectedItemPosition()) {
                    case 0:
                        Spinner_Distribuidora.setSelection(0);
                        break;
                    case 1:
                        Spinner_Distribuidora.setSelection(1);
                        break;
                    case 2:
                        Spinner_Distribuidora.setSelection(2);
                        break;
                    case 3:
                        Spinner_Distribuidora.setSelection(3);
                        break;
                    case 4:
                        Spinner_Distribuidora.setSelection(4);
                        break;
                    case 5:
                        Spinner_Distribuidora.setSelection(5);
                        break;
                    case 6:
                        Spinner_Distribuidora.setSelection(6);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner_Distribuidora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
              switch(Spinner_Distribuidora.getSelectedItemPosition()) {
                    case 0:
                        SpinnerMarcaToxina.setSelection(0);
                        List<String> ListaMarcaToxina = new ArrayList<>();
                        ListaMarcaToxina.add("Conteúdo");
                        Edit_Nome_Toxina.setText("Nome da Toxina:");

                        ArrayAdapter<String> AdapterMarcaToxina = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, ListaMarcaToxina);
                        AdapterMarcaToxina.setDropDownViewResource(R.layout.center_item);
                        Spinner_Conteudo_Frasco.setAdapter(AdapterMarcaToxina);
                        SpinnerMarcaToxina.setSelection(0);
                        break;
                    case 1:
                        Toast.makeText(Preencher_Mapa_Part2.this, "SpinnerMarcaToxina : "+SpinnerMarcaToxina.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
                        SpinnerMarcaToxina.setSelection(1);
                        List<String> ListaConteudoFrasco1 = new ArrayList<>();

                        ListaConteudoFrasco1.add("Conteúdo");
                        ListaConteudoFrasco1.add("50U");
                        ListaConteudoFrasco1.add("100U");
                        ListaConteudoFrasco1.add("200U");
                        Edit_Nome_Toxina.setText("Toxina Botulínica tipo A");


                        ArrayAdapter<String> AdapterConteudo_Frasco = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, ListaConteudoFrasco1);
                        AdapterConteudo_Frasco.setDropDownViewResource(R.layout.center_item);
                        Spinner_Conteudo_Frasco.setAdapter(AdapterConteudo_Frasco);
                        Spinner_Conteudo_Frasco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                switch(Spinner_Conteudo_Frasco.getSelectedItemPosition()) {
                                    case 1:
                                        imgBotox.setImageResource(R.drawable.botox_allegran_50units);
                                        final Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.botox_allegran_50units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN:
                                                        ZoomImgBotox(bit);
                                                        break;

                                                }
                                            }return true;}
                                        });
                                        break;
                                    case 2:
                                        imgBotox.setImageResource(R.drawable.botox_allegran_100units);
                                        final Bitmap bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.botox_allegran_100units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN:
                                                        ZoomImgBotox(bit2);
                                                        break;

                                                }
                                            }return true;}
                                        });
                                        break;
                                    case 3:
                                        imgBotox.setImageResource(R.drawable.botox_allegran_200units);
                                        final Bitmap bit3 = BitmapFactory.decodeResource(getResources(), R.drawable.botox_allegran_200units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN:
                                                        ZoomImgBotox(bit3);
                                                        break;

                                                }
                                            }return true;}
                                        });
                                        break;
                                    default:
                                        imgBotox.setImageResource(R.drawable.logotipo);
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        break;
                    case 2:
                        SpinnerMarcaToxina.setSelection(2);
                        List<String> ListaMarcaToxina2 = new ArrayList<>();
                        Edit_Nome_Toxina.setText("Toxina Botulínica tipo A");


                        ListaMarcaToxina2.add("Conteúdo");
                        ListaMarcaToxina2.add("100U");

                        ArrayAdapter<String> AdapterMarcaToxina2 = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, ListaMarcaToxina2);
                        AdapterMarcaToxina2.setDropDownViewResource(R.layout.center_item);
                        Spinner_Conteudo_Frasco.setAdapter(AdapterMarcaToxina2);
                        Spinner_Conteudo_Frasco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                switch(Spinner_Conteudo_Frasco.getSelectedItemPosition()) {
                                    case 1:
                                        imgBotox.setImageResource(R.drawable.botox_xeomin_100units);
                                        final Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.botox_xeomin_100units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN:
                                                        ZoomImgBotox(bit);
                                                        break;

                                                }
                                            }return true;}
                                        });
                                        break;
                                    default:
                                        imgBotox.setImageResource(R.drawable.logotipo);
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case 3:
                        SpinnerMarcaToxina.setSelection(3);
                        List<String> ListaMarcaToxina3 = new ArrayList<>();
                        Edit_Nome_Toxina.setText("Toxina Botulínica  A");


                        ListaMarcaToxina3.add("Conteúdo");
                        ListaMarcaToxina3.add("50U");
                        ListaMarcaToxina3.add("100U");

                        ArrayAdapter<String> AdapterMarcaToxina3 = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, ListaMarcaToxina3);
                        AdapterMarcaToxina3.setDropDownViewResource(R.layout.center_item);
                        Spinner_Conteudo_Frasco.setAdapter(AdapterMarcaToxina3);
                        Spinner_Conteudo_Frasco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if(Spinner_Conteudo_Frasco.getSelectedItemPosition() == 1 || Spinner_Conteudo_Frasco.getSelectedItemPosition() == 2){
                                    imgBotox.setImageResource(R.drawable.botox_prosigne);
                                    final Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.botox_prosigne,null);
                                    imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                            int action = m.getActionMasked();
                                            switch (action)
                                            {
                                                case MotionEvent.ACTION_DOWN: ZoomImgBotox(bit); break;
                                            }
                                        }return true;}
                                    });
                                }else{
                                    imgBotox.setImageResource(R.drawable.logotipo);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        break;
                    case 4:
                        SpinnerMarcaToxina.setSelection(4);
                        List<String> ListaMarcaToxina4 = new ArrayList<>();
                        Edit_Nome_Toxina.setText("Clostridium botulinum type A");


                        ListaMarcaToxina4.add("Conteúdo");
                        ListaMarcaToxina4.add("300U");
                        ListaMarcaToxina4.add("500U");

                        ArrayAdapter<String> AdapterMarcaToxina4 = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, ListaMarcaToxina4);
                        AdapterMarcaToxina4.setDropDownViewResource(R.layout.center_item);
                        Spinner_Conteudo_Frasco.setAdapter(AdapterMarcaToxina4);
                        Spinner_Conteudo_Frasco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                switch(Spinner_Conteudo_Frasco.getSelectedItemPosition()) {
                                    case 1:
                                        imgBotox.setImageResource(R.drawable.botox_ipsen_dysport_300units);
                                        final Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.botox_ipsen_dysport_300units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN: ZoomImgBotox(bit); break;
                                                }
                                            }return true;}
                                        });
                                        break;
                                    case 2:
                                        imgBotox.setImageResource(R.drawable.botox_ipsen_dysport_500units);
                                        final Bitmap bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.botox_ipsen_dysport_500units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN: ZoomImgBotox(bit2); break;
                                                }
                                            }return true;}
                                        });
                                        break;
                                    default:
                                        imgBotox.setImageResource(R.drawable.logotipo);
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        break;
                    case 5:
                        SpinnerMarcaToxina.setSelection(5);
                        List<String> ListaMarcaToxina5 = new ArrayList<>();
                        Edit_Nome_Toxina.setText("Toxina Botulínica  A");

                        ListaMarcaToxina5.add("Conteúdo");
                        ListaMarcaToxina5.add("50U");
                        ListaMarcaToxina5.add("100U");
                        ListaMarcaToxina5.add("200U");

                        ArrayAdapter<String> AdapterMarcaToxina5 = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, ListaMarcaToxina5);
                        AdapterMarcaToxina5.setDropDownViewResource(R.layout.center_item);
                        Spinner_Conteudo_Frasco.setAdapter(AdapterMarcaToxina5);
                        Spinner_Conteudo_Frasco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                switch(Spinner_Conteudo_Frasco.getSelectedItemPosition()) {
                                    case 1:
                                        imgBotox.setImageResource(R.drawable.logotipo);
                                        break;
                                    case 2:
                                        imgBotox.setImageResource(R.drawable.botox_botulift_100units);
                                        final Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.botox_botulift_100units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN: ZoomImgBotox(bit); break;
                                                }
                                            }return true;}
                                        });
                                        break;
                                    case 3:
                                        imgBotox.setImageResource(R.drawable.botox_botulift_200units);
                                        final Bitmap bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.botox_botulift_200units,null);
                                        imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                                int action = m.getActionMasked();
                                                switch (action)
                                                {
                                                    case MotionEvent.ACTION_DOWN: ZoomImgBotox(bit2); break;
                                                }
                                            }return true;}
                                        });
                                        break;

                                    default:
                                        imgBotox.setImageResource(R.drawable.logotipo);
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                    case 6:
                        SpinnerMarcaToxina.setSelection(6);
                        List<String> ListaMarcaToxina6 = new ArrayList<>();
                        Edit_Nome_Toxina.setText("Botulinum Toxin Type A");

                        ListaMarcaToxina6.add("Conteúdo");
                        ListaMarcaToxina6.add("50U");
                        ListaMarcaToxina6.add("100U");
                        ListaMarcaToxina6.add("200U");

                        ArrayAdapter<String> AdapterMarcaToxina6 = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, ListaMarcaToxina6);
                        AdapterMarcaToxina6.setDropDownViewResource(R.layout.center_item);
                        Spinner_Conteudo_Frasco.setAdapter(AdapterMarcaToxina6);
                        Spinner_Conteudo_Frasco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                if(Spinner_Conteudo_Frasco.getSelectedItemPosition() == 1 ||
                                        Spinner_Conteudo_Frasco.getSelectedItemPosition() == 2 ||
                                        Spinner_Conteudo_Frasco.getSelectedItemPosition() == 3){

                                    imgBotox.setImageResource(R.drawable.botox_botulim_blau);
                                    final Bitmap bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.botox_botulim_blau,null);
                                    imgBotox.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent m) {int pointerCount = m.getPointerCount();for (int i = 0; i < pointerCount; i++){
                                            int action = m.getActionMasked();
                                            switch (action)
                                            {
                                                case MotionEvent.ACTION_DOWN: ZoomImgBotox(bit2); break;
                                            }
                                        }return true;}
                                    });

                                }else{
                                    imgBotox.setImageResource(R.drawable.logotipo);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        SpinnerMarcaToxina.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        SpinnerMarcaToxina.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        SpinnerMarcaToxina.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        SpinnerMarcaToxina.setSelection(0);
        //TODO: ----------------------------//

        ArrayAdapter<String> AdapterDistribuidora = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item,listDistribuidora);
        AdapterDistribuidora.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        Spinner_Distribuidora.setAdapter(AdapterDistribuidora);

        Spinner_Distribuidora.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spinner_Distribuidora.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spinner_Distribuidora.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        Spinner_Distribuidora.setSelection(0);
        //TODO: ----------------------------//

        ArrayAdapter<String> AdapterConteudo_Frasco = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item, listConteudo_Frasco);
        AdapterConteudo_Frasco.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        Spinner_Conteudo_Frasco.setAdapter(AdapterConteudo_Frasco);

        Spinner_Conteudo_Frasco.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Spinner_Conteudo_Frasco.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        Spinner_Conteudo_Frasco.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        Spinner_Conteudo_Frasco.setSelection(0);
        //TODO: ----------------------------//

        ArrayAdapter<String> AdapterDiametroAgulhas = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item,listDiametroAgulhas);
        AdapterDiametroAgulhas.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        SpinnerDiamAgulha.setAdapter(AdapterDiametroAgulhas);

        SpinnerDiamAgulha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        SpinnerDiamAgulha.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        SpinnerDiamAgulha.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        SpinnerDiamAgulha.setSelection(0);
        //TODO: ----------------------------//

        final ArrayAdapter<String> AdapterComprimentoAgulhas = new ArrayAdapter<>(Preencher_Mapa_Part2.this,R.layout.center_item,R.id.center_item,listComprimentoAgulhas);
        AdapterComprimentoAgulhas.setDropDownViewResource(R.layout.center_item);
        // Data bind the spinner with array adapter items
        SpinnerCompAgulha.setAdapter(AdapterComprimentoAgulhas);

        SpinnerCompAgulha.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        SpinnerCompAgulha.setGravity(View.TEXT_ALIGNMENT_CENTER);//Gravity setting for positioning the currently selected item.
        SpinnerCompAgulha.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        SpinnerCompAgulha.setSelection(0);
        //TODO: ----------------------------//
        Edit_DT_Validade.addTextChangedListener(MaskEdit.mask(Edit_DT_Validade, MaskEdit.FORMAT_DATE));

        Button Btn_InserirPontoMapVoltar = view.findViewById(R.id.Btn_InserirPontoMapVoltar);
        Btn_InserirPontoMapVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {alerta.dismiss();
            }
        });

        Button Btn_InserirPontoMapSave = view.findViewById(R.id.Btn_InserirPontoMapSave);
        Btn_InserirPontoMapSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Edit_Nome_Toxina.getText().toString();
                String MarcaToxina =  SpinnerMarcaToxina.getSelectedItem().toString();
                String Distribuidora =  Spinner_Distribuidora.getSelectedItem().toString();
                String Conteudo_Frasco =  Spinner_Conteudo_Frasco.getSelectedItem().toString();
                txtViewSoroFisi.getText().toString();
                String CompAgulha =  SpinnerDiamAgulha.getSelectedItem().toString();
                String DiamAgulha =  SpinnerCompAgulha.getSelectedItem().toString();
                Edit_DT_Validade.getText().toString();
                Edit_Lote_Toxina.getText().toString();

                String[] CountArray = new String[listPontoCount.size()];
                CountArray = listPontoCount.toArray(CountArray);

                //for (int i = 0; i < NomesArray.length; i++) {
                hmResult.put("Txt_PontoNumber",CountArray[i]);
                hmResult.put("Edit_Nome_Toxina",Edit_Nome_Toxina.getText().toString());
                hmResult.put("SpinnerMarcaToxina",MarcaToxina);
                hmResult.put("Spinner_Distribuidora",Distribuidora);
                hmResult.put("Spinner_Conteudo_Frasco", Conteudo_Frasco);
                hmResult.put("txtViewSoroFisi",  txtViewSoroFisi.getText().toString());
                hmResult.put("SpinnerDiamAgulha",  DiamAgulha);
                hmResult.put("SpinnerCompAgulha", CompAgulha);
                hmResult.put("Edit_DT_Validade",Edit_DT_Validade.getText().toString());
                hmResult.put("Edit_Lote_Toxina", Edit_Lote_Toxina.getText().toString());
                aListResult.add(hmResult);
                //}

                String[] from2 = {"Txt_PontoNumber","Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                "Edit_DT_Validade", "Edit_Lote_Toxina"};

                int[] to2 = {R.id.Txt_PontoNumber,R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                SimpleAdapter simpleAdapter2 = new SimpleAdapter(getBaseContext(), aListResult, R.layout.show_list_ponto_saved, from2, to2);
                listViewAddPontos.setAdapter(simpleAdapter2);
                simpleAdapter2.notifyDataSetChanged();*/
                /* List<HashMap<String, String>> aList = new ArrayList<>();
                    for (int i = 0; i < 1; i++) {
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("Txt_PontoNumber", CountArray[0 + 1]);
                        hm.put("Edit_Nome_Toxina", Edit_Nome_Toxina.getText().toString());
                        hm.put("SpinnerMarcaToxina", MarcaToxina);
                        hm.put("Spinner_Distribuidora", Distribuidora);
                        hm.put("Spinner_Conteudo_Frasco", Conteudo_Frasco);
                        hm.put("txtViewSoroFisi", txtViewSoroFisi.getText().toString());
                        //hm.put("imgBotox", DataNascArray[i]);
                        hm.put("SpinnerDiamAgulha", DiamAgulha);
                        hm.put("SpinnerCompAgulha", CompAgulha);
                        hm.put("Edit_DT_Validade", Edit_DT_Validade.getText().toString());
                        hm.put("Edit_Lote_Toxina", Edit_Lote_Toxina.getText().toString());
                        aList.add(hm);
                    }

                    String[] from = {"Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                    "Edit_DT_Validade", "Edit_Lote_Toxina"};

                    int[] to = {R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                    R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.show_list_ponto_saved, from, to);
                    listViewAddPontos.setAdapter(simpleAdapter);
                    simpleAdapter.notifyDataSetChanged();*/
                /*listViewPonto.add(String.valueOf(PontoCount));
                listViewPonto.add(Edit_Nome_Toxina.getText().toString());
                listViewPonto.add(SpinnerMarcaToxina.getSelectedItem().toString());
                listViewPonto.add(Spinner_Distribuidora.getSelectedItem().toString());
                listViewPonto.add(Spinner_Conteudo_Frasco.getSelectedItem().toString());
                listViewPonto.add(txtViewSoroFisi.getText().toString());
                listViewPonto.add(SpinnerDiamAgulha.getSelectedItem().toString());
                listViewPonto.add(SpinnerCompAgulha.getSelectedItem().toString());
                listViewPonto.add(Edit_DT_Validade.getText().toString());
                listViewPonto.add(Edit_Lote_Toxina.getText().toString());*/
                //List<HashMap<String, String>> aList = new ArrayList<>();
                //for (int i = 0; i < 1; i++) {

                HashMap<String, String> hm = new HashMap<>();
                hm.put("Txt_PontoNumber", PontoCount+"");
                hm.put("Edit_Nome_Toxina", Edit_Nome_Toxina.getText().toString());
                hm.put("SpinnerMarcaToxina", SpinnerMarcaToxina.getSelectedItem().toString());
                hm.put("Spinner_Distribuidora", Spinner_Distribuidora.getSelectedItem().toString());
                hm.put("Spinner_Conteudo_Frasco", Spinner_Conteudo_Frasco.getSelectedItem().toString());
                hm.put("txtViewSoroFisi", txtViewSoroFisi.getText().toString());
                hm.put("EditVolumeAplicado", EditVolumeAplicado.getText().toString()+" ml");
                //hm.put("imgBotox", DataNascArray[i]);
                hm.put("SpinnerDiamAgulha", SpinnerDiamAgulha.getSelectedItem().toString());
                hm.put("SpinnerCompAgulha", SpinnerCompAgulha.getSelectedItem().toString());
                hm.put("Edit_DT_Validade", Edit_DT_Validade.getText().toString());
                hm.put("Edit_Lote_Toxina", Edit_Lote_Toxina.getText().toString());
                //aList.add(hm);
                aListResult.add(hm);
                Log.e("App1","Result aList : "+aListResult.get(0));
                CheckPontoAdd.add(PontoCount);


                String[] from = {"Txt_PontoNumber","Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco","EditVolumeAplicado", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
                "Edit_DT_Validade", "Edit_Lote_Toxina"};

                int[] to = {R.id.Txt_PontoNumber,R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result,R.id.EditVolumeAplicado, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
                R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

                SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aListResult, R.layout.show_list_ponto_saved, from, to);
                listViewAddPontos.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();

                String Nome_Toxina     = Edit_Nome_Toxina.getText().toString();
                String MarcaToxina     = SpinnerMarcaToxina.getSelectedItem().toString();
                String Distribuidora   = Spinner_Distribuidora.getSelectedItem().toString();
                String Conteudo_Frasco = Spinner_Conteudo_Frasco.getSelectedItem().toString();
                String SoroFisiologico = txtViewSoroFisi.getText().toString();
                String Volume_Aplicado = EditVolumeAplicado.getText().toString()+" ml";

                PontoCountAux               = PontoCount;
                Edit_Nome_ToxinaAux         = Edit_Nome_Toxina.getText().toString();
                SpinnerMarcaToxinaAux       = SpinnerMarcaToxina.getSelectedItem().toString();
                Spinner_DistribuidoraAux    = Spinner_Distribuidora.getSelectedItem().toString();
                Spinner_Conteudo_FrascoAux  = Spinner_Conteudo_Frasco.getSelectedItem().toString().replace("U","");
                txtViewSoroFisiAux          = txtViewSoroFisi.getText().toString();
                EditVolumeAplicadoAux       = EditVolumeAplicado.getText().toString().replace(" ml","");
                SpinnerDiamAgulhaAux        = SpinnerDiamAgulha.getSelectedItem().toString().replace("G","");
                SpinnerCompAgulhaAux        = SpinnerCompAgulha.getSelectedItem().toString().replace("mm","");
                Edit_DT_ValidadeAux         = Edit_DT_Validade.getText().toString();
                Edit_Lote_ToxinaAux         = Edit_Lote_Toxina.getText().toString();

                doTaskInsertPontoApli taskInsertPonto = new doTaskInsertPontoApli(
                Preencher_Mapa_Part2.this,NomePontoAux,CoordXAux,CoordYAux,Nome_Toxina,MarcaToxina,Distribuidora,
                Integer.parseInt(Conteudo_Frasco.replace("U","")),
                txtViewSoroFisi.getText().toString(),
                Integer.parseInt(Volume_Aplicado.replace(" ml","")),
                Integer.parseInt(SpinnerDiamAgulha.getSelectedItem().toString().replace("G","")),
                Integer.parseInt(SpinnerCompAgulha.getSelectedItem().toString().replace("mm","")),
                Edit_DT_Validade.getText().toString(),Edit_Lote_Toxina.getText().toString());
                taskInsertPonto.execute();
                //Spinner_ResultPontos.setSelection(0);

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
        return listViewPonto;

    }

    private List<String> PreviewMap(List<HashMap<String, String>> ListHash,List<String> ListCoord,Bitmap bit1) {

        final List<String> listViewPonto = new ArrayList<>();
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.preview_mapa, null);


        ProportionalImageView Img_DPP = view.findViewById(R.id.Img_DPP);
        DesenharPontoPreview DesenharPP = view.findViewById(R.id.DesenharPP);
        DesenharPP.ListCoord = ListCoord;


        ListView listPreview = view.findViewById(R.id.ListPreview);
        //Img_DPP ImagePreview = view.findViewById(R.id.Img_DPP);
        //Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.antes, null);
        Img_DPP.setImageBitmap(bit1);


        String[] from = {"Txt_PontoNumber","Edit_Nome_Toxina", "SpinnerMarcaToxina", "Spinner_Distribuidora", "Spinner_Conteudo_Frasco","EditVolumeAplicado", "txtViewSoroFisi", "SpinnerDiamAgulha", "SpinnerCompAgulha",
        "Edit_DT_Validade", "Edit_Lote_Toxina"};

        int[] to = {R.id.Txt_PontoNumber,R.id.Txt_Nome_Tox_Result, R.id.Txt_Marc_Tox_Result, R.id.Txt_Dest_Tox_Result, R.id.Txt_Frasco_Tox_Result,R.id.EditVolumeAplicado, R.id.txtViewSoroFisiResult, R.id.Txt_Diam_Tox_Result,
        R.id.Txt_Comp_Tox_Result, R.id.Txt_Data_Tox_Result, R.id.Txt_Lote_Tox_Result};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aListResult, R.layout.show_list_preview, from, to);
        listPreview.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();

        Button Btn_ClosePreview = view.findViewById(R.id.Btn_ClosePreview);
        Btn_ClosePreview.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {alerta.dismiss();}
        });

        builder.setView(view);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        Window window = alerta.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        Preencher_Mapa_Part2.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (displaymetrics.widthPixels);
        int height = (displaymetrics.heightPixels);
        alerta.getWindow().setLayout(width, height);

        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        return listViewPonto;

    }

    /*TODO: Atualizar todos os campos*/
    private void ZoomImgBotox(final Bitmap bit) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.show_zoom_image,null);
        //definimos para o botão do layout um clickListener

        final ImageView ImgShowZoom = view.findViewById(R.id.ImgShowZoom);
        ImgShowZoom.setImageBitmap(bit);

        Button BtnZoomImgClose = view.findViewById(R.id.BtnZoomImgClose);
        BtnZoomImgClose.setOnClickListener(new View.OnClickListener() {
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

    private byte[] BitmapToFotoByteArray(String FilePathFoto){
        Bitmap bit1 = BitmapFactory.decodeFile(FilePathFoto);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit1.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private byte[] BitmapDefaultToByteArray(){
        //TODO: Foto Default(drawable to bitmap /Bitmap to byte array)
        Bitmap BitDefault = BitmapFactory.decodeResource(Preencher_Mapa_Part2.this.getResources(),R.drawable.logotipo);
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        BitDefault.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] byteArray2 = stream2.toByteArray();
        return byteArray2;
    }
}
