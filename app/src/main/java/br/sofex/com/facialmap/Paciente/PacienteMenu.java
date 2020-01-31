package br.sofex.com.facialmap.Paciente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroCheckRow;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetDataByNome;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetFotoByNome;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetListas;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskSelectSharedPreference;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskTotalPacientes;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.Paciente.Alterar.Alterar_Paciente;
import br.sofex.com.facialmap.Paciente.Cadastro.Cadastro_Paciente;
import br.sofex.com.facialmap.Paciente.Excluir.Excluir_Paciente;
import br.sofex.com.facialmap.Paciente.Procurar.Listar_Pacientes;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class PacienteMenu extends AppCompatActivity {

    Button BtnCadPaciente,BtnUpdatePaciente,BtnListarPaciente,
    BtnExcluirPaciente,BtnPacienteVoltar;
    TextView SelectedPacient;
    Button BtnProcurarPaciente;
    AutoCompleteTextView AC_TxtPaciente;
    Context mContext; private AlertDialog alerta;
    Util util = new Util(PacienteMenu.this);
    Mensagem mensagem = new Mensagem(PacienteMenu.this);
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs" ;
    String Selectedpaciente = "";
    final Integer[] CountPaciente = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_menu);

        util.setTitleBarApp("Facial Map - Menu De Paciente",getSupportActionBar(),PacienteMenu.this);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, PacienteMenu.this.MODE_PRIVATE);
        AC_TxtPaciente = findViewById(R.id.AC_TxtPaciente);
        BtnProcurarPaciente = findViewById(R.id.BtnProcurarPaciente);
        BtnCadPaciente = findViewById(R.id.BtnCadPaciente);
        BtnUpdatePaciente = findViewById(R.id.BtnUpdatePaciente);
        BtnListarPaciente = findViewById(R.id.BtnListarPaciente);
        BtnExcluirPaciente = findViewById(R.id.BtnExcluirPaciente);
        BtnPacienteVoltar = findViewById(R.id.BtnPacienteVoltar);
        util.ClickCallIntent(BtnCadPaciente,PacienteMenu.this, Cadastro_Paciente.class);
        //util.ClickCallIntent(BtnUpdatePaciente,PacienteMenu.this, Alterar_Paciente.class);
        //util.ClickCallIntent(BtnListarPaciente,PacienteMenu.this, Listar_Pacientes.class);
        //util.ClickCallIntent(BtnExcluirPaciente,PacienteMenu.this, Excluir_Paciente.class);
        util.ClickCallIntent(BtnPacienteVoltar,PacienteMenu.this, MainActivity.class);

        BtnUpdatePaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CountPaciente[0] = new doTaskTotalPacientes(PacienteMenu.this).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(CountPaciente[0] < 1){
                    mensagem.ErrorMsg("Nenhum Paciente Cadastrado ! ");
                }else{
                   Intent intent = new Intent(PacienteMenu.this, Alterar_Paciente.class);
                   startActivity(intent);
                }
            }
        });
        BtnListarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CountPaciente[0] = new doTaskTotalPacientes(PacienteMenu.this).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(CountPaciente[0] < 1){
                    mensagem.ErrorMsg("Nenhum Paciente Cadastrado ! ");
                }else{
                    Intent intent = new Intent(PacienteMenu.this, Listar_Pacientes.class);
                    startActivity(intent);
                }
            }
        });
        BtnExcluirPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CountPaciente[0] = new doTaskTotalPacientes(PacienteMenu.this).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(CountPaciente[0] < 1){
                    mensagem.ErrorMsg("Nenhum Paciente Cadastrado ! ");
                }else{
                    Intent intent = new Intent(PacienteMenu.this, Excluir_Paciente.class);
                    startActivity(intent);
                }
            }
        });



        sharedPreferences = getSharedPreferences(MyPREFERENCES,0);
        SelectedPacient = findViewById(R.id.SelectedPacient);
        //String str1 = sharedPreferences.getString("PacienteSelecionado",null);
        Selectedpaciente = sharedPreferences.getString("PacienteSelecionado",null);

        if(Selectedpaciente == null || Selectedpaciente == ""){

          SelectedPacient.setText("Nenhum paciente Selecionado");

        }else{ SelectedPacient.setText(Selectedpaciente);}

        List<String> ListNomes = new ArrayList<>();
        try {
           ListNomes = new doTaskCadastroCheckRow(PacienteMenu.this,2).execute().get();
        } catch (ExecutionException e) {
           e.printStackTrace();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

        final String[] Selection = {""};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.textview_autocompletview, R.id.txtview_autocomplete, ListNomes);
        AC_TxtPaciente.setAdapter(adapter);
        AC_TxtPaciente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Selection[0] = (String)adapterView.getItemAtPosition(i);
            }
        });

        BtnProcurarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(AC_TxtPaciente.getText().toString().isEmpty()){
                 Toast.makeText(PacienteMenu.this, "selection1 : "+AC_TxtPaciente.getText().toString(), Toast.LENGTH_SHORT).show();
                 mensagem.ErrorMsg(" Campo do pesquisa de paciente.\nEstá em branco !");
              }else{
                if(!ListNomesPacientes().contains(AC_TxtPaciente.getText().toString())){
                  mensagem.ErrorMsg("O Paciente Digitado não foi cadastrado");
                }
                else{ CarregarPaciente(AC_TxtPaciente.getText().toString());}
              }
              AC_TxtPaciente.setText(null);
            }
        });


    }

    private void CarregarPaciente(final String Nome) {

        final boolean[] b = {false};
        final String[] x = {null};
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.carregar_paciente,null);
        //definimos para o botão do layout um clickListener

        byte[] foto = null;
        ImageView img_perfilSelectPac = view.findViewById(R.id.img_perfilSelectPac);
        try {
            foto = new doTaskCadastroGetFotoByNome(PacienteMenu.this, Nome).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bitmap bit1 = BitmapFactory.decodeByteArray(foto,0,foto.length);
        img_perfilSelectPac.setImageBitmap(bit1);

        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17;
        tv1  =  view.findViewById(R.id.Pac_NomeFoundSelectPac);   tv6  =  view.findViewById(R.id.DataNasc_PacFoundSelectPac);   tv11 =  view.findViewById(R.id.CEP_PacFoundSelectPac);
        tv2  =  view.findViewById(R.id.Doc_PacFoundSelectPac);    tv7  =  view.findViewById(R.id.Sexo_PacFoundSelectPac);       tv12 =  view.findViewById(R.id.End_PacFoundSelectPac);
        tv3  =  view.findViewById(R.id.Doc_ValueFoundSelectPac);  tv8  =  view.findViewById(R.id.TelFixo_PacFoundSelectPac);    tv13 =  view.findViewById(R.id.Comp_PacFoundSelectPac);
        tv4  =  view.findViewById(R.id.Nasc_PacFoundSelectPac);   tv9  =  view.findViewById(R.id.TelCel_PacFoundSelectPac);     tv14 =  view.findViewById(R.id.Num_PacFoundSelectPac);
        tv5  =  view.findViewById(R.id.Etnia_PacFoundSelectPac);  tv10 =  view.findViewById(R.id.Email_PacFoundSelectPac);      tv15 =  view.findViewById(R.id.Bairro_PacFoundSelectPac);
        tv16 =  view.findViewById(R.id.Munic_PacFoundSelectPac);  tv17 =  view.findViewById(R.id.Estado_PacFoundSelectPac);

        try {
            tv1.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,1).execute().get());
            tv2.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,2).execute().get());
            tv3.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,3).execute().get());
            tv4.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,4).execute().get());
            tv5.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,5).execute().get());

            tv6.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,6).execute().get());
            tv7.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,7).execute().get());
            tv8.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,8).execute().get());
            tv9.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,9).execute().get());
            tv10.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,10).execute().get());

            tv11.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,11).execute().get());
            tv12.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,12).execute().get());
            tv13.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,13).execute().get());
            tv14.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,14).execute().get());
            tv15.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,15).execute().get());
            tv16.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,16).execute().get());
            tv17.setText(new doTaskCadastroGetDataByNome(PacienteMenu.this , Nome,17).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Button BtnLoadSelectPac = view.findViewById(R.id.BtnLoadSelectPac);
        BtnLoadSelectPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Long long1 = sharedPreferences.getLong("CodUser",0);
              Toast.makeText(PacienteMenu.this, "long1 "+long1, Toast.LENGTH_SHORT).show();
              String str = "";

                try {
                    str =  new doTaskSelectSharedPreference(PacienteMenu.this ,long1,Nome).execute().get();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("PacienteSelecionado", str);
                    editor.commit();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String Str2 = sharedPreferences.getString("PacienteSelecionado",null);
                SelectedPacient.setText(Str2);
                alerta.dismiss();
            }
        });


        Button BtnFecharSelectPac = view.findViewById(R.id.BtnFecharSelectPac);
        BtnFecharSelectPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alerta.dismiss();
            }
        });


        builder.setView(view);
        //define um botão como positivo
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        alerta.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN);
        TextView messageView = alerta.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);

    }
    private List<String> ListNomesPacientes(){
        List<String> ListNomes = new ArrayList<>();
        try {
            ListNomes = new doTaskCadastroGetListas(PacienteMenu.this, 1).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ListNomes;
    }


}
