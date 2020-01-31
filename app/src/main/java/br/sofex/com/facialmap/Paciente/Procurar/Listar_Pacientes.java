package br.sofex.com.facialmap.Paciente.Procurar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroCheckRow;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetCodList;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetCodListByNome;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetListByNome;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetListas;
import br.sofex.com.facialmap.MainActivity;
import br.sofex.com.facialmap.Paciente.PacienteMenu;
import br.sofex.com.facialmap.Paciente.Paciente_Encontrado;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;
import br.sofex.com.facialmap.Util.doTaskCall_Intent;

public class Listar_Pacientes extends AppCompatActivity {

    ListView LV_RETORNO;
    AutoCompleteTextView EditPesqPaciente;
    Util util = new Util(Listar_Pacientes.this);
    doTaskCall_Intent taskcallintent = new doTaskCall_Intent(Listar_Pacientes.this, Paciente_Encontrado.class);
    Button BtnEditPacPrinc,BtnEditPacVolta,BtnTelaPrincipal1FM,BtnTelaListarPaciente;
    //DataBaseDAO pacienteDAO = new DataBaseDAO(Listar_Pacientes.this);
    Button BtnTelaPrincipalListarPac,BtnTelaListarPacBack;

    /*TODO: Variáveis do tipo AlertDialog*/
    private AlertDialog alerta;
    ListView lv_itemsListar;
    RelativeLayout RL_PacFounds;
    AutoCompleteTextView Auto_SearchPacListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar__pacientes);

        /*TODO: importação de classes internas*/
        util.setTitleBarApp("Facial Map - Listar Paciente(s)",getSupportActionBar(),Listar_Pacientes.this);
        lv_itemsListar = findViewById(R.id.LV_RETORNO);
        BtnTelaListarPacBack = findViewById(R.id.BtnTelaListarPacBack);
        BtnTelaPrincipalListarPac = findViewById(R.id.BtnTelaPrincipalListarPac);

        BtnTelaListarPacBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Listar_Pacientes.this, PacienteMenu.class);
                startActivity(intent);
            }
        });

        BtnTelaPrincipalListarPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Listar_Pacientes.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*String[] NomesArray = new String[pacienteDAO.getListNomePacientes().size()];
        NomesArray = pacienteDAO.getListNomePacientes().toArray(NomesArray);

        String[] DocTypeArray = new String[pacienteDAO.getListTipoDoc().size()];
        DocTypeArray = pacienteDAO.getListTipoDoc().toArray(DocTypeArray);

        String[] DocValueArray = new String[pacienteDAO.getListDocValue().size()];
        DocValueArray = pacienteDAO.getListDocValue().toArray(DocValueArray);

        String[] NascionalidadeArray = new String[pacienteDAO.getListNascionalidade().size()];
        NascionalidadeArray = pacienteDAO.getListNascionalidade().toArray(NascionalidadeArray);

        String[] DataNascArray = new String[pacienteDAO.getListDataNasc().size()];
        DataNascArray = pacienteDAO.getListDataNasc().toArray(DataNascArray);

        String[] TelFixoArray = new String[pacienteDAO.getListTelFixo().size()];
        TelFixoArray = pacienteDAO.getListTelFixo().toArray(TelFixoArray);

        String[] TelCelularArray = new String[pacienteDAO.getListTelCelular().size()];
        TelCelularArray = pacienteDAO.getListTelCelular().toArray(TelCelularArray);

        String[] EmailArray = new String[pacienteDAO.getListEmail().size()];
        EmailArray = pacienteDAO.getListEmail().toArray(EmailArray);

        String[] FotosArray = new String[pacienteDAO.getListPacienteFotos().size()];
        FotosArray = pacienteDAO.getListPacienteFotos().toArray(FotosArray);


        if(NomesArray != null){
            List<HashMap<String, String>> aList = new ArrayList<>();
            for (int i = 0; i < NomesArray.length; i++) {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("img_perfil",  FotosArray[i]);
                hm.put("Pac_Nome", NomesArray[i]);
                hm.put("Doc_Pac", DocTypeArray[i]);
                hm.put("Doc_Value", DocValueArray[i]);
                hm.put("Nasc_Pac", NascionalidadeArray[i]);
                hm.put("DataNasc_Pac", DataNascArray[i]);
                hm.put("TelFixo_PacFound", TelFixoArray[i]);
                hm.put("TelCel_PacFound", TelCelularArray[i]);
                hm.put("Email_PacFound", EmailArray[i]);
                aList.add(hm);
            }

            String[] from = {"img_perfil", "Pac_Nome","Doc_Pac","Doc_Value","Nasc_Pac","DataNasc_Pac","TelFixo_PacFound","TelCel_PacFound","Email_PacFound"};
            int[] to = {R.id.img_perfil, R.id.Pac_Nome, R.id.Doc_Pac, R.id.Doc_Value, R.id.Nasc_Pac, R.id.DataNasc_Pac, R.id.TelFixo_PacFound, R.id.TelCel_PacFound, R.id.Email_PacFound};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_image_text, from, to);
            lv_items.setAdapter(simpleAdapter);
        }else{Toast.makeText(Listar.this, "Erro , Nome Vazio", Toast.LENGTH_SHORT).show();}*/

        Auto_SearchPacListar = findViewById(R.id.EditPesqPaciente);
        List<String> ListNomesAux = new ArrayList<>();
        final String[] word = {""};


        ListNomesAux.add(word[0]);
        List<String> ListNomes = new ArrayList<>();
        try {
            ListNomes = new doTaskCadastroCheckRow(Listar_Pacientes.this,2).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.textview_autocompletview, R.id.txtview_autocomplete, ListNomes);
        Auto_SearchPacListar.setAdapter(adapter);
        Auto_SearchPacListar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               try {
                   CarregarPacientesByNome(
                   new doTaskCadastroGetCodListByNome(Listar_Pacientes.this , Auto_SearchPacListar.getText().toString()).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),1).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),2).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),3).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),4).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),5).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),6).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),7).execute().get(),
                   new doTaskCadastroGetListByNome(Listar_Pacientes.this,Auto_SearchPacListar.getText().toString(),8).execute().get());
               } catch (ExecutionException e) {
                  e.printStackTrace();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }

            }

        });


        try {
            CarregarPacientesByNome(
               new doTaskCadastroGetCodList(Listar_Pacientes.this).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,1).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,2).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,3).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,4).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,5).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,6).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,7).execute().get(),
               new doTaskCadastroGetListas(Listar_Pacientes.this,8).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lv_itemsListar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Log.e("UPDATE","Selected : "+lv_itemsUpdate.getItemAtPosition(i));

            Log.e("UPDATE","Selected : "+GetOnlyCodPaciente(lv_itemsListar.getItemAtPosition(i).toString()));
            //String filePath1 = pacienteDAO.getListPacienteFotosByID(String.valueOf(GetOnlyCodPaciente(lv_itemsUpdate.getItemAtPosition(i).toString())));

            String CodUserFoto = String.valueOf(GetOnlyCodPaciente(lv_itemsListar.getItemAtPosition(i).toString()));
            if(CodUserFoto != null){
                 Intent intent = new Intent(Listar_Pacientes.this, PacienteInfo.class);
                 intent.putExtra("CodPaciente",CodUserFoto);
                 startActivity(intent);
                }else{
                  Toast.makeText(Listar_Pacientes.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void CarregarPacientes(List<Long> ListaCodPacientes,List<String> ListaNomePacientes, List<String> ListaTipoDocumento, List<String> ListaDocumentoValor, List<String> ListaNascionalidade,
    List<String> ListaDataNascimento, List<String> ListaTelefoneFixo, List<String> ListaTelefoneCelular, List<String> ListaEmail){

        Long[] CodPacArray = new Long[ListaCodPacientes.size()];
        CodPacArray = ListaCodPacientes.toArray(CodPacArray);

        String[] NomesArray = new String[ListaNomePacientes.size()];
        NomesArray = ListaNomePacientes.toArray(NomesArray);

        String[] DocTypeArray = new String[ListaTipoDocumento.size()];
        DocTypeArray = ListaTipoDocumento.toArray(DocTypeArray);

        String[] DocValueArray = new String[ListaDocumentoValor.size()];
        DocValueArray = ListaDocumentoValor.toArray(DocValueArray);

        String[] NascionalidadeArray = new String[ListaNascionalidade.size()];
        NascionalidadeArray = ListaNascionalidade.toArray(NascionalidadeArray);

        String[] DataNascArray = new String[ListaDataNascimento.size()];
        DataNascArray = ListaDataNascimento.toArray(DataNascArray);

        String[] TelFixoArray = new String[ListaTelefoneFixo.size()];
        TelFixoArray = ListaTelefoneFixo.toArray(TelFixoArray);

        String[] TelCelularArray = new String[ListaTelefoneCelular.size()];
        TelCelularArray = ListaTelefoneCelular.toArray(TelCelularArray);

        String[] EmailArray = new String[ListaEmail.size()];
        EmailArray = ListaEmail.toArray(EmailArray);


        if(NomesArray != null){
            List<HashMap<String, String>> aList = new ArrayList<>();
            for (int i = 0; i < NomesArray.length; i++) {
                HashMap<String, String> hm = new HashMap<>();

                hm.put("Cod_PacFound", String.valueOf(CodPacArray[i]));
                hm.put("Pac_Nome", NomesArray[i]);
                hm.put("Doc_Pac", DocTypeArray[i]);
                hm.put("Doc_Value", DocValueArray[i]);
                hm.put("Nasc_Pac", NascionalidadeArray[i]);
                hm.put("DataNasc_Pac", DataNascArray[i]);
                hm.put("TelFixo_PacFound", TelFixoArray[i]);
                hm.put("TelCel_PacFound", TelCelularArray[i]);
                hm.put("Email_PacFound", EmailArray[i]);
                aList.add(hm);
            }

            String[] from = { "Pac_Nome","Doc_Pac","Doc_Value","Nasc_Pac","DataNasc_Pac","TelFixo_PacFound","TelCel_PacFound","Email_PacFound"};
            int[] to = { R.id.Pac_Nome, R.id.Doc_Pac, R.id.Doc_Value, R.id.Nasc_Pac, R.id.DataNasc_Pac, R.id.TelFixo_PacFound, R.id.TelCel_PacFound, R.id.Email_PacFound};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_image_text, from, to);
            LV_RETORNO.setAdapter(simpleAdapter);
        }else{
            Toast.makeText(Listar_Pacientes.this, "Erro , Nome Vazio", Toast.LENGTH_SHORT).show();}
    }

    private void CarregarPacientesByNome(List<Long> ListaCodPacientes,List<String> ListaNomePacientes, List<String> ListaTipoDocumento, List<String> ListaDocumentoValor, List<String> ListaNascionalidade,
    List<String> ListaDataNascimento, List<String> ListaTelefoneFixo, List<String> ListaTelefoneCelular, List<String> ListaEmail){

        Long[] CodPacArray = new Long[ListaCodPacientes.size()];
        CodPacArray = ListaCodPacientes.toArray(CodPacArray);

        String[] NomesArray = new String[ListaNomePacientes.size()];
        NomesArray = ListaNomePacientes.toArray(NomesArray);

        String[] DocTypeArray = new String[ListaTipoDocumento.size()];
        DocTypeArray = ListaTipoDocumento.toArray(DocTypeArray);

        String[] DocValueArray = new String[ListaDocumentoValor.size()];
        DocValueArray = ListaDocumentoValor.toArray(DocValueArray);

        String[] NascionalidadeArray = new String[ListaNascionalidade.size()];
        NascionalidadeArray = ListaNascionalidade.toArray(NascionalidadeArray);

        String[] DataNascArray = new String[ListaDataNascimento.size()];
        DataNascArray = ListaDataNascimento.toArray(DataNascArray);

        String[] TelFixoArray = new String[ListaTelefoneFixo.size()];
        TelFixoArray = ListaTelefoneFixo.toArray(TelFixoArray);

        String[] TelCelularArray = new String[ListaTelefoneCelular.size()];
        TelCelularArray = ListaTelefoneCelular.toArray(TelCelularArray);

        String[] EmailArray = new String[ListaEmail.size()];
        EmailArray = ListaEmail.toArray(EmailArray);

        /*String[] FotosArray = new String[ListaPacienteFotos.size()];
        FotosArray = ListaPacienteFotos.toArray(FotosArray);*/


        if(NomesArray != null){
            List<HashMap<String, String>> aList = new ArrayList<>();
            for (int i = 0; i < NomesArray.length; i++) {
                HashMap<String, String> hm = new HashMap<>();

                hm.put("Cod_PacFound",  String.valueOf(CodPacArray[i]));
                //hm.put("img_perfil",  FotosArray[i]);
                hm.put("Pac_Nome", NomesArray[i]);
                hm.put("Doc_Pac", DocTypeArray[i]);
                hm.put("Doc_Value", DocValueArray[i]);
                hm.put("Nasc_Pac", NascionalidadeArray[i]);
                hm.put("DataNasc_Pac", DataNascArray[i]);
                hm.put("TelFixo_PacFound", TelFixoArray[i]);
                hm.put("TelCel_PacFound", TelCelularArray[i]);
                hm.put("Email_PacFound", EmailArray[i]);
                aList.add(hm);
            }

            String[] from = { "Pac_Nome","Doc_Pac","Doc_Value","Nasc_Pac","DataNasc_Pac","TelFixo_PacFound","TelCel_PacFound","Email_PacFound"};
            int[] to = { R.id.Pac_Nome, R.id.Doc_Pac, R.id.Doc_Value, R.id.Nasc_Pac, R.id.DataNasc_Pac, R.id.TelFixo_PacFound, R.id.TelCel_PacFound, R.id.Email_PacFound};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_image_text, from, to);
            lv_itemsListar.setAdapter(simpleAdapter);
        }else{
            Toast.makeText(Listar_Pacientes.this, "Erro , Nome Vazio", Toast.LENGTH_SHORT).show();}
    }


    private Integer GetOnlyCodPaciente(String Found){
        String FoundAux1 =  Found.substring(Found.indexOf("{Cod_PacFound=") + 14,Found.indexOf(", DataNasc"));
        if(isNumeric(FoundAux1)){
            return Integer.parseInt(FoundAux1);
        }else{
            return null;
        }
    }
    public  boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
