package br.sofex.com.facialmap.Paciente.Excluir;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import br.sofex.com.facialmap.AsynkTask.Paciente.DoTaskDeletePacienteByCod;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetDataByCod;
import br.sofex.com.facialmap.AsynkTask.Paciente.doTaskCadastroGetFotoByCod;
import br.sofex.com.facialmap.Mensagem.Mensagem;
import br.sofex.com.facialmap.R;
import br.sofex.com.facialmap.Util.Util;

public class Excluir_Detail extends AppCompatActivity {

    Button BtnBackHomePacFound,BtnBackPacFound,Btn_DefDelPac;
    ImageView img_perfilFound;
    TextView Pac_NomeFound;
    TextView Doc_PacFound;
    TextView Doc_ValueFound;
    TextView Nasc_PacFound;
    TextView Etnia_PacFound;
    TextView DataNasc_PacFound;
    TextView Sexo_PacFound;
    TextView TelFixo_PacFound;
    TextView TelCel_PacFound;
    TextView Email_PacFound;
    TextView CEP_PacFound;
    TextView End_PacFound;
    TextView Comp_PacFound;
    TextView Num_PacFound;
    TextView Bairro_PacFound;
    TextView Munic_PacFound;
    TextView Estado_PacFound;
    Long CodPaciente;
    Mensagem mensagem = new Mensagem(Excluir_Detail.this);

    /*TODO: Variáveis do tipo Boolean*/
    Boolean confdel = false;

    /*TODO: Variáveis do tipo AlertDialog*/
    private AlertDialog alerta;

    /*TODO: importação de classes internas*/
    Util util = new Util(Excluir_Detail.this);
    //DataBaseDAO pacienteDAO = new DataBaseDAO(Excluir_Detail.this);

    /*TODO: Variáveis do tipo AlertDialog*/
    ListView lv_itemsUpdate;
    RelativeLayout RL_Cod_PacFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir__detail);

        util.setTitleBarApp("Facil Map - Exclusão de Paciente",getSupportActionBar(),Excluir_Detail.this);

        BtnBackHomePacFound = findViewById(R.id.BtnBackHomePacFound);
        BtnBackPacFound = findViewById(R.id.BtnBackPacFound);

        //TODO: TextView
        img_perfilFound = findViewById(R.id.img_perfilFound);
        Pac_NomeFound = findViewById(R.id.Pac_NomeFound);
        Doc_PacFound = findViewById(R.id.Doc_PacFound);
        Doc_ValueFound = findViewById(R.id.Doc_ValueFound);
        Nasc_PacFound = findViewById(R.id.Nasc_PacFound);
        Etnia_PacFound = findViewById(R.id.Etnia_PacFound);
        DataNasc_PacFound = findViewById(R.id.DataNasc_PacFound);
        Sexo_PacFound = findViewById(R.id.Sexo_PacFound);
        TelFixo_PacFound = findViewById(R.id.TelFixo_PacFound);
        TelCel_PacFound = findViewById(R.id.TelCel_PacFound);
        Email_PacFound = findViewById(R.id.Email_PacFound);
        CEP_PacFound = findViewById(R.id.CEP_PacFound);
        End_PacFound = findViewById(R.id.End_PacFound);
        Comp_PacFound = findViewById(R.id.Comp_PacFound);
        Num_PacFound = findViewById(R.id.Num_PacFound);
        Bairro_PacFound = findViewById(R.id.Bairro_PacFound);
        Munic_PacFound = findViewById(R.id.Munic_PacFound);
        Estado_PacFound = findViewById(R.id.Estado_PacFound);

        //TODO: Button
        Btn_DefDelPac = findViewById(R.id.Btn_DefDelPac);

        CodPaciente = Long.parseLong(getIntent().getExtras().getString("CodPaciente"));

        // TODO Eventos das Views
        Btn_DefDelPac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ConfirmarAlteracao(CodPaciente);
            }
        });

        byte[] FotoThread = null;
        try {
            FotoThread = new doTaskCadastroGetFotoByCod(Excluir_Detail.this,CodPaciente).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap bit = BitmapFactory.decodeByteArray(FotoThread, 0, FotoThread.length);
        img_perfilFound.setImageBitmap(bit);
        try {
            Pac_NomeFound.setText(        new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,1).execute().get());
            Doc_PacFound.setText(         new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,2).execute().get());
            Doc_ValueFound.setText(       new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,3).execute().get());
            Nasc_PacFound.setText(        new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,4).execute().get());
            Etnia_PacFound.setText(       new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,5).execute().get());
            DataNasc_PacFound.setText(    new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,6).execute().get());
            Sexo_PacFound.setText(        new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,7).execute().get());
            TelFixo_PacFound.setText(     new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,8).execute().get());
            TelCel_PacFound.setText(      new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,9).execute().get());
            Email_PacFound.setText(       new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,10).execute().get());
            CEP_PacFound.setText(         new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,11).execute().get());
            End_PacFound.setText(         new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,12).execute().get());
            Comp_PacFound.setText(        new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,13).execute().get());
            Num_PacFound.setText(         new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,14).execute().get());
            Bairro_PacFound.setText(      new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,15).execute().get());
            Munic_PacFound.setText(       new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,16).execute().get());
            Estado_PacFound.setText(      new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,17).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //TODO: Upadate Nome
    private void ConfirmarAlteracao(final Long CodPaciente) {

        final boolean[] b = {false};
        final String[] x = {null};
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.confirm_delete_paciente,null);
        //definimos para o botão do layout um clickListener


        TextView tv_msgdelpac = view.findViewById(R.id.tv_delconf);
        tv_msgdelpac.setText("Deseja realmente excluir este paciente,\n definitivamente ?");

        Button BtnDel_PC_Close = view.findViewById(R.id.BtnDel_PC_Close);
        BtnDel_PC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Toast.makeText(Excluir_Detail.this, "Confirm ? "+ b[0], Toast.LENGTH_SHORT).show();
           alerta.dismiss();
            }
        });
        Button BtnDel_PC_save = view.findViewById(R.id.BtnDel_PC_save);
        BtnDel_PC_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = "";
                try {
                    str = new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,1).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                File del = new File("/data/data/br.sofex.com.facialmap/files/Files/Mapa Facial/Paciente "+str);
                deleteDir(del);

                b[0] = true;
                try {
                    DoTaskDeletePacienteByCod TaskDeletePaciente = new DoTaskDeletePacienteByCod(Excluir_Detail.this,CodPaciente);
                    TaskDeletePaciente.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            updateInfoPaciente(CodPaciente);  alerta.dismiss();
            Intent intent = new Intent(Excluir_Detail.this,Excluir_Paciente.class);
                String NomePaciente = "";
                try {
                    NomePaciente = new doTaskCadastroGetDataByCod(Excluir_Detail.this,CodPaciente,1).execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
             intent.putExtra("pacientedeletado",NomePaciente);
            startActivity(intent);
            //Toast.makeText(Excluir_Detail.this, "Confirm ? "+ b[0], Toast.LENGTH_SHORT).show();
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
    private void  updateInfoPaciente(Long CodPaciente){
        //CodPaciente = getIntent().getExtras().getString("CodPaciente");
        /*Bitmap bit = BitmapFactory.decodeByteArray(pacienteDAO.getFotoByID(CodPaciente), 0, pacienteDAO.getFotoByID(CodPaciente).length);
        img_perfilFound.setImageBitmap(bit);
        Pac_NomeFound.setText(pacienteDAO.getNomePacienteByID(CodPaciente));
        Doc_PacFound.setText(pacienteDAO.getDocumentoByID(CodPaciente));
        Doc_ValueFound.setText(pacienteDAO.getDocumentoConteudoByID(CodPaciente));
        Nasc_PacFound.setText(pacienteDAO.getNacionalidadeByID(CodPaciente));
        Etnia_PacFound.setText(pacienteDAO.getEtniaByID(CodPaciente));
        DataNasc_PacFound.setText(pacienteDAO.getDataNascimentoByID(CodPaciente));
        Sexo_PacFound.setText(pacienteDAO.getSexoByID(CodPaciente));
        TelFixo_PacFound.setText(pacienteDAO.getTelefoneFixoByID(CodPaciente));
        TelCel_PacFound.setText(pacienteDAO.getTelefoneCelularByID(CodPaciente));
        Email_PacFound.setText(pacienteDAO.getEmailByID(CodPaciente));
        CEP_PacFound.setText(pacienteDAO.getCepByID(CodPaciente));
        End_PacFound.setText(pacienteDAO.getEnderecoByID(CodPaciente));
        Comp_PacFound.setText(pacienteDAO.getComplementoByID(CodPaciente));
        Num_PacFound.setText(pacienteDAO.getNumeroByID(CodPaciente));
        Bairro_PacFound.setText(pacienteDAO.getBairroByID(CodPaciente));
        Munic_PacFound.setText(pacienteDAO.getMunicipioByID(CodPaciente));
        Estado_PacFound.setText(pacienteDAO.getEstadoByID(CodPaciente));*/
    }

    public static void deleteFiles(String path) {

        File file = new File(path);

        if (file.exists()) {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e) { }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

}
