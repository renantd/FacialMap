package br.sofex.com.facialmap.Mapeamento.Alterar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskGetCodMapaFK_ByDCHC;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskGetDataByNPCMFK;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskGetListCoordDTHR;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskGetListDataHoraByNP;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskListFotoByNomeDTHR;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskListFotosMapByCod;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskListNP_BYDCHC_CMFK;
import br.sofex.com.facialmap.AsynkTask.Mapeamento.doTaskPacGetCodByNome;
import br.sofex.com.facialmap.Mapeamento.Menu_Mapeamento;
import br.sofex.com.facialmap.Preview.DesenharPontoPreview2;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Alterar_Mapa extends AppCompatActivity {

    Spinner SpinData,SpinHora;
    Button BtnProcurarMapaFacial;
    ListView listUpdateMapa;
    Util util = new Util(Alterar_Mapa.this);
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs" ;
    String Selectedpaciente = "";
    Long CodPacSelect = 0l;
    TextView PacienteLogadoAlt;
    Button BtnAltPacBack;
    ImageView ImgPacAntes,ImgPacMapeado,ImgPacFinal,ImgPacFotoVert,ImgPacFotoHoriz;
    DesenharPontoPreview2 desenharPonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar__mapa);

        util.setTitleBarApp("Facial Map - Alterar Mapa",getSupportActionBar(),Alterar_Mapa.this);

        SpinData = findViewById(R.id.SpinDataMapaUpdate);
        SpinHora = findViewById(R.id.SpinHoraMapaHora);
        //listUpdateMapa = findViewById(R.id.ListUpdateMapa);
        PacienteLogadoAlt = findViewById(R.id.PacienteLogadoAlt);
        BtnAltPacBack = findViewById(R.id.BtnAltPacBack);
        desenharPonto = findViewById(R.id.desenharPontoMapear);
        desenharPonto.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences(MyPREFERENCES,0);
        Selectedpaciente = sharedPreferences.getString("PacienteSelecionado",null);
        PacienteLogadoAlt.setText(Selectedpaciente);


        Toast.makeText(Alterar_Mapa.this, "Selectedpaciente :"+Selectedpaciente, Toast.LENGTH_SHORT).show();
        try {CodPacSelect = new doTaskPacGetCodByNome(Alterar_Mapa.this,Selectedpaciente).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ImgPacAntes = findViewById(R.id.ImgPacAntes);
        ImgPacMapeado = findViewById(R.id.ImgPacMapeado);
        ImgPacFinal = findViewById(R.id.ImgPacFinal);
        ImgPacFotoVert = findViewById(R.id.ImgPacFotoVert);
        ImgPacFotoHoriz = findViewById(R.id.ImgPacFotoHoriz);


        List<Integer> ListFotos = new ArrayList<>();
        ListFotos.add(R.drawable.antes);
        ListFotos.add(R.drawable.foto_mapa);
        ListFotos.add(R.drawable.depois);
        ListFotos.add(R.drawable.fotohorizontal);
        ListFotos.add(R.drawable.fotovertical);
        /*List<Integer> ListFotos2 = new ArrayList<>();
        ListFotos2 = new doTaskListFotosMapByCod(Alterar_Mapa.this,)*/


        //String[] Datas = {"12/01/2019","10/04/2019","21/06/2019","15/05/2019","26/09/2019"};
        List<String> ListDatas = new ArrayList<>();
        try { ListDatas = new doTaskGetListDataHoraByNP(Alterar_Mapa.this,1).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> ListHoras = new ArrayList<>();
        try { ListHoras = new doTaskGetListDataHoraByNP(Alterar_Mapa.this,2).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(String str : ListDatas){
            Log.e("App1","Datas : "+str);
        }
        for(String str : ListHoras){
          Log.e("App1","Horas : "+str);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(Alterar_Mapa.this,R.layout.center_item,R.id.center_item, ListDatas);
        SpinData.setAdapter(spinnerArrayAdapter);
        SpinData.setSelection(0);

        //String[] horas = {"\t06:00 as\n12:00","\t12:01 as\n17:00","\t17:01 as\n00:00"};
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>(Alterar_Mapa.this,R.layout.center_item,R.id.center_item, ListHoras);
        SpinHora.setAdapter(spinnerArrayAdapter2);
        SpinHora.setSelection(0);

        /*Toast.makeText(Alterar_Mapa.this, "CodPacSelect :"+CodPacSelect+"SpinData :"+SpinData.getSelectedItem().toString()
        +"SpinHora :"+SpinHora.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();*/

        byte[] b1 = null; byte[] b2 = null;
        byte[] b3 = null;byte[] b4 = null; byte[] b5 = null;
        Integer Foto1 = 0;Integer Foto2 = 0;
        Integer Foto3 = 0;Integer Foto4 = 0;Integer Foto5 = 0;

        if(Selectedpaciente == null || Selectedpaciente.equalsIgnoreCase("Nenhum Paciente Selecionado")){

            b1 = DrawableToByteArray();
            b2 = DrawableToByteArray();
            b3 = DrawableToByteArray();
            b4 = DrawableToByteArray();
            b5 = DrawableToByteArray();

        }else{
            try {
                desenharPonto.setVisibility(View.VISIBLE);
                b1 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,1,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
                Foto1 = byteArrayToInt(b1);

                b2 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,2,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
                Foto2 = byteArrayToInt(b2);

                b3 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,3,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
                Foto3 = byteArrayToInt(b3);

                b4 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,4,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
                Foto4 = byteArrayToInt(b4);

                b5 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,5,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
                Foto5 = byteArrayToInt(b5);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CarregarDesenharPontos(Selectedpaciente,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString());
        }

        /*for(String str : ListNomes){
          //Log.e("App1","ListNomes : "+str);
            for(Integer ListCoordX1 : ListCoordX){
                //Log.e("App1","ListCoordX : "+ListCoordX1);
                for(Integer ListCoordY1 : ListCoordY){
                  Log.e("App1","Nome: "+str+" X "+ListCoordX1+" Y "+ListCoordY1);
                  Toast.makeText(Alterar_Mapa.this, "Nome: "+str+" X "+ListCoordX1+" Y "+ListCoordY1, Toast.LENGTH_SHORT).show();
                }
            }
        }*/
        /*if(List1 !=  null) {
          for(String str : List1){
            Log.e("App1","Lista123123 : "+str);
            Log.e("App1","ListaX : "+GetPointNumber(str));
            Log.e("App1","ListaX : "+RefactorCoordX(str));
            Log.e("App1","ListaX : "+RefactorCoordY(str));
          }
        }*/
        //desenharPonto.ListCoord = List1;


        Bitmap bit1 = BitmapFactory.decodeByteArray(b1,0,b1.length);
        Bitmap bit2 = BitmapFactory.decodeByteArray(b2,0,b2.length);
        Bitmap bit3 = BitmapFactory.decodeByteArray(b3,0,b3.length);
        Bitmap bit4 = BitmapFactory.decodeByteArray(b4,0,b4.length);
        Bitmap bit5 = BitmapFactory.decodeByteArray(b5,0,b5.length);
        ImgPacAntes.setImageBitmap(bit1);
        ImgPacMapeado.setImageBitmap(bit2);
        ImgPacFinal.setImageBitmap(bit3);
        ImgPacFotoVert.setImageBitmap(bit4);
        ImgPacFotoHoriz.setImageBitmap(bit5);

        ImgPacAntes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventaction = motionEvent.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                      Toast.makeText(Alterar_Mapa.this, " 1 ", Toast.LENGTH_SHORT).show();
                    break;
                }
                return true;
            }
        });
        ImgPacMapeado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventaction = motionEvent.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(Alterar_Mapa.this, " 2 ", Toast.LENGTH_SHORT).show();
                    break;
                }
                return true;
            }
        });
        ImgPacFinal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventaction = motionEvent.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(Alterar_Mapa.this, " 3 ", Toast.LENGTH_SHORT).show();
                    break;
                }
                return true;
            }
        });
        ImgPacFotoVert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventaction = motionEvent.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(Alterar_Mapa.this, " 4 ", Toast.LENGTH_SHORT).show();
                    break;
                }
                return true;
            }
        });
        ImgPacFotoHoriz.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventaction = motionEvent.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(Alterar_Mapa.this, " 5 ", Toast.LENGTH_SHORT).show();
                    break;
                }
                return true;
            }
        });

        BtnAltPacBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(Alterar_Mapa.this,Menu_Mapeamento.class);
           startActivity(intent);
            }
        });

        BtnProcurarMapaFacial = findViewById(R.id.BtnProcurarMapaFacial);
        BtnProcurarMapaFacial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           CarregarDesenharPontos(Selectedpaciente,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString());
            }
        });

    }

    public void CarregarDesenharPontos(String NomePaciente, String DataCriacao,String HoraCriacao){

        byte[] b1 = null; byte[] b2 = null;
        byte[] b3 = null;byte[] b4 = null;
        byte[] b5 = null;
        Integer Foto1 = 0;Integer Foto2 = 0;
        Integer Foto3 = 0;Integer Foto4 = 0;
        Integer Foto5 = 0;

        try {
            desenharPonto.setVisibility(View.VISIBLE);
            b1 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,1,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
            Foto1 = byteArrayToInt(b1);

            b2 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,2,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
            Foto2 = byteArrayToInt(b2);

            b3 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,3,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
            Foto3 = byteArrayToInt(b3);

            b4 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,4,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
            Foto4 = byteArrayToInt(b4);

            b5 = new doTaskListFotosMapByCod(Alterar_Mapa.this,CodPacSelect,5,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
            Foto5 = byteArrayToInt(b5);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap bit1 = BitmapFactory.decodeByteArray(b1,0,b1.length);
        Bitmap bit2 = BitmapFactory.decodeByteArray(b2,0,b2.length);
        Bitmap bit3 = BitmapFactory.decodeByteArray(b3,0,b3.length);
        Bitmap bit4 = BitmapFactory.decodeByteArray(b4,0,b4.length);
        Bitmap bit5 = BitmapFactory.decodeByteArray(b5,0,b5.length);
        ImgPacAntes.setImageBitmap(bit1);
        ImgPacMapeado.setImageBitmap(bit2);
        ImgPacFinal.setImageBitmap(bit3);
        ImgPacFotoVert.setImageBitmap(bit4);
        ImgPacFotoHoriz.setImageBitmap(bit5);

        List<String> ListNomes = null; List<Integer> ListCoordX = null;  List<Integer> ListCoordY = null;
        try {
            ListNomes = new doTaskListFotoByNomeDTHR(Alterar_Mapa.this,Selectedpaciente,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            ListCoordX = new doTaskGetListCoordDTHR(Alterar_Mapa.this,Selectedpaciente,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString(),1).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            ListCoordY = new doTaskGetListCoordDTHR(Alterar_Mapa.this,Selectedpaciente,SpinData.getSelectedItem().toString(),SpinHora.getSelectedItem().toString(),2).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<String> ListNomePonto = null;Long CodMapaFK = null;List<String> ListResult = new ArrayList<>(); List<Integer> ListCoordX1 = null;  List<Integer> ListCoordY1 = null;
        try {
            CodMapaFK = new doTaskGetCodMapaFK_ByDCHC(Alterar_Mapa.this,NomePaciente,DataCriacao,HoraCriacao).execute().get();
            ListNomePonto = new doTaskListNP_BYDCHC_CMFK(Alterar_Mapa.this,NomePaciente,DataCriacao,HoraCriacao,CodMapaFK).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(String str1 : ListNomePonto){
            try {

                ListResult.add(new doTaskGetDataByNPCMFK(Alterar_Mapa.this,NomePaciente,DataCriacao,HoraCriacao,str1,1,CodMapaFK).execute().get()+
                "X"+new doTaskGetDataByNPCMFK(Alterar_Mapa.this,NomePaciente,DataCriacao,HoraCriacao,str1,2,CodMapaFK).execute().get()+
                " "+new doTaskGetDataByNPCMFK(Alterar_Mapa.this,NomePaciente,DataCriacao,HoraCriacao,str1,1,CodMapaFK).execute().get()+
                "Y"+new doTaskGetDataByNPCMFK(Alterar_Mapa.this,NomePaciente,DataCriacao,HoraCriacao,str1,3,CodMapaFK).execute().get());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Log.e("App1","Result List1 : "+str1);
        }

        for(String str1 : ListResult){
            Log.e("App1","Result List1 : "+str1);
        }
        desenharPonto.ListCoord = ListResult;
    }

    public static int byteArrayToInt(byte[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
    public byte[] DrawableToByteArray(){
        Drawable d = getResources().getDrawable(R.drawable.logotipo);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
    public String GetPointNumber(String Coordenada){
        String Pont_Cont = Coordenada.substring(0, Coordenada.indexOf("P"));
        String requiredString = Coordenada.replaceAll(Pont_Cont, "");
        String str4 = requiredString.substring(0,  requiredString.indexOf("X"));
        return str4;
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

}

