package br.sofex.com.facialmap.Paciente.Excluir;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
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
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.Paciente.PacienteMenu;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Excluir_Paciente extends AppCompatActivity {

    /*TODO: importação de classes internas*/
    Util util = new Util(Excluir_Paciente.this);
   // DataBaseDAO pacienteDAO = new DataBaseDAO(Excluir_Paciente.this);

    /*TODO: Variáveis do tipo AlertDialog*/
    ListView lv_itemsDeletePac;
    AutoCompleteTextView Auto_SearchDelPac;
    Button BtnTelaInicialDelPac,BtnVoltarDelPac;

    Mensagem mensagem = new Mensagem(Excluir_Paciente.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir__paciente);

        util.setTitleBarApp("Facial Map -Excluir Paciente(s)",getSupportActionBar(),Excluir_Paciente.this);
        lv_itemsDeletePac = findViewById(R.id.LV_RETORNO);
        Auto_SearchDelPac = findViewById(R.id.EditPesqPaciente);
        BtnTelaInicialDelPac = findViewById(R.id.BtnTelaInicialDelPac);
        BtnVoltarDelPac = findViewById(R.id.BtnVoltarDelPac);

        List<String> ListNomes = new ArrayList<>();
        try {ListNomes = new doTaskCadastroCheckRow(Excluir_Paciente.this,2).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String  x123 = "Renan Costa e Silva";

        // Criação de um ArrayAdapter, para que possa mostrar
        // a lista de sugestões para o usuário
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.textview_autocompletview, R.id.txtview_autocomplete, ListNomes);
        Auto_SearchDelPac.setAdapter(adapter);
        Auto_SearchDelPac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           try {
                 CarregarPacientesByNome(
                 new doTaskCadastroGetCodListByNome(Excluir_Paciente.this,Auto_SearchDelPac.getText().toString()).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),1).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),2).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),3).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),4).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),5).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),6).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),7).execute().get(),
                 new doTaskCadastroGetListByNome   (Excluir_Paciente.this,Auto_SearchDelPac.getText().toString(),8).execute().get());
           } catch (ExecutionException e) {
               e.printStackTrace();
           } catch (InterruptedException e) {
              e.printStackTrace();
           }

            }

        });

        Auto_SearchDelPac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("JFT", "EDITTEXT => = "+charSequence);
                lv_itemsDeletePac.setAdapter(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        try {
            CarregarPacientesByNome(
                    new doTaskCadastroGetCodList(Excluir_Paciente.this).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,1).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,2).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,3).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,4).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,5).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,6).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,7).execute().get(),
                    new doTaskCadastroGetListas(Excluir_Paciente.this,8).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BtnTelaInicialDelPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Excluir_Paciente.this, MainActivity.class);
                startActivity(intent);
            }
        });
        BtnVoltarDelPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Excluir_Paciente.this, PacienteMenu.class);
                startActivity(intent);
            }
        });

        lv_itemsDeletePac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.e("UPDATE","Selected : "+lv_itemsUpdate.getItemAtPosition(i));
                Log.e("UPDATE","Selected : "+GetOnlyCodPaciente(lv_itemsDeletePac.getItemAtPosition(i).toString()));
                //String filePath1 = pacienteDAO.getListPacienteFotosByID(String.valueOf(GetOnlyCodPaciente(lv_itemsUpdate.getItemAtPosition(i).toString())));
                String CodUserFoto = String.valueOf(GetOnlyCodPaciente(lv_itemsDeletePac.getItemAtPosition(i).toString()));
                if(CodUserFoto != null){
                    Intent intent = new Intent(Excluir_Paciente.this, Excluir_Detail.class);
                    intent.putExtra("CodPaciente",CodUserFoto);
                    startActivity(intent);
                }else{
                    Toast.makeText(Excluir_Paciente.this, "Error", Toast.LENGTH_SHORT).show();
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
            int[] to = {R.id.Pac_Nome, R.id.Doc_Pac, R.id.Doc_Value, R.id.Nasc_Pac, R.id.DataNasc_Pac, R.id.TelFixo_PacFound, R.id.TelCel_PacFound, R.id.Email_PacFound};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_image_text, from, to);
            lv_itemsDeletePac.setAdapter(simpleAdapter);
        }else{
            Toast.makeText(Excluir_Paciente.this, "Erro , Nome Vazio", Toast.LENGTH_SHORT).show();}
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
            int[] to = {R.id.Pac_Nome, R.id.Doc_Pac, R.id.Doc_Value, R.id.Nasc_Pac, R.id.DataNasc_Pac, R.id.TelFixo_PacFound, R.id.TelCel_PacFound, R.id.Email_PacFound};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_image_text, from, to);
            lv_itemsDeletePac.setAdapter(simpleAdapter);
        }else{
            Toast.makeText(Excluir_Paciente.this, "Erro , Nome Vazio", Toast.LENGTH_SHORT).show();}
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

    @Override
    protected void onResume(){
        super.onResume();
        /*CarregarPacientes(pacienteDAO.getListCodPacientesLong(),pacienteDAO.getListNomePacientes(),pacienteDAO.getListTipoDoc(),pacienteDAO.getListDocValue(),
                pacienteDAO.getListNascionalidade(), pacienteDAO.getListDataNasc(),pacienteDAO.getListTelFixo(),pacienteDAO.getListTelCelular(),pacienteDAO.getListEmail());*/
    }


}
